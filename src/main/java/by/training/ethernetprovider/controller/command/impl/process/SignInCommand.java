package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.controller.command.impl.redirect.ToTariffsCommand;
import by.training.ethernetprovider.exception.CommandException;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.service.UserResultType;
import by.training.ethernetprovider.service.UserService;
import by.training.ethernetprovider.service.impl.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.training.ethernetprovider.controller.command.Message.*;

public class SignInCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String login = request.getParameter(AttributeAndParameter.USERNAME);
        CharSequence password = request.getParameter(AttributeAndParameter.PASSWORD);
        try {
            var userService = new UserServiceImpl();
            UserResultType result = userService.signIn(login, password);
            switch (result) {
                case INCORRECT_CREDENTIALS -> {
                    request.setAttribute(AttributeAndParameter.MESSAGE, INCORRECT_CREDENTIALS);
                    return new Router(PagePath.LOGIN, Router.RouterType.FORWARD);
                }
                case SUCCESS -> {
                    HttpSession httpSession = request.getSession(true);
                    String role = userService.findRoleByUsername(login).getValue();
                    httpSession.setAttribute(AttributeAndParameter.ROLE, role);
                    httpSession.setAttribute(AttributeAndParameter.USERNAME, login);
                    return new ToTariffsCommand().execute(request);
                }
                case USER_BLOCKED -> {
                    request.setAttribute(AttributeAndParameter.MESSAGE, USER_IS_BLOCKED);
                    return new Router(PagePath.LOGIN, Router.RouterType.FORWARD);
                }
                case USER_NOT_CONFIRMED -> {
                    request.setAttribute(AttributeAndParameter.MESSAGE, USER_NOT_CONFIRMED);
                    return new Router(PagePath.LOGIN, Router.RouterType.FORWARD);
                }
            }
        } catch (ServiceException e){
            LOGGER.error("Can't execute sign in command", e);
            throw new CommandException("Can't execute sign in command", e);
        }
        request.setAttribute(AttributeAndParameter.MESSAGE, CANNOT_SIGN_IN_USER);
        return new Router(PagePath.LOGIN, Router.RouterType.FORWARD);

    }
}
