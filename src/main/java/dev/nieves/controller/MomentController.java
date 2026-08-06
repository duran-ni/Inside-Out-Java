package dev.nieves.controller;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
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
}
