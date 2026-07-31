package dev.nieves.service;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import java.time.LocalDate;

/**
 * Reglas de negocio relacionadas con los momentos vividos.
 */
public interface InterfaceDiaryService {
    Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate);
}