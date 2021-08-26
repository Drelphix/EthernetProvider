package by.training.ethernetprovider.command.impl.redirect;

import by.training.ethernetprovider.command.Attribute;
import by.training.ethernetprovider.command.Command;
import by.training.ethernetprovider.command.PagePath;
import by.training.ethernetprovider.command.Router;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


public class LogOut implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(true);
        httpSession.removeAttribute(Attribute.USERNAME);
        return new Router(PagePath.TARIFF_LIST_JSP, Router.RouterType.REDIRECT);
    }
}
