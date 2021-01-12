package manufacturing.db;

import java.util.HashMap;
import java.util.Map;
import manufacturing.model.Manufacturer;

public class Storage {
    public static Long manufacturerId = 0L;
    private static final Map<Long, Manufacturer> manufacturers = new HashMap<>();

    public static Map<Long, Manufacturer> getManufacturers() {
        return manufacturers;
    }

    public static Manufacturer saveManufacturer(Manufacturer manufacturer) {
        Long id = Storage.manufacturerId++;
        manufacturer.setId(id);
        manufacturers.put(id, manufacturer);
        return manufacturer;
    }
}
