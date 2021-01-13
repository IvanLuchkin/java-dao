package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import manufacturing.dao.CarDao;
import manufacturing.db.Storage;
import manufacturing.injections.Dao;
import manufacturing.model.Car;

@Dao
public class CarDaoImpl implements CarDao {

    @Override
    public Car create(Car car) {
        return Storage.saveCar(car);
    }

    @Override
    public Optional<Car> get(Long id) {
        return Optional.ofNullable(Storage.getCars().get(id));
    }

    @Override
    public List<Car> getAll() {
        return new ArrayList<>(Storage.getCars().values());
    }

    @Override
    public Car update(Car newCar) {
        Map<Long, Car> cars = Storage.getCars();
        Long id = newCar.getId();
        if (!cars.containsKey(id)) {
            throw new RuntimeException("Car " + newCar.getId() + " not found");
        }
        cars.put(id, newCar);
        return newCar;
    }

    @Override
    public boolean delete(Long id) {
        Map<Long, Car> cars = Storage.getCars();
        Car car = cars.get(id);
        if (car == null) {
            return false;
        }
        cars.remove(id);
        return true;
    }

    @Override
    public List<Car> getAllByDriver(Long driverId) {
        return Storage.getCars().values()
                .stream()
                .filter(car -> car.getDrivers()
                        .stream()
                        .anyMatch(driver -> driver.getId().equals(driverId)))
                .collect(Collectors.toList());
    }
}
