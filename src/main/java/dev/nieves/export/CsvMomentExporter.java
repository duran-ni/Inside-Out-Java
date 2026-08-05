package dev.nieves.export;

import dev.nieves.model.Moment;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Exporta una lista de momentos vividos a un archivo CSV en disco.
 */
public class CsvMomentExporter implements InterfaceMomentExporter {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    private static final DateTimeFormatter FILE_TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
    private static final String CSV_HEADER = "id,title,description,emotion,momentDate,createdAt,updatedAt";

    private final Path exportDirectory;

    public CsvMomentExporter(Path exportDirectory) {
        this.exportDirectory = exportDirectory;
    }

    @Override
    public String export(List<Moment> moments) throws IOException {
        Files.createDirectories(exportDirectory);

        String fileName = "moments_" + LocalDateTime.now().format(FILE_TIMESTAMP_FORMAT) + ".csv";
        Path filePath = exportDirectory.resolve(fileName);

        try (Writer writer = Files.newBufferedWriter(filePath, StandardCharsets.UTF_8)) {
            writer.write(CSV_HEADER);
            writer.write(System.lineSeparator());
            for (Moment moment : moments) {
                writer.write(toCsvRow(moment));
                writer.write(System.lineSeparator());
            }
        }

        return filePath.toAbsolutePath().toString();
    }

    private String toCsvRow(Moment moment) {
        return String.join(",",
                escape(String.valueOf(moment.getId())),
                escape(moment.getTitle()),
                escape(moment.getDescription()),
                escape(moment.getEmotion().getDisplayName()),
                escape(moment.getMomentDate().format(DATE_FORMAT)),
                escape(moment.getCreatedAt().format(DATE_TIME_FORMAT)),
                escape(moment.getUpdatedAt().format(DATE_TIME_FORMAT)));
    }

    private String escape(String value) {
        String escaped = value.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }
}
