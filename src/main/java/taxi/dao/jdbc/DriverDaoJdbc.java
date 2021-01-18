package taxi.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import taxi.dao.DriverDao;
import taxi.exception.DataProcessingException;
import taxi.injections.Dao;
import taxi.model.Driver;
import taxi.util.ConnectionUtil;

@Dao
public class DriverDaoJdbc implements DriverDao {

    @Override
    public Driver create(Driver driver) {
        String query = "INSERT INTO drivers (driver_name, license_number) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, driver.getName());
            insertStatement.setString(2, driver.getLicenseNumber());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                driver.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not insert " + driver, e);
        }
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        String query = "SELECT * FROM drivers WHERE driver_id = ? AND deleted = FALSE";
        Driver driver = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(query)) {

            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.next()) {
                driver = createDriverInstance(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get driver by id " + id, e);
        }
        return Optional.ofNullable(driver);
    }

    @Override
    public List<Driver> getAll() {
        String query = "SELECT * FROM drivers WHERE deleted = FALSE";
        List<Driver> allDrivers = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query)) {

            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                allDrivers.add(createDriverInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all drivers", e);
        }
        return allDrivers;
    }

    private Driver createDriverInstance(ResultSet resultSet) throws SQLException {
        Long driverId = resultSet.getObject("driver_id", Long.class);
        String name = resultSet.getObject("driver_name", String.class);
        String licenseNumber = resultSet.getObject("license_number", String.class);
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(driverId);
        return driver;
    }

    @Override
    public Driver update(Driver driver) {
        String query = "UPDATE drivers "
                + "SET driver_name = ?, license_number = ? "
                + "WHERE driver_id = ? AND deleted = FALSE";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateStatement = connection.prepareStatement(query)) {

            updateStatement.setString(1, driver.getName());
            updateStatement.setString(2, driver.getLicenseNumber());
            updateStatement.setLong(3, driver.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update " + driver, e);
        }
        return driver;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE drivers SET deleted = true WHERE driver_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement markDeletedStatement = connection.prepareStatement(query)) {

            markDeletedStatement.setLong(1, id);
            int deletedRows = markDeletedStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete driver" + id, e);
        }
    }
}
