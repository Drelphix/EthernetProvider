package by.training.ethernetprovider.command.impl.process;

import by.training.ethernetprovider.command.Attribute;
import by.training.ethernetprovider.command.Command;
import by.training.ethernetprovider.command.PagePath;
import by.training.ethernetprovider.command.Router;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.dao.impl.PromotionDaoImpl;
import by.training.ethernetprovider.model.dao.impl.TariffDaoImpl;
import by.training.ethernetprovider.model.entity.Promotion;
import by.training.ethernetprovider.model.entity.Tariff;
import by.training.ethernetprovider.service.TariffService;
import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Optional;

public class FindTariff implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        try {
            int tariffId = Integer.parseInt(request.getParameter(Attribute.ID));
            TariffDaoImpl tariffDao = TariffDaoImpl.getInstance();
            PromotionDaoImpl promotionDao = PromotionDaoImpl.getInstance();
            Optional<Tariff> tariffOptional = tariffDao.findById(tariffId);
            if (tariffOptional.isPresent()) {
                Tariff tariff = tariffOptional.get();
                if (tariff.isArchive()) {
                    return new Router(PagePath.TARIFF_LIST_JSP, Router.RouterType.REDIRECT);
                }
                Optional<Promotion> promotionOptional = promotionDao.findById(tariff.getPromotion().getId());
                if (promotionOptional.isPresent()) {
                    Promotion promotion = promotionOptional.get();
                    tariff.setPromotion(promotion);
                    BigDecimal discountPrice = TariffService.calculateActualPrice(tariff);
                    request.setAttribute(Attribute.DISCOUNT_PRICE, discountPrice);
                }
                request.setAttribute(Attribute.TARIFF, tariff);
                return new Router(PagePath.CONNECT_TARIFF_JSP, Router.RouterType.FORWARD);
            }
        } catch (DaoException e) {
            e.printStackTrace();
        }
        return new Router(PagePath.TARIFF_LIST_JSP, Router.RouterType.REDIRECT);
    }
}
