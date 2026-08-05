package dev.nieves.service;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.model.MonthYear;
import java.time.LocalDate;
import java.util.List;

/**
 * Reglas de negocio relacionadas con los momentos vividos.
 */
public interface InterfaceDiaryService {

    Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate);

    List<Moment> listMoments();

    void deleteMoment(Integer id);

    Moment updateMoment(Integer id, String title, String description, Emotion emotion, LocalDate momentDate);

    List<Moment> getMomentsByEmotion(Emotion emotion);

    List<Moment> getMomentsByMonth(MonthYear monthYear);

    String exportToCsv();
}
