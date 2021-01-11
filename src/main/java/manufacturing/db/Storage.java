package manufacturing.db;

import java.util.HashMap;
import java.util.Map;
import manufacturing.model.Car;
import manufacturing.model.Driver;
import manufacturing.model.Manufacturer;

public class Storage {
    public static Long manufacturerId = 0L;
    public static Long carId = 0L;
    public static Long driverId = 0L;
    private static final Map<Long, Manufacturer> manufacturers = new HashMap<>();
    private static final Map<Long, Car> cars = new HashMap<>();
    private static final Map<Long, Driver> drivers = new HashMap<>();

    public static Map<Long, Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public static Map<Long, Car> getCars() {
        return cars;
    }

    public static Map<Long, Driver> getDrivers() {
        return drivers;
    }

    public static Manufacturer saveManufacturer(Manufacturer manufacturer) {
        Long id = Storage.manufacturerId++;
        manufacturer.setId(id);
        manufacturers.put(id, manufacturer);
        return manufacturer;
    }
}
