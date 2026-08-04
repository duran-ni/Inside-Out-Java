package dev.nieves.service;

import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.repository.InterfaceRepositoryBasicActions;
import dev.nieves.repository.InterfaceRepositoryEditableActions;
import java.time.LocalDate;
import java.util.List;

/**
 * Implementación de la lógica de negocio relacionada con los momentos vividos.
 */
public class DiaryService implements InterfaceDiaryService {

    private final InterfaceRepositoryBasicActions basicRepository;
    private final InterfaceRepositoryEditableActions editableRepository;

    public DiaryService(InterfaceRepositoryBasicActions basicRepository,
            InterfaceRepositoryEditableActions editableRepository) {
        this.basicRepository = basicRepository;
        this.editableRepository = editableRepository;
    }

    /**
     * Crea un nuevo momento vivido con los datos proporcionados por el usuario.
     *
     * @param title       título del momento
     * @param description descripción del momento
     * @param emotion     emoción asociada
     * @param momentDate  fecha en la que ocurrió el momento
     * @return el momento creado, ya con id asignado
     */
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
        return basicRepository.save(moment);
    }

    @Override
    public List<Moment> listMoments() {
        return basicRepository.list();
    }

    @Override
    public void deleteMoment(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        boolean deleted = editableRepository.delete(id);
        if (!deleted) {
            throw new IllegalArgumentException("Moment with id " + id + " does not exist");
        }
    }
}
