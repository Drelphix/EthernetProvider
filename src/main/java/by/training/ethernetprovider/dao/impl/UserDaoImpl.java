package by.training.ethernetprovider.dao.impl;

import by.training.ethernetprovider.connection.ConnectionPool;
import by.training.ethernetprovider.dao.UserDao;
import by.training.ethernetprovider.entity.Role;
import by.training.ethernetprovider.entity.Status;
import by.training.ethernetprovider.entity.User;
import by.training.ethernetprovider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.dao.impl.ColumnName.*;

public class UserDaoImpl implements UserDao { //TODO 18.08.2021 14:39 :
    private static final Logger LOGGER = LogManager.getLogger();
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_USER_BY_ID = "SELECT id_user, name, surname, city, address, login, email, " +
            "balance, roles.role, statuses.status FROM provider.users " +
            "RIGHT JOIN roles ON users.id_role = roles.id_role " +
            "JOIN statuses ON users.id_status = statuses.id_status WHERE id_user = ?";
    private static final String SELECT_ALL_USERS = "SELECT id_user, name, surname, city, address, login, email, " +
            "balance, roles.role, statuses.status FROM provider.users " +
            "RIGHT JOIN roles ON users.id_role = roles.id_role " +
            "JOIN statuses ON users.id_status = statuses.id_status;";
    private static final String INSERT_NEW_USER = "INSERT INTO users (name, surname, city, address, login, " +
            "email, id_role, id_status,  balance, id_user) VALUES ?, ?, ?, ?, ?, ?, ?, ?, ?, ?";
    private static final String SELECT_ID_ROLE_BY_NAME = "SELECT id_role FROM roles WHERE role = ?";
    private static final String SELECT_ID_STATUS_BY_NAME = "SELECT id_status FROM statuses WHERE status = ?";
    private static final String UPDATE_USER_BY_USER = "UPDATE users SET name = ?, surname = ?, city = ?, address = ?," +
            "login = ?, email = ?, id_role = ?, id_status = ? WHERE id_user = ?";
    private static final String UPDATE_PASSWORD_BY_USERNAME = "UPDATE users SET password = ? WHERE login = ?";
    private static final String SELECT_USER_BY_LOGIN = "SELECT id_user, name, surname, city, address, login, email, " +
            "balance, roles.role, statuses.status FROM provider.users "+
            "RIGHT JOIN roles ON users.id_role = roles.id_role "+
            "JOIN statuses ON users.id_status = statuses.id_status WHERE users.login = ?;";
    private static final String DELETE_USER_BY_ID = "DELETE FROM users WHERE id_user = ?";
    private static final String INSERT_NEW_USER_BY_NAME_PASSWORD_EMAIL = "INSERT INTO users (name, password, email, role_id, status_id) VALUES ?, ?, ?, ?, ?";
    private static final String UPDATE_USER_STATUS_BY_EMAIL = "UPDATE users SET id_status = ? WHERE email = ?";


    private static class UserDaoHolder{
        private static final UserDaoImpl instance = new UserDaoImpl();
    }

    public static UserDaoImpl getInstance(){
        return UserDaoHolder.instance;
    }

