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
        Long id = Storage.manufacturerId++;
        manufacturer.setId(id);
        Storage.getManufacturers().put(id, manufacturer);
        return manufacturer;
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
        Manufacturer manufacturerToChange =
                Storage.getManufacturers().get(newManufacturer.getId());
        if (manufacturerToChange == null) {
            throw new RuntimeException("Manufacturer " + newManufacturer.getId() + " not found");
        }
        manufacturerToChange.setCountry(newManufacturer.getCountry());
        manufacturerToChange.setName(newManufacturer.getName());
        return manufacturerToChange;
    }

    @Override
    public boolean delete(Long id) {
        Map<Long, Manufacturer> manufacturers = Storage.getManufacturers();
        Manufacturer manufacturer = manufacturers.get(id);
        if (manufacturer == null) {
            throw new RuntimeException("Manufacturer " + id + " not found");
        }
        manufacturers.remove(id);
        return true;
    }
}
