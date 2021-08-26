package by.training.ethernetprovider.command.impl.process;

import by.training.ethernetprovider.command.Attribute;
import by.training.ethernetprovider.command.Command;
import by.training.ethernetprovider.command.PagePath;
import by.training.ethernetprovider.command.Router;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.dao.impl.TariffDaoImpl;
import by.training.ethernetprovider.model.entity.Tariff;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public class TariffList implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        TariffDaoImpl tariffDao = TariffDaoImpl.getInstance();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffDao.findNotArchiveTariffs();
        } catch (DaoException e) {
        }
        request.setAttribute(Attribute.TARIFFS, tariffs);
        return new Router(PagePath.TARIFF_LIST_JSP, Router.RouterType.FORWARD);
    }
}
