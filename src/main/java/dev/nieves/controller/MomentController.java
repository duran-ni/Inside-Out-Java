package dev.nieves.controller;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.model.MonthYear;
import dev.nieves.service.InterfaceDiaryService;
import java.time.LocalDate;
import java.util.List;

/**
 * Coordina las peticiones de la Vista hacia el Service, sin aplicar reglas de negocio.
 */
public class MomentController {

    private final InterfaceDiaryService diaryService;

    public MomentController(InterfaceDiaryService diaryService) {
        this.diaryService = diaryService;
    }

    public Moment addMoment(String title, String description, Emotion emotion, LocalDate momentDate) {
        return diaryService.addMoment(title, description, emotion, momentDate);
    }

    public List<Moment> listMoments() {
        return diaryService.listMoments();
    }

    public void deleteMoment(Integer id) {
        diaryService.deleteMoment(id);
    }

    public Moment getMomentById(Integer id) {
        return diaryService.getMomentById(id);
    }

    public Moment updateMoment(Integer id, String title, String description, Emotion emotion, LocalDate momentDate) {
        return diaryService.updateMoment(id, title, description, emotion, momentDate);
    }

    public List<Moment> getMomentsByEmotion(Emotion emotion) {
        return diaryService.getMomentsByEmotion(emotion);
    }

    public List<Moment> getMomentsByMonth(MonthYear monthYear) {
        return diaryService.getMomentsByMonth(monthYear);
    }
}
