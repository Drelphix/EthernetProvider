package by.training.ethernetprovider.model.dao.impl;

import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.model.connection.ConnectionPool;
import by.training.ethernetprovider.model.dao.ContractDao;
import by.training.ethernetprovider.model.entity.Contract;
import by.training.ethernetprovider.model.entity.Tariff;
import by.training.ethernetprovider.model.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.model.dao.impl.ColumnName.*;

public class ContractDaoImpl implements ContractDao {
    private static final Logger LOGGER = LogManager.getLogger();
    private ConnectionPool connectionPool = ConnectionPool.getInstance();
    private static final String SELECT_CONTRACT_BY_ID = "SELECT id_contract, start_date, end_date, " +
            "is_active, id_tariff, id_user FROM contracts WHERE id=?";
    private static final String SELECT_ALL_CONTRACTS = "SELECT id_contract, start_date, end_date, " +
            "is_active, id_tariff, id_user FROM contracts";
    private static final String UPDATE_CONTRACT_BY_CONTRACT = "UPDATE contracts SET start_date = ?, end_date = ?, " +
            "is_active = ?, id_tariff = ?, id_user = ? WHERE id = ?";
    private static final String DELETE_CONTRACT_BY_ID = "DELETE FROM contracts WHERE id_contract = ?";
    private static final String INSERT_NEW_CONTRACT = "INSERT INTO contracts (start_date, end_date, " +
            "is_active, id_tariff, id_user) values (?, ?, ?, ?, ?)";

    public static ContractDaoImpl getInstance() {
        return ContractDaoHolder.instance;
    }

    @Override
    public void save(Contract contract) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(INSERT_NEW_CONTRACT)){
            setContract(statement, contract.getStartDate(), contract.getEndDate(),
                    contract.isActive(),
                    contract.getTariff().getId(), contract.getUser().getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Can't insert new contract: {}", contract, e);
            throw new DaoException("Can't insert new contract: "+contract+'.', e);
        }
    }

    @Override
    public Optional<Contract> findById(int id) throws DaoException {
        Contract contract = null;
        try(Connection connection = connectionPool.getConnection();
            PreparedStatement statement = connection.prepareStatement(SELECT_CONTRACT_BY_ID)){
            statement.setInt(1, id);
            ResultSet result = statement.executeQuery();
            if(result.next()) {
                contract = getContract(result);
            }
        } catch (SQLException e) {
            LOGGER.error("Can't get contract by id: {} ", id, e);
            throw new DaoException("Can't get contract by id: " + id + '.', e);
        }
        return Optional.ofNullable(contract);
    }



    @Override
    public List<Contract> findAll() throws DaoException {
        List<Contract> contracts = new ArrayList<>();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ALL_CONTRACTS);
            ResultSet result = statement.executeQuery()){
            while (result.next()){
                contracts.add(getContract(result));
            }
        } catch (SQLException e){
            LOGGER.error("Can't get all contracts.",e);
            throw new DaoException("Can't get all contracts.",e);
        }
        return contracts;
    }

    @Override
    public void update(Contract contract) throws DaoException {
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(UPDATE_CONTRACT_BY_CONTRACT)){
            statement.setInt(6, contract.getId());
            setContract(statement, contract.getStartDate(),
                    contract.getEndDate(), contract.isActive(),
                    contract.getTariff().getId(), contract.getUser().getId());
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't update contract: {}.", contract, e);
            throw new DaoException("Can't update contract: " + contract +'.', e);
        }
    }

    private Contract getContract(ResultSet result) throws DaoException {
        try {
            int contractId = result.getInt(CONTRACT_ID_CONTRACT);
            int userId = result.getInt(CONTRACT_ID_USER);
            int tariffId = result.getInt(CONTRACT_ID_TARIFF);
            LocalDate startDate = result.getDate(CONTRACT_START_DATE).toLocalDate();
            LocalDate endDate = result.getDate(CONTRACT_END_DATE).toLocalDate();
            boolean isActive = result.getBoolean(CONTRACT_IS_ACTIVE);
            return new Contract(contractId, startDate, endDate, new Tariff(tariffId), isActive, new User(userId));
        } catch (SQLException e){
            LOGGER.error("Can't get contract from result set.",e);
            throw new DaoException("Can't get contract from result set.",e);
        }
    }

    @Override
    public void delete(Contract contract) throws DaoException {
        int contractId = contract.getId();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(DELETE_CONTRACT_BY_ID)){
            statement.setInt(1, contractId);
            statement.executeUpdate();
        } catch (SQLException e){
            LOGGER.error("Can't delete user by id: {}", contractId, e);
            throw new DaoException("Can't delete user by id: " + contractId + '.', e);
        }
    }

    private void setContract(PreparedStatement statement, LocalDate startDate, LocalDate endDate,
                             boolean isActive, int tariffId, int userId) throws DaoException {
        try {
            statement.setDate(1, Date.valueOf(startDate));
            statement.setDate(2, Date.valueOf(endDate));
            statement.setBoolean(3, isActive);
            statement.setInt(4, tariffId);
            statement.setInt(5, userId);
        } catch (SQLException e) {
            LOGGER.error("Can't set prepared statement with contract", e);
            throw new DaoException("Can't set prepared statement with contract", e);
            }
    }

    private static class ContractDaoHolder {
        private static final ContractDaoImpl instance = new ContractDaoImpl();
    }
}
