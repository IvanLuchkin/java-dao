package manufacturing.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import manufacturing.dao.ManufacturerDao;
import manufacturing.db.Storage;
import manufacturing.injections.Dao;
import manufacturing.model.Manufacturer;

@Dao
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
        Storage.getManufacturers().put(id, newManufacturer);
        return newManufacturer;
    }

    @Override
    public boolean delete(Long id) {
        Map<Long, Manufacturer> manufacturers = Storage.getManufacturers();
        Manufacturer manufacturer = manufacturers.get(id);
        if (manufacturer == null) {
            return false;
        }
        manufacturers.remove(id);
        return true;
    }
}
