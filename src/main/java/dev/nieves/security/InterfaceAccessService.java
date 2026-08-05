package dev.nieves.security;

/**
 * Contrato para el control de acceso a la aplicación mediante contraseña.
 */
public interface InterfaceAccessService {

    boolean authenticate(String password);

    boolean isLocked();
}
