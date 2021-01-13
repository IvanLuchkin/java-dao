package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import manufacturing.dao.ManufacturerDao;
import manufacturing.db.Storage;
import manufacturing.model.Manufacturer;

public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return Storage.saveManufacturer(manufacturer);
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        return Optional.ofNullable(Storage.getManufacturers().get(id));
    }

    @Override
    public List<Manufacturer> getAll() {
        return new ArrayList<>(Storage.getManufacturers().values());
    }

    @Override
    public Manufacturer update(Manufacturer newManufacturer) {
        Map<Long, Manufacturer> manufacturers = Storage.getManufacturers();
        Long id = newManufacturer.getId();
        if (!manufacturers.containsKey(id)) {
            throw new RuntimeException("Manufacturer " + id + " not found");
        }
        manufacturers.put(id, newManufacturer);
        return newManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return Storage.getManufacturers().remove(id) != null;
    }
}
