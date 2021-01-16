package manufacturing.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import manufacturing.dao.CarDao;
import manufacturing.exception.DataProcessingException;
import manufacturing.injections.Dao;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.model.Manufacturer;
import manufacturing.util.ConnectionUtil;

@Dao
public class CarDaoJdbc implements CarDao {

    @Override
    public Car create(Car car) {
        String query = "INSERT INTO cars (manufacturer_id, model) VALUES (?, ?)";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement insertStatement =
                        connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setLong(1, car.getManufacturer().getId());
            insertStatement.setString(2, car.getModel());
            insertStatement.executeUpdate();
            ResultSet resultSet = insertStatement.getGeneratedKeys();
            if (resultSet.next()) {
                car.setId(resultSet.getObject(1, Long.class));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not insert " + car, e);
        }
        return car;
    }

    @Override
    public Optional<Car> get(Long id) {
        String query = "SELECT cars.car_id AS car_id, cars.model, m.manufacturer_id AS m_id, "
                + "m.manufacturer_name AS m_name, m.manufacturer_country AS m_country, "
                + "d.driver_id AS d_id, d.driver_name AS d_name, d.license_number FROM cars "
                + "LEFT JOIN cars_drivers cd ON cars.car_id = cd.car_id "
                + "LEFT JOIN drivers d ON d.driver_id = cd.driver_id "
                + "LEFT JOIN manufacturers m ON cars.manufacturer_id = m.manufacturer_id "
                + "WHERE cars.car_id = ? AND cars.deleted = FALSE AND d.deleted = false";
        Car car = null;
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getByIdStatement = connection.prepareStatement(
                        query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {

            getByIdStatement.setLong(1, id);
            ResultSet resultSet = getByIdStatement.executeQuery();
            if (resultSet.first()) {
                car = createCarInstance(resultSet);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get car by id " + id, e);
        }
        return Optional.ofNullable(car);
    }

    @Override
    public List<Car> getAll() {
        String query = "SELECT cars.car_id AS car_id, cars.model, m.manufacturer_id AS m_id, "
                + "m.manufacturer_name AS m_name, m.manufacturer_country AS m_country, "
                + "d.driver_id AS d_id, d.driver_name AS d_name, d.license_number FROM cars "
                + "LEFT JOIN cars_drivers cd ON cars.car_id = cd.car_id "
                + "LEFT JOIN drivers d ON d.driver_id = cd.driver_id "
                + "LEFT JOIN manufacturers m ON cars.manufacturer_id = m.manufacturer_id "
                + "WHERE cars.deleted = false AND d.deleted = false";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getAllStatement = connection.prepareStatement(query,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {
            ResultSet resultSet = getAllStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(createCarInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get all cars", e);
        }
        return cars;
    }

    private Car createCarInstance(ResultSet resultSet) throws SQLException {
        Long carId = resultSet.getObject("car_id", Long.class);
        String model = resultSet.getObject("model", String.class);
        List<Driver> drivers = new ArrayList<>();
        Car car = new Car(model, createManufacturerInstance(resultSet));
        do {
            Driver driver = createDriverInstance(resultSet);
            if (driver.getId() != null) {
                drivers.add(createDriverInstance(resultSet));
            }
        } while (resultSet.next() && resultSet.getObject("car_id", Long.class).equals(carId));
        resultSet.relative(-1);
        car.setId(carId);
        car.setDrivers(drivers);
        return car;
    }

    private Manufacturer createManufacturerInstance(ResultSet resultSet) throws SQLException {
        Long manufacturerId = resultSet.getObject("m_id", Long.class);
        String name = resultSet.getObject("m_name", String.class);
        String country = resultSet.getObject("m_country", String.class);
        Manufacturer manufacturer = new Manufacturer(name, country);
        manufacturer.setId(manufacturerId);
        return manufacturer;
    }

    private Driver createDriverInstance(ResultSet resultSet) throws SQLException {
        Long driverId = resultSet.getObject("d_id", Long.class);
        String name = resultSet.getObject("d_name", String.class);
        String licenseNumber = resultSet.getObject("license_number", String.class);
        Driver driver = new Driver(name, licenseNumber);
        driver.setId(driverId);
        return driver;
    }

    @Override
    public Car update(Car car) {
        String updateCarQuery = "UPDATE cars SET manufacturer_id = ?, model = ? "
                + "WHERE car_id = ? AND deleted = false";
        String deleteDriversQuery = "DELETE FROM cars_drivers WHERE car_id = ?";
        String insertDriversQuery = "INSERT INTO cars_drivers(driver_id, car_id) VALUES (?, ?)";
        Long carId = car.getId();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement updateCarStmt = connection.prepareStatement(updateCarQuery);
                PreparedStatement deleteDriversStmt =
                        connection.prepareStatement(deleteDriversQuery);
                PreparedStatement insertDriversStmt =
                        connection.prepareStatement(insertDriversQuery)) {

            updateCarStmt.setLong(1, car.getManufacturer().getId());
            updateCarStmt.setString(2, car.getModel());
            updateCarStmt.setLong(3, carId);
            updateCarStmt.executeUpdate();

            deleteDriversStmt.setLong(1, carId);
            deleteDriversStmt.executeUpdate();

            for (Driver driver : car.getDrivers()) {
                insertDriversStmt.setLong(1, driver.getId());
                insertDriversStmt.setLong(2, carId);
                insertDriversStmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not update " + car, e);
        }
        return car;
    }

    @Override
    public boolean delete(Long id) {
        String query = "UPDATE cars SET deleted = true WHERE car_id = ?";
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement deleteStatement = connection.prepareStatement(query)) {

            deleteStatement.setLong(1, id);
            int deletedRows = deleteStatement.executeUpdate();
            return deletedRows != 0;
        } catch (SQLException e) {
            throw new DataProcessingException("Could not delete car by id " + id, e);
        }
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        String getFiltered = "SELECT cars.car_id AS car_id, cars.model, m.manufacturer_id AS m_id, "
                + "m.manufacturer_name AS m_name, m.manufacturer_country AS m_country, "
                + "d.driver_id AS d_id, d.driver_name AS d_name, d.license_number FROM cars "
                + "LEFT JOIN cars_drivers cd ON cars.car_id = cd.car_id "
                + "LEFT JOIN drivers d ON d.driver_id = cd.driver_id "
                + "LEFT JOIN manufacturers m ON cars.manufacturer_id = m.manufacturer_id "
                + "WHERE cars.deleted = FALSE AND d.deleted = false "
                + "AND cars.car_id IN (SELECT car_id FROM cars_drivers WHERE driver_id = ?)";
        List<Car> cars = new ArrayList<>();
        try (Connection connection = ConnectionUtil.getConnection();
                PreparedStatement getFilteredStmt = connection.prepareStatement(getFiltered,
                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY)) {

            getFilteredStmt.setLong(1, driverId);
            ResultSet resultSet = getFilteredStmt.executeQuery();
            while (resultSet.next()) {
                cars.add(createCarInstance(resultSet));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Could not get cars by driver " + driverId, e);
        }
        return cars;
    }
}
