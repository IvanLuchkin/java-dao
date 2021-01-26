package taxi.service;

import java.util.Optional;
import taxi.model.Driver;

public interface DriverService extends AbstractService<Driver> {
    Optional<Driver> findByLogin(String login);
}
