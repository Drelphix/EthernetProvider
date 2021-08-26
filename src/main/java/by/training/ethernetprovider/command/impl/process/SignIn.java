package by.training.ethernetprovider.command.impl.process;

import by.training.ethernetprovider.command.*;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.dao.impl.UserDaoImpl;
import by.training.ethernetprovider.util.PasswordEncryptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.training.ethernetprovider.command.ServletMessage.INCORRECT_CREDENTIALS;

public class SignIn implements Command {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public Router execute(HttpServletRequest request) {
        String username = request.getParameter(Attribute.USERNAME);
        CharSequence password = request.getParameter(Attribute.PASSWORD);
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute(Attribute.MESSAGE, ServletMessage.NOT_ALL_FIELDS_FILLED);
                return new Router(PagePath.LOGIN_JSP, Router.RouterType.FORWARD);
            }
            String encryptedPassword = userDao.findPasswordByUsernameOrEmail(username);
            if (!encryptedPassword.isEmpty()) {
                if (PasswordEncryptor.compare(encryptedPassword, password)) {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute(Attribute.USERNAME, username);
                    request.setAttribute(Attribute.REDIRECT, CommandType.TARIFF_LIST);
                    return new Router(PagePath.LOGIN_JSP, Router.RouterType.FORWARD);
                }
            }
        } catch (DaoException e) {
        }
        request.setAttribute(Attribute.MESSAGE, INCORRECT_CREDENTIALS);
        return new Router(PagePath.LOGIN_JSP, Router.RouterType.FORWARD);
    }
}
