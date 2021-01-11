package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import manufacturing.dao.DriverDao;
import manufacturing.db.Storage;
import manufacturing.model.Driver;

public class DriverDaoImpl implements DriverDao {

    @Override
    public Driver create(Driver driver) {
        Long id = Storage.driverId++;
        driver.setId(id);
        Storage.getDrivers().put(id, driver);
        return driver;
    }

    @Override
    public Optional<Driver> get(Long id) {
        return Optional.ofNullable(Storage.getDrivers().get(id));
    }

    @Override
    public List<Driver> getAll() {
        return new ArrayList<>(Storage.getDrivers().values());
    }

    @Override
    public Driver update(Driver newDriver) {
        Driver driverToChange =
                Storage.getDrivers().get(newDriver.getId());
        if (driverToChange == null) {
            throw new RuntimeException("Driver " + newDriver.getId() + " not found");
        }
        driverToChange.setLicenseNumber(newDriver.getLicenseNumber());
        driverToChange.setName(newDriver.getName());
        return driverToChange;
    }

    @Override
    public boolean delete(Long id) {
        Map<Long, Driver> drivers = Storage.getDrivers();
        Driver driver = drivers.get(id);
        if (driver == null) {
            throw new RuntimeException("Driver " + id + " not found");
        }
        drivers.remove(id);
        return true;
    }
}
