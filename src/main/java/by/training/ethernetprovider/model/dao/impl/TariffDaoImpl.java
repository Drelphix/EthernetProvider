package by.training.ethernetprovider.model.dao.impl;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.connection.ConnectionPool;
import by.training.ethernetprovider.model.dao.TariffDao;
import by.training.ethernetprovider.model.entity.Promotion;
import by.training.ethernetprovider.model.entity.Tariff;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.model.dao.impl.ColumnName.*;

public class TariffDaoImpl implements TariffDao {
    private static final Logger logger = LogManager.getLogger();
    private static final String SELECT_ALL_TARIFFS="SELECT id_tariff, name, description, price, is_archive, "+
            "id_promotion FROM tariffs";
    private static final String SELECT_ALL_NOT_ARCHIVE_TARIFFS="SELECT  id_tariff, name, description, price, " +
            "id_promotion, is_archive FROM tariffs where is_archive = false";
    private static final String SELECT_TARIFF_BY_NAME = "SELECT id_tariff, name, description, price, is_archive, " +
            "id_promotion FROM tariffs WHERE name = ?";
    private static final String SELECT_TARIFF_BY_ID = "SELECT id_tariff, name, description, price, is_archive, " +
            "id_promotion FROM tariffs WHERE id_tariff = ?";
    private static final String INSERT_NEW_TARIFF = "INSERT INTO tariffs ( name, description,  " +
            "is_archive, price, id_promotion, id_tariff) VALUES ?,?,?,?,?,?";
    private static final String UPDATE_TARIFF_BY_TARIFF = "UPDATE tariffs SET name = ?, description = ?, is_archive = ?," +
            " price = ?, id_promotion = ? WHERE id_tariff=?";
    private static final String DELETE_TARIFF_BY_ID = "DELETE FROM tariffs where id_tariff = ?";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static class TariffDaoHolder{
        private static final TariffDaoImpl instance = new TariffDaoImpl();
    }

    public static TariffDaoImpl getInstance(){
        return TariffDaoHolder.instance;
    }

    @Override
    public Optional<Tariff> findById(int id) throws DaoException {
        Tariff tariff = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TARIFF_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                tariff = getTariff(resultSet);
            }
        } catch (SQLException e) {
            logger.error("Can't get tariff by id ", e);
            throw new DaoException("Can't get tariff by id ", e);
        }
        return Optional.ofNullable(tariff);
    }

    @Override
    public List<Tariff> findAll() throws DaoException {
        List<Tariff> tariffs = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_TARIFFS)){
            ResultSet result = statement.executeQuery();
            while (result.next()){
                tariffs.add(getTariff(result));
            }
        } catch (SQLException e){
            logger.error("Can't get all tariffs", e);
            throw new DaoException("Can't get all tariffs", e);
        }
        return tariffs;
    }

    @Override
    public void save(Tariff tariff) throws DaoException {
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(INSERT_NEW_TARIFF)){
            setTariff(statement, tariff.getName(), tariff.getDescription(), tariff.isArchive(), tariff.getPrice(), tariff.getPromotion().getId());
            statement.setInt(6, tariff.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("Can't add new tariff", e);
            throw new DaoException("Can't add new tariff", e);
        }
    }

    @Override
    public void update(Tariff tariff) throws DaoException {
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(UPDATE_TARIFF_BY_TARIFF)){
            setTariff(statement, tariff.getName(), tariff.getDescription(), tariff.isArchive(), tariff.getPrice(), tariff.getPromotion().getId());
            statement.setInt(6, tariff.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("Can't update tariff", e);
            throw new DaoException("Can't update tariff", e);
        }
    }

    @Override
    public void delete(Tariff tariff) throws DaoException {
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE_TARIFF_BY_ID)){
            statement.setInt(1, tariff.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            logger.error("Can't delete tariff", e);
            throw new DaoException("Can't delete tariff", e);
        }
    }
    
    @Override
    public Optional<Tariff> findTariffByName(String name) throws DaoException {
        Tariff tariff = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_TARIFF_BY_NAME)) {
            statement.setString(1, name);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.first()) {
                tariff = getTariff(resultSet);
            }
        } catch (SQLException e){
            logger.error("Can't get tariff by name ", e);
            throw new DaoException("Can't get tariff by name ", e);
        }
        return Optional.ofNullable(tariff);
    }


    @Override
    public List<Tariff> findNotArchiveTariffs() throws DaoException {
        List<Tariff> tariffs = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_NOT_ARCHIVE_TARIFFS)){
            ResultSet result = statement.executeQuery();
            while (result.next()){
                tariffs.add(getTariff(result));
            }
        } catch (SQLException e) {
            logger.error("Can't get not archive tariffs", e);
            throw new DaoException("Can't get not archive tariffs", e);
        }
        return tariffs;
    }

    private Tariff getTariff(ResultSet result) throws DaoException {
        try {
            int id = result.getInt(TARIFF_ID_TARIFF);
            int promotionId = result.getInt(TARIFF_ID_PROMOTION);
            if (result.wasNull()) {
                promotionId = -1;
            }
            Promotion promotion = new Promotion(promotionId);
            String name = result.getString(TARIFF_NAME);
            String description = result.getString(TARIFF_DESCRIPTION);
            BigDecimal price = result.getBigDecimal(TARIFF_PRICE);
            boolean isArchive = result.getBoolean(TARIFF_IS_ARCHIVE);
            return new Tariff(id, name, description, price, isArchive, promotion);
        } catch (SQLException e){
            logger.error("Can't get tariff from result set", e);
            throw new DaoException("Can't get tariff from result set", e);
        }
    }

    private void setTariff(PreparedStatement statement, String name, String description, boolean isArchive,
                           BigDecimal price, int promotionId) throws DaoException {
        try {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setBoolean(3, isArchive);
            statement.setBigDecimal(4, price);
            statement.setInt(5, promotionId);
        } catch (SQLException e){
            logger.error("Can't set tariff", e);
            throw new DaoException("Can't set tariff", e);
        }
    }

}
