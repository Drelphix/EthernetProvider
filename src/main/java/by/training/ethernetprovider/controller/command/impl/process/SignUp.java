package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.controller.command.impl.redirect.ToSignIn;
import by.training.ethernetprovider.exception.CommandException;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.service.UserResultType;
import by.training.ethernetprovider.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.training.ethernetprovider.controller.command.AttributeAndParameter.MESSAGE;
import static by.training.ethernetprovider.controller.command.Message.*;

public class SignUp implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String username = request.getParameter(AttributeAndParameter.USERNAME);
        CharSequence password = request.getParameter(AttributeAndParameter.PASSWORD);
        String email = request.getParameter(AttributeAndParameter.EMAIL);
        try {
            var userService = new UserServiceImpl();
            UserResultType result = userService.register(username, password, email);
            switch (result) {
                case INCORRECT_USERNAME -> {
                    request.setAttribute(MESSAGE, INCORRECT_USERNAME);
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                case INCORRECT_EMAIL -> {
                    request.setAttribute(MESSAGE, INCORRECT_EMAIL);
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                case INCORRECT_PASSWORD -> {
                    request.setAttribute(MESSAGE, INCORRECT_PASSWORD);
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                case USERNAME_ALREADY_REGISTERED -> {
                    request.setAttribute(MESSAGE, CURRENT_USERNAME_IS_TAKEN);
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                case EMAIL_ALREADY_REGISTERED -> {
                    request.setAttribute(MESSAGE, CURRENT_EMAIL_IS_TAKEN);
                    return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
                }
                case SUCCESS -> {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute(AttributeAndParameter.USERNAME, username);
                    return new ToSignIn().execute(request);
                }
            }
        } catch (ServiceException e){
            LOGGER.error("Can't execute sign up", e);
            throw new CommandException("Can't execute sign up", e);
        }
        request.setAttribute(MESSAGE, CANNOT_REGISTER_USER);
        return new Router(PagePath.REGISTRATION, Router.RouterType.FORWARD);
    }
}
