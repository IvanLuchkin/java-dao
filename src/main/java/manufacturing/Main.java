package manufacturing;

import manufacturing.injections.Injector;
import manufacturing.model.Manufacturer;
import manufacturing.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);

        Manufacturer manufacturer = new Manufacturer("Tesla", "USA");
        Manufacturer anotherManufacturer = new Manufacturer("Dodge", "USA");

        manufacturerService.create(anotherManufacturer);
        manufacturerService.create(manufacturer);
        System.out.println(manufacturerService.get(manufacturer.getId()));
        manufacturer.setName("TeslaNew");
        manufacturerService.update(manufacturer);
        System.out.println(manufacturerService.get(manufacturer.getId()));
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(anotherManufacturer.getId());
        System.out.println(manufacturerService.getAll());
    }
}
