package dev.nieves.service;

import dev.nieves.export.InterfaceMomentExporter;
import dev.nieves.model.Emotion;
import dev.nieves.model.Moment;
import dev.nieves.model.MonthYear;
import dev.nieves.repository.InterfaceRepositoryBasicActions;
import dev.nieves.repository.InterfaceRepositoryEditableActions;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementación de la lógica de negocio relacionada con los momentos vividos.
 */
public class DiaryService implements InterfaceDiaryService {

    private final InterfaceRepositoryBasicActions basicRepository;
    private final InterfaceRepositoryEditableActions editableRepository;
    private final InterfaceMomentExporter exporter;

    /**
     * Crea el servicio de diario, inyectando sus dependencias.
     *
     * @param basicRepository    operaciones básicas de acceso a datos
     * @param editableRepository operaciones de edición de datos
     * @param exporter           componente encargado de exportar los momentos a un
     *                           formato externo
     */
    public DiaryService(InterfaceRepositoryBasicActions basicRepository,
            InterfaceRepositoryEditableActions editableRepository,
            InterfaceMomentExporter exporter) {
        this.basicRepository = basicRepository;
        this.editableRepository = editableRepository;
        this.exporter = exporter;
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
        validateMomentData(title, description, emotion, momentDate);

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

    /**
     * Modifica un momento vivido existente, conservando su id y fecha de creación.
     *
     * @param id          identificador del momento a modificar
     * @param title       nuevo título
     * @param description nueva descripción
     * @param emotion     nueva emoción
     * @param momentDate  nueva fecha del momento
     * @return el momento ya actualizado
     */
    @Override
    public Moment updateMoment(Integer id, String title, String description, Emotion emotion, LocalDate momentDate) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        validateMomentData(title, description, emotion, momentDate);

        Moment existingMoment = getMomentById(id);

        existingMoment.setTitle(title);
        existingMoment.setDescription(description);
        existingMoment.setEmotion(emotion);
        existingMoment.setMomentDate(momentDate);

        return editableRepository.update(id, existingMoment);
    }

    @Override
    public List<Moment> getMomentsByEmotion(Emotion emotion) {
        if (emotion == null) {
            throw new IllegalArgumentException("Emotion is required");
        }
        return basicRepository.list().stream()
                .filter(moment -> moment.getEmotion() == emotion)
                .collect(Collectors.toList());
    }

    @Override
    public List<Moment> getMomentsByMonth(MonthYear monthYear) {
        if (monthYear == null) {
            throw new IllegalArgumentException("Month and year are required");
        }
        return basicRepository.list().stream()
                .filter(moment -> moment.getMomentDate().getMonthValue() == monthYear.getMonth()
                        && moment.getMomentDate().getYear() == monthYear.getYear())
                .collect(Collectors.toList());
    }

    @Override
    public String exportToCsv() {
        List<Moment> moments = basicRepository.list();
        if (moments.isEmpty()) {
            throw new IllegalStateException("There are no moments to export");
        }
        try {
            return exporter.export(moments);
        } catch (IOException e) {
            throw new IllegalStateException("Could not export moments to CSV", e);
        }
    }

    @Override
    public Moment getMomentById(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id is required");
        }
        return basicRepository.show(id)
                .orElseThrow(() -> new IllegalArgumentException("Moment with id " + id + " does not exist"));
    }

    private void validateMomentData(String title, String description, Emotion emotion, LocalDate momentDate) {
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
    }

}
