package by.training.ethernetprovider.controller.command.impl.redirect;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.exception.CommandException;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.entity.Tariff;
import by.training.ethernetprovider.service.impl.TariffServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.util.List;

public class ToTariffsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        var tariffService = new TariffServiceImpl();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffService.findAllNotArchive();
            for(Tariff tariff: tariffs) {
                tariffService.setPromotion(tariff);
                BigDecimal discountPrice = tariffService.calculateActualPrice(tariff);
                tariff.setDiscountPrice(discountPrice);
            }
        } catch (ServiceException e) {
            LOGGER.error("Can't execute tariff list", e);
            throw new CommandException("Can't execute tariff list", e);
        }
        request.setAttribute(AttributeAndParameter.TARIFFS, tariffs);
        return new Router(PagePath.TARIFF_LIST, Router.RouterType.FORWARD);
    }
}
