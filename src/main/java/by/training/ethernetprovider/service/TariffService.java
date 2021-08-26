package by.training.ethernetprovider.service;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.entity.Promotion;
import by.training.ethernetprovider.model.entity.Tariff;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class TariffService {
    private static final MathContext MATH_CONTEXT = new MathContext(5, RoundingMode.HALF_UP);

    public static BigDecimal calculateActualPrice(Tariff tariff) throws DaoException {
        BigDecimal price = tariff.getPrice();
        Promotion promotion = tariff.getPromotion();
        if (promotion != null) {
            BigDecimal promotionDiscount = BigDecimal.valueOf(promotion.getDiscount());
            if (!promotionDiscount.equals(BigDecimal.ZERO)) {
                BigDecimal calculatedDiscount = price.multiply(promotionDiscount);
                calculatedDiscount = calculatedDiscount.divide(BigDecimal.valueOf(100));
                calculatedDiscount = calculatedDiscount.round(MATH_CONTEXT);
                price = price.subtract(calculatedDiscount);
            }
        }
        return price;
    }

}
