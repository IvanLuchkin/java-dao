package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import manufacturing.dao.DriverDao;
import manufacturing.db.Storage;
import manufacturing.injections.Dao;
import manufacturing.model.Driver;

@Dao
public class DriverDaoImpl implements DriverDao {

    @Override
    public Driver create(Driver driver) {
        return Storage.saveDriver(driver);
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
        Map<Long, Driver> drivers = Storage.getDrivers();
        Long id = newDriver.getId();
        if (!drivers.containsKey(id)) {
            throw new RuntimeException("Driver " + newDriver.getId() + " not found");
        }
        drivers.put(id, newDriver);
        return newDriver;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getDrivers().remove(id) != null;

    }
}
