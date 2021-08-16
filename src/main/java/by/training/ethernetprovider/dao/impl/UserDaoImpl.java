package by.training.ethernetprovider.dao.impl;

import by.training.ethernetprovider.connection.ConnectionPool;
import by.training.ethernetprovider.dao.UserDao;
import by.training.ethernetprovider.entity.Role;
import by.training.ethernetprovider.entity.Status;
import by.training.ethernetprovider.entity.User;
import static by.training.ethernetprovider.dao.impl.ColumnName.*;

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

public class UserDaoImpl implements UserDao { //TODO 15.08.2021 14:39 :
    private static final Logger LOGGER = LogManager.getLogger();
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_USER_BY_ID = "SELECT id_user, name, surname, city, address, login, email, " +
            "balance, roles.role, statuses.status FROM provider.users " +
            "RIGHT JOIN roles ON users.id_role = roles.id_role " +
            "JOIN statuses ON users.id_status = statuses.id_status WHERE id_user = ?";
    private static final String SELECT_ALL_USERS = "SELECT id_user, name, surname, city, address, login, email, " +
            "balance, roles.role, statuses.status FROM provider.users " +
            "RIGHT JOIN roles ON users.id_role = roles.id_role " +
            "JOIN statuses ON users.id_status = statuses.id_status;";
    @Override
    public Optional<User> getById(int id) throws DaoException {
        User user = null;
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_USER_BY_ID)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()){
                user = getUser(result);
            }
        } catch (SQLException e){
            LOGGER.error("Can't find user by id: " + id, e);
            throw new DaoException("Can't find user by id: "+id, e);
        }
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ALL_USERS)){
            ResultSet result = statement.executeQuery();
            while (result.next()){
                users.add(getUser(result));
            }
        }catch (SQLException e){
            LOGGER.error("Can't get all users.", e);
            throw new DaoException("Can't get all users.", e);
        }
        return null;
    }

    @Override
    public void save(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public void delete(User user) {

    }

    @Override
    public boolean registerUser(String username, String password, String email) {
        return false;
    }

    @Override
    public User findUserByName(String username) {
        return null;
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
        } catch (SQLException e){
            throw new DaoException("Can't get user from result set.", e);
        }
    }

    private void setUser(PreparedStatement statement, String name, String surname, String city, String address,
                         String login, String email, Role role, Status status) throws DaoException {
        try{
            statement.setString(1, name);
            statement.setString(2, surname);
            statement.setString(3, city);
            statement.setString(4, address);
            statement.setString(5, login);
            statement.setString(6, email);
            statement.setString(7, role.getValue());
            statement.setString(8, status.getValue());
        } catch (SQLException e){
            LOGGER.error("Can't set user to statement.",e);
            throw new DaoException("Can't set user to statement.",e);
        }
    }
}
