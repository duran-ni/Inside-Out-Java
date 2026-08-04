package dev.nieves.repository;

import dev.nieves.model.Moment;

/**
 * Operaciones de edición, no todas las entidades las necesitan.
 */
public interface InterfaceRepositoryEditableActions {

    Moment update(Integer id, Moment moment);

    boolean delete(Integer id);
}
