package dev.nieves.view;

import dev.nieves.controller.MomentController;
import dev.nieves.repository.InMemoryDiaryRepository;
import dev.nieves.service.DiaryService;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

/**
 * Tests unitarios de {@link ConsoleView}, verificando la salida por consola.
 */
class ConsoleViewTest {

    private final PrintStream originalOut = System.out;
    private ByteArrayOutputStream outputCapture;

    @BeforeEach
    void setUp() {
        outputCapture = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputCapture, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    void tearDown() {
        System.setOut(originalOut);
    }

    @Test
    void start_withExitOption_shouldPrintFarewellMessageAndStop() {
        ConsoleView view = buildView("8\n");

        view.start();

        String output = outputCapture.toString(StandardCharsets.UTF_8);
        assertThat(output, containsString("¡Hasta pronto! Gracias por usar Mi Diario."));
    }

    private ConsoleView buildView(String simulatedInput) {
        InMemoryDiaryRepository repository = new InMemoryDiaryRepository();
        var exporter = new dev.nieves.export.CsvMomentExporter(tempDir());
        var diaryService = new DiaryService(repository, repository, exporter);
        var controller = new MomentController(diaryService);

        InputStream inputStream = new ByteArrayInputStream(simulatedInput.getBytes(StandardCharsets.UTF_8));
        Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8);

        return new ConsoleView(controller, scanner);
    }

    private java.nio.file.Path tempDir() {
        return java.nio.file.Path.of(System.getProperty("java.io.tmpdir"));
    }
}
