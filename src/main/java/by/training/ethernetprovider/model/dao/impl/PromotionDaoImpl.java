package by.training.ethernetprovider.model.dao.impl;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.connection.ConnectionPool;
import by.training.ethernetprovider.model.dao.PromotionDao;
import by.training.ethernetprovider.model.entity.Promotion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.model.dao.impl.ColumnName.*;

public class PromotionDaoImpl implements PromotionDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_PROMOTION_BY_ID = "SELECT id_promotion, name, description, discount, " +
            "start_date, end_date FROM promotions WHERE id_promotion = ?";
    private static final String SELECT_ALL_PROMOTIONS = "SELECT id_promotion, name, description, discount, " +
            "start_date, end_date FROM promotions";
    private static final String INSERT_NEW_PROMOTION = "INSERT INTO promotions (name, description, discount, " +
            "start_date, end_date, id_promotion) VALUES ?,?,?,?,?,?";
    private static final String UPDATE_PROMOTION = "UPDATE promotions SET name = ?, description = ?, discount = ?," +
            "start_date = ?, end_date =? WHERE id_promotion = ?";
    private static final String DELETE_PROMOTION = "DELETE FROM promotions WHERE id_promotion = ?";

    public static PromotionDaoImpl getInstance() {
        return PromotionDaoHolder.instance;
    }

    @Override
    public Optional<Promotion> findById(int id) throws DaoException {
        Promotion promotion = null;
        if (id == -1) {
            return Optional.of(new Promotion(id));
        }
        try (Connection connection = connectionPool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_PROMOTION_BY_ID)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                promotion = getPromotion(resultSet);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't find promotion by id: {}", id, e);
            throw new DaoException("Can't find promotion by id: " + id, e);
        }
        return Optional.ofNullable(promotion);
    }

    private static class PromotionDaoHolder{
        private static final PromotionDaoImpl instance = new PromotionDaoImpl();
    }

    @Override
    public List<Promotion> findAll() throws DaoException {
        List<Promotion> promotions = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PROMOTIONS)){
           ResultSet resultSet = statement.executeQuery();
           while (resultSet.next()){
               promotions.add(getPromotion(resultSet));
           }
        } catch (SQLException e){
            LOGGER.error("Can't get all promotions", e);
            throw new DaoException("Can't get all promotions", e);
        }
        return promotions;
    }

    @Override
    public void save(Promotion promotion) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(INSERT_NEW_PROMOTION)){
            setPromotion(statement, promotion.getName(), promotion.getDescription(), promotion.getDiscount(),
                    promotion.getStartDate(), promotion.getEndDate());
            statement.setInt(6,promotion.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't add new promotion", e);
            throw new DaoException("Can't add new promotion", e);
        }
    }

    @Override
    public void update(Promotion promotion) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_PROMOTION)){
            setPromotion(statement, promotion.getName(), promotion.getDescription(), promotion.getDiscount(),
                    promotion.getStartDate(), promotion.getEndDate());
            statement.setInt(6, promotion.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't update promotion", e);
            throw new DaoException("Can't update promotion", e);
        }
    }

    @Override
    public void delete(Promotion promotion) throws DaoException {
        try (PreparedStatement statement = connectionPool.getConnection().prepareStatement(DELETE_PROMOTION)){
            statement.setInt(1, promotion.getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't delete promotion", e);
            throw new DaoException("Can't delete promotion", e);
        }
    }

    private Promotion getPromotion(ResultSet resultSet) throws DaoException {
        try {
            int promotionId = resultSet.getInt(PROMOTION_ID_PROMOTION);
            String name = resultSet.getString(PROMOTION_NAME);
            String description = resultSet.getString(PROMOTION_DESCRIPTION);
            byte discount = resultSet.getByte(PROMOTION_DISCOUNT);
            LocalDate startDate = resultSet.getDate(PROMOTION_START_DATE).toLocalDate();
            LocalDate endDate = resultSet.getDate(PROMOTION_END_DATE).toLocalDate();
            return new Promotion(promotionId, name, description, discount, startDate, endDate);
        } catch (SQLException e){
            LOGGER.error("Can't get promotion from result set", e);
            throw new DaoException("Can't get promotion from result set", e);
        }
    }

    private void setPromotion(PreparedStatement statement, String name, String description, byte discount,
                              LocalDate startDate, LocalDate endDate) throws DaoException {
        try {
            statement.setString(1, name);
            statement.setString(2, description);
            statement.setByte(3, discount);
            statement.setDate(4, Date.valueOf(startDate));
            statement.setDate(5, Date.valueOf(endDate));
        } catch (SQLException e){
            LOGGER.error("Can't set promotion to statement",e);
            throw new DaoException("Can't set promotion to statement",e);
        }
    }
    
}
