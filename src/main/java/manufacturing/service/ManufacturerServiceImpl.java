package manufacturing.service;

import java.util.List;
import java.util.Optional;
import manufacturing.dao.ManufacturerDao;
import manufacturing.injections.Inject;
import manufacturing.injections.Service;
import manufacturing.model.Manufacturer;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Inject
    ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        Optional<Manufacturer> manufacturer = manufacturerDao.get(id);
        if (manufacturer.isEmpty()) {
            throw new RuntimeException("Manufacturer with id " + id + " not found");
        }
        return manufacturer.get();
    }

    @Override
    public List<Manufacturer> getAll() {
        return manufacturerDao.getAll();
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return manufacturerDao.update(manufacturer);
    }

    @Override
    public boolean delete(Long id) {
        return manufacturerDao.delete(id);
    }
}
