package by.training.ethernetprovider.controller.command.impl.redirect;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.entity.Tariff;
import by.training.ethernetprovider.service.TariffService;
import by.training.ethernetprovider.service.impl.TariffServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.Optional;

public class ToTariffConnectCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        try {
            int tariffId = Integer.parseInt(request.getParameter(AttributeAndParameter.ID));
            var tariffService = new TariffServiceImpl();
            Optional<Tariff> tariffOptional = tariffService.findTariffById(tariffId);
            if (tariffOptional.isPresent()) {
                Tariff tariff = tariffOptional.get();
                if (tariff.isArchive()) {
                    return new Router(PagePath.TARIFF_LIST, Router.RouterType.REDIRECT);
                }
                tariffService.setPromotion(tariff);
                BigDecimal discountPrice = tariffService.calculateActualPrice(tariff);
                request.setAttribute(AttributeAndParameter.DISCOUNT_PRICE, discountPrice);
                request.setAttribute(AttributeAndParameter.TARIFF, tariff);
                return new Router(PagePath.CONNECT_TARIFF, Router.RouterType.FORWARD);
            }
        } catch (ServiceException e) {
            LOGGER.error("Can't execute tariff command", e);
        }
        return new Router(PagePath.TARIFF_LIST, Router.RouterType.REDIRECT);
    }
}
