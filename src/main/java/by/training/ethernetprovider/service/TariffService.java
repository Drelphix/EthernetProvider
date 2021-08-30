package by.training.ethernetprovider.service;

import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.entity.Tariff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TariffService {
    BigDecimal calculateActualPrice(Tariff tariff);

    Optional<Tariff> findTariffById(int id);

    List<Tariff> findAllNotArchive() throws ServiceException;

    public void setPromotion(Tariff tariff) throws ServiceException;
}
