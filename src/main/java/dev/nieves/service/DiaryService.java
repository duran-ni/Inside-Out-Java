package dev.nieves.service;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.repository.InterfaceRepositoryBasicActions;
import java.time.LocalDate;

/**
 * Implementación de la lógica de negocio relacionada con los momentos vividos.
 */
public class DiaryService implements InterfaceDiaryService {

    private final InterfaceRepositoryBasicActions repository;

    public DiaryService(InterfaceRepositoryBasicActions repository) {
        this.repository = repository;
    }

    @Override
    public Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be empty");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }
        if (emotion == null) {
            throw new IllegalArgumentException("Emotion is required");
        }
        if (momentDate == null) {
            throw new IllegalArgumentException("Moment date is required");
        }

        Moment moment = new Moment(title, description, emotion, momentDate);
        return repository.save(moment);
    }
}
