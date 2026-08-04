package dev.nieves.repository;

import dev.nieves.model.Moment;
import java.util.List;
import java.util.Optional;

/**
 * Operaciones básicas de cualquier repositorio: guardar y consultar.
 */
public interface InterfaceRepositoryBasicActions {

    Moment save(Moment moment);

    List<Moment> list();

    Optional<Moment> show(Integer id);
}