    @Override
    public Optional<User> getById(int id) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_USER_BY_ID)) {
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = getUser(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find user by id: {}",id, e);
            throw new DaoException("Can't find user by id: " + id, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ALL_USERS)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                users.add(getUser(result));
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get all users.", e);
            throw new DaoException("Can't get all users.", e);
        }
        return users;
    }

    @Override
    public void save(User user) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(INSERT_NEW_USER)) {
            int roleId = getRoleIdByName(user.getRole().getValue());
            int statusId = getStatusIdByName(user.getStatus().getValue());
            setUser(statement, user.getName(), user.getSurname(), user.getCity(), user.getAddress(), user.getLogin(), user.getEmail(), roleId, statusId);
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't save user: {}", user, e);
            throw new DaoException("Can't save user: " + user, e);
        }
    }

    @Override
    public void update(User user) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_USER_BY_USER)){
            int roleId = getRoleIdByName(user.getRole().getValue());
            int statusId = getStatusIdByName(user.getStatus().getValue());
            setUser(statement, user.getName(), user.getSurname(), user.getCity(), user.getAddress(), user.getLogin(), user.getEmail(), roleId, statusId);
            statement.setInt(9, user.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't update user: {}", user, e);
            throw  new DaoException("Can't update user: " + user, e);
        }
    }

    @Override
    public void delete(User user) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(DELETE_USER_BY_ID)){
            statement.setInt(1, user.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't delete user by id: {}", user.getId(), e);
            throw new DaoException("Can't delete user by id: "+user.getId(), e);
        }
    }

    @Override
    public boolean registerUser(String username, String password, String email) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(INSERT_NEW_USER_BY_NAME_PASSWORD_EMAIL)){
            statement.setString(1,username);
            statement.setString(2, password);
            statement.setString(3,email);
            statement.setInt(4, getRoleIdByName(Role.USER.getValue()));
            statement.setInt(5, getStatusIdByName(Status.INACTIVE.getValue()));
            return statement.executeUpdate() != 0;
        } catch (SQLException e){
            LOGGER.error("Can't register user: {}", username, e);
            throw new DaoException("Can't register user: "+ username, e);
        }
    }

    @Override
    public Optional<User> findUserByUsername(String username) throws DaoException {
        User user = null;
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_USER_BY_LOGIN)) {
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                user = getUser(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find user by name: {}", username, e);
            throw new DaoException("Can't find user by name: " + username, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public boolean updatePasswordByUsername(String username, String password) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_PASSWORD_BY_USERNAME)){
            statement.setString(1, password);
            statement.setString(2, username);
            return statement.executeUpdate() != 0;
        } catch (SQLException e){
            LOGGER.error("Can't update password by username: {}", username, e);
            throw new DaoException("Can't update password by username: " + username, e);
        }
    }

    @Override
    public boolean updateStatusByEmail(String email, Status status) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_USER_STATUS_BY_EMAIL)) {
            statement.setInt(1, getStatusIdByName(status.getValue()));
            statement.setString(2, email);
            return statement.executeUpdate() != 0;
        } catch (SQLException e){
            LOGGER.error("Can't update status by email: {}",email, e);
            throw new DaoException("Can't update status by email: " + email, e);
        }
    }

    private User getUser(ResultSet resultSet) throws DaoException {
        try {
            int id = resultSet.getInt(USER_ID_USER);
            String name = resultSet.getString(USER_NAME);
            String surname = resultSet.getString(USER_SURNAME);
            String city = resultSet.getString(USER_CITY);
            String address = resultSet.getString(USER_ADDRESS);
            String login = resultSet.getString(USER_LOGIN);
            String email = resultSet.getString(USER_EMAIL);
            BigDecimal balance = resultSet.getBigDecimal(USER_BALANCE);
            Role role = Role.valueOf(resultSet.getString(ROLE_ROLE));
            Status status = Status.valueOf(resultSet.getString(STATUS_STATUS));
            return new User(id, name, surname, city, address, login, email, balance, role, status);
        } catch (SQLException e) {
            LOGGER.error("Can't get user from result set.", e);
            throw new DaoException("Can't get user from result set.", e);
        }
    }

    private void setUser(PreparedStatement statement, String name, String surname, String city, String address,
                         String login, String email, int roleId, int statusId) throws DaoException {
        try {
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, city);
            statement.setString(4, address);
            statement.setString(5, login);
            statement.setString(6, email);
            statement.setInt(7, roleId);
            statement.setInt(8, statusId);
        } catch (SQLException e) {
            LOGGER.error("Can't set user to statement.", e);
            throw new DaoException("Can't set user to statement.", e);
        }
    }

    private int getRoleIdByName(String name) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ID_ROLE_BY_NAME)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.first();
            return result.getInt(ROLE_ID_ROLE);
        } catch (SQLException e) {
            LOGGER.error("Can't find role by name: {}", name, e);
            throw new DaoException("Can't find role by name: " + name, e);
        }
    }

    private int getStatusIdByName(String name) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ID_STATUS_BY_NAME)) {
            statement.setString(1, name);
            ResultSet result = statement.executeQuery();
            result.first();
            return result.getInt(STATUS_ID_STATUS);
        } catch (SQLException e) {
            LOGGER.error("Can't find status by name: {}", name, e);
            throw new DaoException("Can't find status by name: " + name, e);
        }
    }
}
