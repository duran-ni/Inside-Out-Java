package dev.nieves.repository;

import dev.nieves.model.Moment;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementación en memoria del repositorio de momentos vividos.
 * Encapsula el Map interno; ninguna otra capa accede a él directamente.
 */
public class InMemoryDiaryRepository
        implements InterfaceRepositoryBasicActions, InterfaceRepositoryEditableActions {

    private final Map<Integer, Moment> moments = new LinkedHashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);

    @Override
    public Moment save(Moment moment) {
        int id = nextId.getAndIncrement();
        moment.setId(id);
        moments.put(id, moment);
        return moment;
    }

    @Override
    public List<Moment> list() {
        return List.copyOf(moments.values());
    }

    @Override
    public Optional<Moment> show(Integer id) {
        return Optional.ofNullable(moments.get(id));
    }

    @Override
    public Moment update(Integer id, Moment updatedMoment) {
        if (!moments.containsKey(id)) {
            throw new IllegalArgumentException("Moment with id " + id + " does not exist");
        }
        updatedMoment.setId(id);
        moments.put(id, updatedMoment);
        return updatedMoment;
    }

    @Override
    public boolean delete(Integer id) {
        return moments.remove(id) != null;
    }
}
