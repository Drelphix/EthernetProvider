package by.training.ethernetprovider.dao.impl;

import by.training.ethernetprovider.connection.ConnectionPool;
import by.training.ethernetprovider.dao.PromotionDao;
import by.training.ethernetprovider.entity.Promotion;
import by.training.ethernetprovider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.dao.impl.ColumnName.*;

public class PromotionDaoImpl implements PromotionDao { //TODO 20.08.2021 15:20 :
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_PROMOTION_BY_ID = "SELECT id_promotion, name, description, discount, " +
            "start_date, end_date FROM promotions WHERE id_promotion = ?";
    private static final String SELECT_ALL_PROMOTIONS = "SELECT id_promotion, name, description, discount, " +
            "start_date, end_date FROM promotions";

    private static class PromotionDaoHolder{
        private static final ContractDaoImpl instance = new ContractDaoImpl();
    }

    public static ContractDaoImpl getInstance(){
        return PromotionDaoHolder.instance;
    }

    @Override
    public Optional<Promotion> findById(int id) throws DaoException {
        Promotion promotion = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_PROMOTION_BY_ID)){
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.first()){
                promotion = getPromotion(resultSet);
            }
        } catch (SQLException e){
            LOGGER.error("Can't find promotion by id: {}",id, e);
            throw new DaoException("Can't find promotion by id: " + id, e);
        }
        return Optional.ofNullable(promotion);
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
    public void save(Promotion promotion) {

    }

    @Override
    public void update(Promotion promotion) {

    }

    @Override
    public void delete(Promotion promotion) {

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
    
}
