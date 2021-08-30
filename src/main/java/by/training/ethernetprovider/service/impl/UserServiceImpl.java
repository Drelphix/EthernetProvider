package by.training.ethernetprovider.service.impl;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.dao.UserDao;
import by.training.ethernetprovider.model.dao.impl.UserDaoImpl;
import by.training.ethernetprovider.model.entity.User;
import by.training.ethernetprovider.service.UserResultType;
import by.training.ethernetprovider.service.UserService;
import by.training.ethernetprovider.util.PasswordEncryptor;
import by.training.ethernetprovider.validator.CredentialValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

import static by.training.ethernetprovider.service.UserResultType.*;

public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LogManager.getLogger();
    private final UserDao userDao = UserDaoImpl.getInstance();

    @Override
    public UserResultType register(String username, CharSequence password, String email) throws ServiceException {
        try {
            if (!CredentialValidator.validateUsername(username)) {
                return INCORRECT_USERNAME;
            }
            if (!CredentialValidator.validateEmail(email)) {
                return INCORRECT_EMAIL;
            }
            if (!CredentialValidator.validatePassword(password)) {
                return INCORRECT_PASSWORD;
            }

            Optional<User> user = userDao.findUserByUsername(username);
            if (user.isPresent()) {
                return USERNAME_ALREADY_REGISTERED;
            }
            user = userDao.findUserByEmail(email);
            if (user.isPresent()) {
                return EMAIL_ALREADY_REGISTERED;
            }
            if (userDao.save(username, PasswordEncryptor.encode(password), email)) {
                return SUCCESS;
            }
        } catch (DaoException e) {
            LOGGER.error("Can't register username", e);
            throw new ServiceException("Can't register username", e);
        }
        return ERROR;
    }

    @Override
    public UserResultType signIn(String login, CharSequence password) throws ServiceException {
        try {
            if (!CredentialValidator.validateUsername(login) &&
                    !CredentialValidator.validateEmail(login) ||
                    !CredentialValidator.validatePassword(password)) {
                return INCORRECT_CREDENTIALS;
            }
            String encryptedPassword = userDao.findPasswordByUsernameOrEmail(login);
            if (PasswordEncryptor.compare(encryptedPassword, password)) {
                return SUCCESS;
            }
        } catch (DaoException e) {
            LOGGER.error("Can't signIn user", e);
            throw new ServiceException("Can't signIn user", e);
        }
        return INCORRECT_CREDENTIALS;
    }

    @Override
    public boolean activate(String identifier) {
        return false;
    }

    @Override
    public boolean deactivateUser(String username) {
        return false;
    }
}
