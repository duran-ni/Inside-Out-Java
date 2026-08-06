package dev.nieves.controller;

import dev.nieves.service.InterfaceDiaryService;

/**
 * Coordina las peticiones de la Vista hacia el Service, sin aplicar reglas de negocio.
 */
public class MomentController {

    private final InterfaceDiaryService diaryService;

    public MomentController(InterfaceDiaryService diaryService) {
        this.diaryService = diaryService;
    }
}
