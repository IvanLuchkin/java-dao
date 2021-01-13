package manufacturing.service.impl;

import java.util.List;
import manufacturing.dao.ManufacturerDao;
import manufacturing.injections.Inject;
import manufacturing.injections.Service;
import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

@Service
public class ManufacturerServiceImpl implements ManufacturerService {

    @Inject
    private ManufacturerDao manufacturerDao;

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        return manufacturerDao.create(manufacturer);
    }

    @Override
    public Manufacturer get(Long id) {
        return manufacturerDao.get(id).orElseThrow(() ->
                new RuntimeException("Manufacturer with id " + id + " not found"));
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
