package dev.nieves;

import dev.nieves.controller.MomentController;
import dev.nieves.export.CsvMomentExporter;
import dev.nieves.export.InterfaceMomentExporter;
import dev.nieves.repository.InMemoryDiaryRepository;
import dev.nieves.security.InterfaceAccessService;
import dev.nieves.security.PasswordAccessService;
import dev.nieves.service.DiaryService;
import dev.nieves.service.InterfaceDiaryService;
import dev.nieves.view.AccessView;
import dev.nieves.view.ConsoleView;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Punto de entrada de la aplicación Mi Diario.
 */
public final class App {

    /**
     * Constructor privado: esta clase solo se usa a través de su método
     * {@code main}.
     */
    private App() {
    }

    /**
     * Punto de entrada de la aplicación: construye las dependencias y arranca la
     * Vista.
     *
     * @param args argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        InterfaceAccessService accessService = new PasswordAccessService();
        Scanner scanner = new Scanner(System.in, java.nio.charset.StandardCharsets.UTF_8);
        AccessView accessView = new AccessView(accessService, scanner);

        if (!accessView.login()) {
            return;
        }

        InMemoryDiaryRepository repository = new InMemoryDiaryRepository();
        InterfaceMomentExporter exporter = new CsvMomentExporter(Path.of("exports"));
        InterfaceDiaryService diaryService = new DiaryService(repository, repository, exporter);

        MomentController controller = new MomentController(diaryService);
        ConsoleView view = new ConsoleView(controller, scanner);

        view.start();
    }
}
