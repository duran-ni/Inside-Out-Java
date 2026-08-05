package dev.nieves.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Controla el acceso a la aplicación validando una contraseña frente a su hash SHA-256,
 * bloqueando el acceso tras un número máximo de intentos fallidos.
 */
public class PasswordAccessService implements InterfaceAccessService {

    private static final int MAX_ATTEMPTS = 3;
    private static final String HASH_ALGORITHM = "SHA-256";
    private static final String HASHED_PASSWORD =
            "a830a35b2353c07e57d5c6b50841c53cc7ae7a0bcea0a6a7a17ec8129b3269ea";

    private int failedAttempts;

    @Override
    public boolean authenticate(String password) {
        if (isLocked()) {
            throw new IllegalStateException("Maximum number of failed attempts reached");
        }
        if (password != null && hash(password).equals(HASHED_PASSWORD)) {
            return true;
        }
        failedAttempts++;
        return false;
    }

    @Override
    public boolean isLocked() {
        return failedAttempts >= MAX_ATTEMPTS;
    }

    private String hash(String value) {
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            byte[] hashBytes = digest.digest(value.getBytes(StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();
            for (byte b : hashBytes) {
                builder.append(String.format("%02x", b));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(HASH_ALGORITHM + " algorithm not available", e);
        }
    }
}
