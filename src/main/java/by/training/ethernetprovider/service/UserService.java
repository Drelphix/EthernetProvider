package by.training.ethernetprovider.service;

import by.training.ethernetprovider.exception.ServiceException;
import by.training.ethernetprovider.model.entity.Role;

public interface UserService {

    UserResultType register(String username, CharSequence password, String email) throws ServiceException;

    UserResultType signIn(String username, CharSequence password) throws ServiceException;

    boolean activate(String identifier);

    boolean deactivateUser(String username);

    Role findRoleByUsername(String username) throws ServiceException;
}
