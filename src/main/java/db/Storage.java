package db;

import java.util.HashMap;
import java.util.Map;
import model.Manufacturer;

public class Storage {
    public static Long manufacturerId = 0L;
    private static final Map<Long, Manufacturer> manufacturers = new HashMap<>();

    public static Map<Long, Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public static Long getManufacturerId() {
        return manufacturerId;
    }
}
