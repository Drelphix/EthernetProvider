package by.training.ethernetprovider.service;

import by.training.ethernetprovider.exception.ServiceException;

public interface UserService {

    UserResultType register(String username, CharSequence password, String email) throws ServiceException;

    UserResultType signIn(String username, CharSequence password) throws ServiceException;

    boolean activate(String identifier);

    boolean deactivateUser(String username);
}
