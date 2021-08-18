package by.training.ethernetprovider.dao;

import by.training.ethernetprovider.entity.Tariff;
import by.training.ethernetprovider.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface TariffDao extends ProviderDao<Tariff> { //TODO 15.08.2021 15:20 :

    public Optional<Tariff> getTariffByName(String name) throws DaoException;

    public List<Tariff> getNotArchiveTariffs() throws DaoException;
}
