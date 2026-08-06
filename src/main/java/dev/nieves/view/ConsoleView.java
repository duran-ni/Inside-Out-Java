package dev.nieves.view;

import dev.nieves.controller.MomentController;
import java.util.Scanner;

/**
 * Gestiona toda la entrada/salida por consola: menú principal y sub-menús.
 */
public class ConsoleView {

    private static final String MENU = """

            ===== MI DIARIO =====
            1. Añadir momento
            2. Listar momentos
            3. Eliminar momento
            4. Modificar momento
            5. Filtrar por emoción
            6. Filtrar por mes
            7. Exportar a CSV
            8. Salir
            ======================
            Elige una opción: """;

    private final MomentController controller;
    private final Scanner scanner;
    private boolean running;

    /**
     * Crea la vista de consola, inyectando el controlador y el lector de entrada.
     *
     * @param controller controlador que coordina las peticiones hacia el Service
     * @param scanner lector de la entrada estándar del usuario
     */
    public ConsoleView(MomentController controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
        this.running = true;
    }

    /**
     * Arranca el bucle principal del menú, hasta que el usuario elige salir.
     */
    public void start() {
        while (running) {
            System.out.print(MENU);
            String option = scanner.nextLine();
            handleOption(option);
        }
    }

    private void handleOption(String option) {
        switch (option) {
            case "1" -> System.out.println("Añadir momento (pendiente de implementar)");
            case "2" -> System.out.println("Listar momentos (pendiente de implementar)");
            case "3" -> System.out.println("Eliminar momento (pendiente de implementar)");
            case "4" -> System.out.println("Modificar momento (pendiente de implementar)");
            case "5" -> System.out.println("Filtrar por emoción (pendiente de implementar)");
            case "6" -> System.out.println("Filtrar por mes (pendiente de implementar)");
            case "7" -> System.out.println("Exportar a CSV (pendiente de implementar)");
            case "8" -> {
                running = false;
            }
            default -> System.out.println("Opción no válida, inténtalo de nuevo.");
        }
    }
}
