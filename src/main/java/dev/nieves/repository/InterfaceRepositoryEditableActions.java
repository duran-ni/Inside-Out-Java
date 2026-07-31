package dev.nieves.repository;

public interface InterfaceRepositoryEditableActions {
    dev.nieves.model.Moment update(Integer id, dev.nieves.model.Moment moment);
    boolean delete(Integer id);
}