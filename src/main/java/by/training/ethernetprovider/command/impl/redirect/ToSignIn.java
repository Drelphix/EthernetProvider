package by.training.ethernetprovider.command.impl.redirect;

import by.training.ethernetprovider.command.Command;
import by.training.ethernetprovider.command.PagePath;
import by.training.ethernetprovider.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class ToSignIn implements Command {
    @Override
    public Router execute(HttpServletRequest request) {
        return new Router(PagePath.LOGIN_JSP, Router.RouterType.REDIRECT);
    }
}
