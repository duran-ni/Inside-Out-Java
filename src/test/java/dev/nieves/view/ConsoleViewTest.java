package dev.nieves.view;

import dev.nieves.controller.MomentController;
import dev.nieves.export.CsvMomentExporter;
import dev.nieves.repository.InMemoryDiaryRepository;
import dev.nieves.service.DiaryService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Tests unitarios de {@link ConsoleView}, verificando la salida por consola.
 */
class ConsoleViewTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;
    private Path tempDir;

    @BeforeEach
    void setUp(@TempDir Path temporaryDirectory) {
        this.tempDir = temporaryDirectory;
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void start_withExitOption_shouldPrintFarewellMessageAndStop() {
        runView("8\n");

        assertThat(output(), containsString("¡Hasta pronto! Gracias por usar Mi Diario."));
    }

    @Test
    void start_withInvalidOption_shouldPrintErrorAndContinue() {
        runView("99\n8\n");

        assertThat(output(), containsString("Opción no válida"));
    }

    @Test
    void start_addMoment_withValidData_shouldConfirm() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n8\n");

        assertThat(output(), containsString("Momento añadido correctamente."));
    }

    @Test
    void start_addMoment_withInvalidEmotion_shouldShowError() {
        runView("1\nTitle\nDescription\n99\n8\n");

        assertThat(output(), containsString("Emoción no válida."));
    }

    @Test
    void start_addMoment_withInvalidDate_shouldShowError() {
        runView("1\nTitle\nDescription\n1\nnot-a-date\n8\n");

        assertThat(output(), containsString("Formato de fecha no válido"));
    }

    @Test
    void start_listMoments_withNoMoments_shouldShowMessage() {
        runView("2\n8\n");

        assertThat(output(), containsString("No hay momentos registrados todavía."));
    }

    @Test
    void start_listMoments_withMoments_shouldPrintThem() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n2\n8\n");

        assertThat(output(), containsString("Título: Title"));
    }

    @Test
    void start_deleteMoment_withExistingId_shouldConfirm() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n3\n1\n8\n");

        assertThat(output(), containsString("Momento eliminado correctamente."));
    }

    @Test
    void start_deleteMoment_withNonExistingId_shouldShowError() {
        runView("3\n999\n8\n");

        assertThat(output(), containsString("does not exist"));
    }

    @Test
    void start_deleteMoment_withInvalidId_shouldShowError() {
        runView("3\nabc\n8\n");

        assertThat(output(), containsString("El id debe ser un número."));
    }

    @Test
    void start_updateMoment_withExistingId_shouldConfirm() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n4\n1\nNew title\n\n0\n\n8\n");

        assertThat(output(), containsString("Momento modificado correctamente."));
    }

    @Test
    void start_updateMoment_withNonExistingId_shouldShowError() {
        runView("4\n999\n8\n");

        assertThat(output(), containsString("does not exist"));
    }

    @Test
    void start_filterByEmotion_withMatches_shouldPrintThem() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n5\n1\n8\n");

        assertThat(output(), containsString("Título: Title"));
    }

    @Test
    void start_filterByEmotion_withoutMatches_shouldShowMessage() {
        runView("5\n1\n8\n");

        assertThat(output(), containsString("No hay momentos con esa emoción."));
    }

    @Test
    void start_filterByMonth_withMatches_shouldPrintThem() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n6\n15/01/2026\n8\n");

        assertThat(output(), containsString("Título: Title"));
    }

    @Test
    void start_filterByMonth_withoutMatches_shouldShowMessage() {
        runView("6\n15/01/2026\n8\n");

        assertThat(output(), containsString("No hay momentos en ese mes."));
    }

    @Test
    void start_exportToCsv_withMoments_shouldConfirmPath() {
        runView("1\nTitle\nDescription\n1\n15/01/2026\n7\n8\n");

        assertThat(output(), containsString("Momentos exportados correctamente en:"));
    }

    @Test
    void start_exportToCsv_withNoMoments_shouldShowError() {
        runView("7\n8\n");

        assertThat(output(), containsString("Error:"));
    }

    private void runView(String simulatedInput) {
        InMemoryDiaryRepository repository = new InMemoryDiaryRepository();
        CsvMomentExporter exporter = new CsvMomentExporter(tempDir);
        DiaryService diaryService = new DiaryService(repository, repository, exporter);
        MomentController controller = new MomentController(diaryService);

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);

        new ConsoleView(controller, scanner).start();
    }

    private String output() {
        return outputCapture.toString(StandardCharsets.UTF_8);
    }
}
