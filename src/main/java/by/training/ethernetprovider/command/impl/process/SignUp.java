package by.training.ethernetprovider.command.impl.process;

import by.training.ethernetprovider.command.*;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.dao.impl.UserDaoImpl;
import by.training.ethernetprovider.model.entity.User;
import by.training.ethernetprovider.util.PasswordEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.training.ethernetprovider.command.Attribute.MESSAGE;
import static by.training.ethernetprovider.command.ServletMessage.*;

public class SignUp implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String username = request.getParameter(Attribute.USERNAME);
        CharSequence password = request.getParameter(Attribute.PASSWORD);
        String email = request.getParameter(Attribute.EMAIL);
        Optional<User> user;
        try {
            if (username.isEmpty() || password.isEmpty() || email.isEmpty()) {
                request.setAttribute(MESSAGE, NOT_ALL_FIELDS_FILLED);
                return new Router(PagePath.REGISTRATION_JSP, Router.RouterType.FORWARD);
            }
            user = userDao.findUserByUsername(username);
            if (user.isPresent()) {
                request.setAttribute(MESSAGE, CURRENT_USERNAME_IS_TAKEN);
                return new Router(PagePath.REGISTRATION_JSP, Router.RouterType.FORWARD);
            }
            user = userDao.findUserByEmail(email);
            if (user.isPresent()) {
                request.setAttribute(MESSAGE, CURRENT_EMAIL_IS_TAKEN);
                return new Router(PagePath.REGISTRATION_JSP, Router.RouterType.FORWARD);
            }
            userDao.signUpUser(username, PasswordEncryptor.encode(password), email);
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute(Attribute.USERNAME, username);
            return new Router(PagePath.REGISTRATION_JSP, Router.RouterType.REDIRECT);
        } catch (DaoException e) {
            LOGGER.error(CANNOT_REGISTER_USER, e);
            request.setAttribute(MESSAGE, CANNOT_REGISTER_USER);
            return new Router(PagePath.REGISTRATION_JSP, Router.RouterType.FORWARD);
        }
    }
}
