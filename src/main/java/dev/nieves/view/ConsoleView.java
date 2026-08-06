package dev.nieves.view;

import dev.nieves.controller.MomentController;
import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
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

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

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
            case "1" -> addMoment();
            case "2" -> listMoments();
            case "3" -> deleteMoment();
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

    private void addMoment() {
        System.out.print("Título: ");
        String title = scanner.nextLine();

        System.out.print("Descripción: ");
        String description = scanner.nextLine();

        Emotion emotion = readEmotion();
        if (emotion == null) {
            return;
        }

        LocalDate momentDate = readDate("Fecha del momento (dd/MM/yyyy): ");
        if (momentDate == null) {
            return;
        }

        try {
            controller.addMoment(title, description, emotion, momentDate);
            System.out.println("Momento añadido correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listMoments() {
        List<Moment> moments = controller.listMoments();
        if (moments.isEmpty()) {
            System.out.println("No hay momentos registrados todavía.");
            return;
        }
        for (Moment moment : moments) {
            printMoment(moment);
        }
    }

    private void printMoment(Moment moment) {
        System.out.println("---");
        System.out.println("Id: " + moment.getId());
        System.out.println("Título: " + moment.getTitle());
        System.out.println("Descripción: " + moment.getDescription());
        System.out.println("Emoción: " + moment.getEmotion().getDisplayName());
        System.out.println("Fecha: " + moment.getMomentDate().format(DATE_FORMAT));
    }

    private void deleteMoment() {
        Integer id = readId("Id del momento a eliminar: ");
        if (id == null) {
            return;
        }
        try {
            controller.deleteMoment(id);
            System.out.println("Momento eliminado correctamente.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private Integer readId(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("El id debe ser un número.");
            return null;
        }
    }

    private Emotion readEmotion() {
        System.out.println("Elige una emoción:");
        Emotion[] emotions = Emotion.values();
        for (int i = 0; i < emotions.length; i++) {
            System.out.println((i + 1) + ". " + emotions[i].getDisplayName());
        }
        System.out.print("Opción: ");
        String input = scanner.nextLine();

        try {
            int index = Integer.parseInt(input) - 1;
            return emotions[index];
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Emoción no válida.");
            return null;
        }
    }

    private LocalDate readDate(String prompt) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        try {
            return LocalDate.parse(input, DATE_FORMAT);
        } catch (DateTimeParseException e) {
            System.out.println("Formato de fecha no válido, debe ser dd/MM/yyyy.");
            return null;
        }
    }
}
