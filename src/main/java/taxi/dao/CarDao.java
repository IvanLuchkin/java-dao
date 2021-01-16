package taxi.dao;

import java.util.List;
import taxi.model.Car;

public interface CarDao extends AbstractDao<Car> {
    List<Car> getAllByDriver(Long driverId);
}
