package taxi.service.impl;

import java.util.List;
import taxi.dao.DriverDao;
import taxi.injections.Inject;
import taxi.injections.Service;
import taxi.model.Driver;
import taxi.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

    @Inject
    private DriverDao driverDao;

    @Override
    public Driver create(Driver driver) {
        return driverDao.create(driver);
    }

    @Override
    public Driver get(Long id) {
        return driverDao.get(id).orElseThrow(() ->
                new RuntimeException("Driver with id " + id + " not found"));
    }

    @Override
    public List<Driver> getAll() {
        return driverDao.getAll();
    }

    @Override
    public Driver update(Driver driver) {
        return driverDao.update(driver);
    }

    @Override
    public boolean delete(Long id) {
        return driverDao.delete(id);
    }
}
