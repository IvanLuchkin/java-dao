package manufacturing.dao;

import java.util.List;
import manufacturing.model.Car;

public interface CarDao extends AbstractDao<Car> {
    List<Car> getAllByDriver(Long driverId);
}
