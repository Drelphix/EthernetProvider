package by.training.ethernetprovider.dao.impl;

import by.training.ethernetprovider.connection.ConnectionPool;
import by.training.ethernetprovider.dao.ContractDao;
import by.training.ethernetprovider.entity.Contract;
import by.training.ethernetprovider.entity.Tariff;
import by.training.ethernetprovider.entity.User;;
import by.training.ethernetprovider.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.dao.impl.ColumnName.*;

public class ContractDaoImpl implements ContractDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_CONTRACT_BY_ID = "SELECT id_contract, start_date, end_date, discount, " +
            "is_active, id_tariff, id_user FROM contracts WHERE id=?";
    private static final String SELECT_ALL_CONTRACTS = "SELECT id_contract, start_date, end_date, discount, " +
            "is_active, id_tariff, id_user FROM contracts";
    private static final String UPDATE_CONTRACT_BY_CONTRACT = "UPDATE contracts SET start_date = ?, end_date = ?, " +
            "discount = ?, is_active = ?, id_tariff = ?, id_user = ? WHERE id = ?";
    private static final String DELETE_CONTRACT_BY_ID = "DELETE FROM contracts WHERE id_contract = ?";
    private static final String INSERT_NEW_CONTRACT = "INSERT INTO contracts (start_date, end_date, discount, " +
            "is_active, id_tariff, id_user) values ?, ?, ?, ?, ?, ?";

    @Override
    public Optional<Contract> getById(int id) throws DaoException {
        Contract contract = null;
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_CONTRACT_BY_ID)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                contract = getContract(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get contract by id: " + id + '.', e);
            throw new DaoException("Can't get contract by id: " + id + '.', e);
        }
        return Optional.ofNullable(contract);
    }

    @Override
    public List<Contract> getAll() throws DaoException {
        List<Contract> contracts = new ArrayList<>();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ALL_CONTRACTS);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                contracts.add(getContract(result));
            }
        } catch (SQLException e){
            throw new DaoException("Can't get all contracts.",e);
        }
        return contracts;
    }

    @Override
    public void save(Contract contract) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(INSERT_NEW_CONTRACT)){
            setContract(statement, contract.getStartDate(), contract.getEndDate(),
                        contract.getDiscount(), contract.isActive(),
                        contract.getTariff().getId(), contract.getUser().getId());
            statement.executeQuery();
        } catch (SQLException e) {
            throw new DaoException("Can't insert new contract: "+contract+'.', e);
        }
    }

    @Override
    public void update(Contract contract) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_CONTRACT_BY_CONTRACT)){
            statement.setInt(7, contract.getId());
            setContract(statement, contract.getStartDate(),
                        contract.getEndDate(), contract.getDiscount(), contract.isActive(),
                        contract.getTariff().getId(), contract.getUser().getId());
            statement.executeQuery();
        } catch (SQLException e){
            throw new DaoException("Can't update contract: " + contract +'.', e);
        }
    }

    @Override
    public void delete(Contract contract) throws DaoException {
        int contractId = contract.getId();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(DELETE_CONTRACT_BY_ID)){
            statement.setInt(1, contractId);
            statement.executeQuery();
        } catch (SQLException e){
            throw new DaoException("Can't delete user by id: " + contractId + '.', e);
        }
    }


    private Contract getContract(ResultSet result) throws DaoException {
        try {
            int contractId = result.getInt(CONTRACT_ID_CONTRACT);
            int userId = result.getInt(CONTRACT_ID_USER);
            int tariffId = result.getInt(CONTRACT_ID_TARIFF);
            LocalDate startDate = result.getDate(CONTRACT_START_DATE).toLocalDate();
            LocalDate endDate = result.getDate(CONTRACT_END_DATE).toLocalDate();
            byte discount = result.getByte(CONTRACT_DISCOUNT);
            boolean isActive = result.getBoolean(CONTRACT_IS_ACTIVE);
            return new Contract(contractId, startDate,endDate,discount, new Tariff(tariffId),isActive, new User(userId));
        } catch (SQLException e){
            throw new DaoException("Can't get contract from result set.",e);
        }
    }

    private void setContract(PreparedStatement statement, LocalDate startDate, LocalDate endDate, byte discount,
                             boolean isActive, int tariffId, int userId) throws DaoException {
            try {
                statement.setDate(1, Date.valueOf(startDate));
                statement.setDate(2, Date.valueOf(endDate));
                statement.setByte(3, discount);
                statement.setBoolean(4, isActive);
                statement.setInt(5, tariffId);
                statement.setInt(6, userId);
            } catch (SQLException e){
                throw new DaoException("Can't set prepared statement with contract", e);
            }
    }
}
