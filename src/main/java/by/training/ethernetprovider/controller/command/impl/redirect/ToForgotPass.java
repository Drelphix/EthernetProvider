package by.training.ethernetprovider.controller.command.impl.redirect;

import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class ToForgotPass implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new Router(PagePath.FORGOT_PASS, Router.RouterType.REDIRECT);
    }
}
