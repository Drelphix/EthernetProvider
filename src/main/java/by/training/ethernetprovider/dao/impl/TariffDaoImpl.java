package by.training.ethernetprovider.dao.impl;

import by.training.ethernetprovider.connection.ConnectionPool;
import by.training.ethernetprovider.dao.ContractDao;
import by.training.ethernetprovider.dao.TariffDao;
import by.training.ethernetprovider.entity.Promotion;
import by.training.ethernetprovider.entity.ProviderEntity;
import by.training.ethernetprovider.entity.Tariff;
import by.training.ethernetprovider.exception.DaoException;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static by.training.ethernetprovider.dao.impl.ColumnName.*;

public class TariffDaoImpl implements TariffDao { //TODO 15.08.2021 15:20 :
    @Language("SQL")
    private static final String SELECT_ALL_TARIFFS="SELECT id_tariff, name, description, price, is_archive, id_promotion FROM tariffs ";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    private static class TariffDaoHolder{
        private static final TariffDaoImpl instance = new TariffDaoImpl();
    }

    public static TariffDaoImpl getInstance(){
        return TariffDaoHolder.instance;
    }

    @Override
    public Optional<Tariff> getById(int id) {
        return null;
    }

    @Override
    public List<Tariff> getAll() throws DaoException {
        List<Tariff> tariffs = new ArrayList<>();
        try(PreparedStatement statement = connectionPool.getConnection().prepareStatement(SELECT_ALL_TARIFFS)){
            ResultSet result = statement.executeQuery();
            while (result.next()){
                int tariffId = result.getInt(TARIFF_ID_TARIFF);
                String name = result.getString(TARIFF_NAME);
                String description = result.getString(TARIFF_DESCRIPTION);
                BigDecimal price = result.getBigDecimal(TARIFF_PRICE);
                boolean isArchive = result.getBoolean(TARIFF_IS_ARCHIVE);
                Promotion promotion = new Promotion(result.getInt(TARIFF_ID_PROMOTION));
                tariffs.add(new Tariff(tariffId, name, description, price, isArchive, promotion));
            }
        } catch (SQLException e){
            throw new DaoException("Can't get all tariffs", e);
        }
        return tariffs;
    }

    @Override
    public void save(Tariff tariff) {

    }

    @Override
    public void update(Tariff tariff) {

    }

    @Override
    public void delete(Tariff tariff) {

    }
    
    @Override
    public Tariff getTariffByName(String name) {
        return null;
    }
}
