package by.training.ethernetprovider.dao;

import by.training.ethernetprovider.entity.Status;
import by.training.ethernetprovider.entity.User;
import by.training.ethernetprovider.exception.DaoException;

import java.util.Optional;

public interface UserDao extends ProviderDao<User> { //TODO 15.08.2021 15:20 :

    boolean registerUser(String username, String password, String email) throws DaoException;

    Optional<User> findUserByName(String username) throws DaoException;

    boolean updatePasswordByUsername(String username, String password) throws DaoException;

    boolean updateStatusByEmail(String email, Status status) throws DaoException;
}
