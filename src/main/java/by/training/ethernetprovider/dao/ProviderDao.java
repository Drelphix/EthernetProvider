package by.training.ethernetprovider.dao;

import by.training.ethernetprovider.entity.ProviderEntity;
import by.training.ethernetprovider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ProviderDao<T extends ProviderEntity> {

    Optional<T> getById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    void save(T entity) throws DaoException;

    void update(T entity) throws DaoException;

    void delete(T entity) throws DaoException;
}
