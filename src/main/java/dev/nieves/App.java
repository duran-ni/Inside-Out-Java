package dev.nieves;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.nieves.config.DiaryModule;
import dev.nieves.view.AccessView;
import dev.nieves.view.ConsoleView;

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
        Injector injector = Guice.createInjector(new DiaryModule());

        AccessView accessView = injector.getInstance(AccessView.class);
        if (!accessView.login()) {
            return;
        }

        ConsoleView consoleView = injector.getInstance(ConsoleView.class);
        consoleView.start();
    }
}
