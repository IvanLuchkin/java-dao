package dao;

import db.Storage;
import injections.Dao;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import model.Manufacturer;

@Dao
public class ManufacturerDao implements AbstractDao<Manufacturer> {

    public Manufacturer create(Manufacturer manufacturer) {
        Long id = Storage.manufacturerId++;
        manufacturer.setId(id);
        Storage.getManufacturers().put(id, manufacturer);
        return manufacturer;
    }

    public Optional<Manufacturer> get(Long id) {
        return Optional.ofNullable(Storage.getManufacturers().get(id));
    }

    public List<Manufacturer> getAll() {
        return new ArrayList<>(Storage.getManufacturers().values());
    }

    public Manufacturer update(Manufacturer newManufacturer) {
        Manufacturer manufacturerToChange = Storage.getManufacturers().get(newManufacturer.getId());
        if (manufacturerToChange == null) {
            throw new RuntimeException("Manufacturer " + newManufacturer.getId() + " not found");
        }
        manufacturerToChange.setCountry(newManufacturer.getCountry());
        manufacturerToChange.setName(newManufacturer.getName());
        return manufacturerToChange;
    }

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
