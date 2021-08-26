package by.training.ethernetprovider.model.dao;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.entity.Tariff;

import java.util.List;
import java.util.Optional;

public interface TariffDao extends ProviderDao<Tariff> {

    public Optional<Tariff> findTariffByName(String name) throws DaoException;

    public List<Tariff> findNotArchiveTariffs() throws DaoException;
}
