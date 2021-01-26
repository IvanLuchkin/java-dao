package taxi.dao;

import java.util.Optional;
import taxi.model.Driver;

public interface DriverDao extends AbstractDao<Driver> {
    Optional<Driver> findByLogin(String login);
}
