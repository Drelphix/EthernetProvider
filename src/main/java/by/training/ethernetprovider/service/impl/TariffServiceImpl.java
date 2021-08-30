package by.training.ethernetprovider.service.impl;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.dao.PromotionDao;
import by.training.ethernetprovider.model.dao.TariffDao;
import by.training.ethernetprovider.model.dao.impl.PromotionDaoImpl;
import by.training.ethernetprovider.model.dao.impl.TariffDaoImpl;
import by.training.ethernetprovider.model.entity.Promotion;
import by.training.ethernetprovider.model.entity.Tariff;
import by.training.ethernetprovider.service.TariffService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TariffServiceImpl implements TariffService {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final MathContext MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);
    private final TariffDao tariffDao = TariffDaoImpl.getInstance();
    private final PromotionDao promotionDao = PromotionDaoImpl.getInstance();

    @Override
    public Optional<Tariff> findTariffById(int id) {
        Optional<Tariff> tariff = null;
        try {
            tariff = tariffDao.findById(id);
        } catch (DaoException e) {
            LOGGER.error("Can't findTariffById", e);
        }
        return tariff;
    }

    public List<Tariff> findAllNotArchive() throws ServiceException {
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = tariffDao.findNotArchiveTariffs();
        } catch (DaoException e) {
            LOGGER.error("Can't findAllNotArchive tariffs", e);
            throw new ServiceException("Can't findAllNotArchive tariffs", e);
        }
        return tariffs;
    }

    public void setPromotion(Tariff tariff) throws ServiceException {
        Optional<Promotion> promotionOptional = null;
        try {
            promotionOptional = promotionDao.findById(tariff.getPromotion().getId());
            if (promotionOptional.isPresent()) {
                Promotion promotion = promotionOptional.get();
                tariff.setPromotion(promotion);
            }
        } catch (DaoException e) {
            LOGGER.error("Can't set promotion to tariff", e);
            throw new ServiceException("Can't set promotion to tariff", e);
        }
    }

    public BigDecimal calculateActualPrice(Tariff tariff) {
        BigDecimal price = tariff.getPrice();
        Promotion promotion = tariff.getPromotion();
        if (promotion != null) {
            BigDecimal promotionDiscount = BigDecimal.valueOf(promotion.getDiscount());
            if (!promotionDiscount.equals(BigDecimal.ZERO)) {
                BigDecimal calculatedDiscount = price.multiply(promotionDiscount);
                calculatedDiscount = calculatedDiscount.divide(BigDecimal.valueOf(100),MATH_CONTEXT);
                price = price.subtract(calculatedDiscount);
            }
        }
        return price;
    }

}
