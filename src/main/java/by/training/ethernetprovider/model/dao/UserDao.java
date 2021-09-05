package by.training.ethernetprovider.model.dao;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.entity.Role;
import by.training.ethernetprovider.model.entity.Status;
import by.training.ethernetprovider.model.entity.User;

import java.util.Optional;

public interface UserDao extends ProviderDao<User> {

    boolean save(String username, String password, String email) throws DaoException;

    Optional<User> findUserByUsername(String username) throws DaoException;

    Optional<User> findUserByEmail(String email) throws DaoException;

    String findPasswordByUsernameOrEmail(String parameter) throws DaoException;

    boolean updatePasswordByUsername(String username, String password) throws DaoException;

    boolean updateStatusByEmail(String email, Status status) throws DaoException;

    Optional<Role> findRoleByUsername(String username) throws DaoException;


}
