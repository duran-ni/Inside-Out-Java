package dev.nieves.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Representa un momento vivido registrado por el usuario en su diario.
 * El id lo asigna el repositorio al guardar el momento, no esta clase.
 */
public class Moment {
    private Integer id;
    private String title;
    private String description;
    private Emotion emotion;
    private LocalDate momentDate;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Crea un nuevo momento vivido con los datos proporcionados por el usuario.
     *
     * @param title título del momento
     * @param description descripción del momento
     * @param emotion emoción asociada
     * @param momentDate fecha en la que ocurrió el momento
     */
    public Moment(String title, String description, Emotion emotion, LocalDate momentDate) {
        this.title = title;
        this.description = description;
        this.emotion = emotion;
        this.momentDate = momentDate;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public Integer getId() {
        return id;
    }

    // Solo el repositorio debe llamar a este setter, al guardar el momento por
    // primera vez
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.updatedAt = LocalDateTime.now();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
        this.updatedAt = LocalDateTime.now();
    }

    public Emotion getEmotion() {
        return emotion;
    }

    public void setEmotion(Emotion emotion) {
        this.emotion = emotion;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDate getMomentDate() {
        return momentDate;
    }

    public void setMomentDate(LocalDate momentDate) {
        this.momentDate = momentDate;
        this.updatedAt = LocalDateTime.now();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}
