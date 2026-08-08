package dev.nieves.view;

import com.google.inject.Inject;
import dev.nieves.security.InterfaceAccessService;
import java.util.Scanner;

/**
 * Gestiona la pantalla de acceso por contraseña, previa al menú principal.
 */
public class AccessView {

    private final InterfaceAccessService accessService;
    private final Scanner scanner;

    @Inject
    public AccessView(InterfaceAccessService accessService, Scanner scanner) {
        this.accessService = accessService;
        this.scanner = scanner;
    }

    /**
     * Solicita la contraseña hasta que se acierte o se agoten los intentos.
     *
     * @return {@code true} si el acceso fue concedido, {@code false} si se agotaron los intentos
     */
    public boolean login() {
        while (!accessService.isLocked()) {
            System.out.print("Introduce la contraseña: ");
            String password = scanner.nextLine();
            if (accessService.authenticate(password)) {
                System.out.println("Acceso concedido.");
                return true;
            }
            System.out.println("Contraseña incorrecta.");
        }
        System.out.println("Número máximo de intentos alcanzado. Cerrando la aplicación.");
        return false;
    }
}
