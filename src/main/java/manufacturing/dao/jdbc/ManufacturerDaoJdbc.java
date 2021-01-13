package manufacturing.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import manufacturing.dao.ManufacturerDao;
import manufacturing.exception.DataProcessingException;
import manufacturing.injections.Dao;
import manufacturing.model.Manufacturer;
import manufacturing.util.ConnectionController;

@Dao
public class ManufacturerDaoJdbc implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        try (Connection connection = ConnectionController.getConnection()) {
            String query = "INSERT INTO manufacturers (name, country) VALUES (?, ?)";
            PreparedStatement insert =
                    connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            insert.setString(1, manufacturer.getName());
            insert.setString(2, manufacturer.getCountry());
            insert.executeUpdate();
            ResultSet resultSet = insert.getGeneratedKeys();
            if (resultSet.next()) {
                manufacturer.setId(resultSet.getObject(1, Long.class));
            }
            insert.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not insert " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        try (Connection connection = ConnectionController.getConnection()) {
            String query = "SELECT * FROM manufacturers WHERE id = ? AND deleted = FALSE";
            PreparedStatement getById = connection.prepareStatement(query);
            getById.setLong(1, id);
            ResultSet resultSet = getById.executeQuery();
            while (resultSet.next()) {
                manufacturer = createObject(resultSet);
            }
            getById.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get " + manufacturer, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> allManufacturers = new ArrayList<>();
        try (Connection connection = ConnectionController.getConnection()) {
            String query = "SELECT * FROM manufacturers WHERE deleted = FALSE";
            PreparedStatement getAll = connection.prepareStatement(query);
            ResultSet resultSet = getAll.executeQuery();
            while (resultSet.next()) {
                allManufacturers.add(createObject(resultSet));
            }
            getAll.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all manufacturers", e);
        }
        return allManufacturers;
    }

    private Manufacturer createObject(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("id", Long.class);
        String name = resultSet.getObject("name", String.class);
        String country = resultSet.getObject("country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        try (Connection connection = ConnectionController.getConnection()) {
            String query = "UPDATE manufacturers "
                    + "SET name = ?, country = ? "
                    + "WHERE id = ? AND deleted = FALSE";
            PreparedStatement update = connection.prepareStatement(query);
            update.setString(1, manufacturer.getName());
            update.setString(2, manufacturer.getCountry());
            update.setLong(3, manufacturer.getId());
            update.executeUpdate();
            update.close();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update " + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        try (Connection connection = ConnectionController.getConnection()) {
            String query = "UPDATE manufacturers SET deleted = ? WHERE id = ?";
            PreparedStatement markDeleted = connection.prepareStatement(query);
            markDeleted.setBoolean(1, true);
            markDeleted.setLong(2, id);
            int deletedRows = markDeleted.executeUpdate();
            markDeleted.close();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete manufacturer" + id, e);
        }
    }
}
