package by.training.ethernetprovider.dao;

import by.training.ethernetprovider.entity.Tariff;

public interface TariffDao extends ProviderDao<Tariff> { //TODO 15.08.2021 15:20 :

    public Tariff getTariffByName(String name);
}
