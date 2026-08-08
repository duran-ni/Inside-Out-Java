package dev.nieves.config;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import dev.nieves.export.CsvMomentExporter;
import dev.nieves.export.InterfaceMomentExporter;
import dev.nieves.repository.InMemoryDiaryRepository;
import dev.nieves.repository.InterfaceRepositoryBasicActions;
import dev.nieves.repository.InterfaceRepositoryEditableActions;
import dev.nieves.security.InterfaceAccessService;
import dev.nieves.security.PasswordAccessService;
import dev.nieves.service.DiaryService;
import dev.nieves.service.InterfaceDiaryService;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * Configura las dependencias de la aplicación para el contenedor de Guice.
 */
public class DiaryModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(InterfaceRepositoryBasicActions.class).to(InMemoryDiaryRepository.class);
        bind(InterfaceRepositoryEditableActions.class).to(InMemoryDiaryRepository.class);
        bind(InterfaceDiaryService.class).to(DiaryService.class);
        bind(InterfaceAccessService.class).to(PasswordAccessService.class);
    }

    @Provides
    @Singleton
    InMemoryDiaryRepository provideRepository() {
        return new InMemoryDiaryRepository();
    }

    @Provides
    @Singleton
    InterfaceMomentExporter provideExporter() {
        return new CsvMomentExporter(Path.of("exports"));
    }

    @Provides
    @Singleton
    Scanner provideScanner() {
        return new Scanner(System.in, StandardCharsets.UTF_8);
    }
}
