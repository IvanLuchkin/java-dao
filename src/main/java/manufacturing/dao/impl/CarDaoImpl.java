package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import manufacturing.dao.CarDao;
import manufacturing.db.Storage;
import manufacturing.model.Car;

public class CarDaoImpl implements CarDao {

    @Override
    public Car create(Car car) {
        Long id = Storage.carId++;
        car.setId(id);
        Storage.getCars().put(id, car);
        return car;
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
        Car carToChange =
                Storage.getCars().get(newCar.getId());
        if (carToChange == null) {
            throw new RuntimeException("Car " + newCar.getId() + " not found");
        }
        carToChange.setDrivers(newCar.getDrivers());
        carToChange.setModel(newCar.getModel());
        carToChange.setManufacturer(newCar.getManufacturer());
        return carToChange;
    }

    @Override
    public boolean delete(Long id) {
        Map<Long, Car> cars = Storage.getCars();
        Car car = cars.get(id);
        if (car == null) {
            throw new RuntimeException("Car " + id + " not found");
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
