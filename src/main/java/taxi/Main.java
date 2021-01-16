package taxi;

import taxi.injections.Injector;
import taxi.model.Car;
import taxi.model.Driver;
import taxi.model.Manufacturer;
import taxi.service.CarService;
import taxi.service.DriverService;
import taxi.service.ManufacturerService;

public class Main {
    private static final Injector injector = Injector.getInstance(Main.class.getPackageName());

    public static void main(String[] args) {
        ManufacturerService manufacturerService =
                (ManufacturerService) injector.getInstance(ManufacturerService.class);
        Manufacturer manufacturer = new Manufacturer("Tesla", "USA");
        Manufacturer anotherManufacturer = new Manufacturer("Dodge", "USA");
        manufacturerService.create(anotherManufacturer);
        manufacturerService.create(manufacturer);

        CarService carService =
                (CarService) injector.getInstance(CarService.class);
        Car car = new Car("Model X", manufacturer);
        Car anotherCar = new Car("Viper SRT", anotherManufacturer);
        carService.create(car);
        carService.create(anotherCar);

        DriverService driverService =
                (DriverService) injector.getInstance(DriverService.class);
        Driver driver = new Driver("Bob", "1234");
        Driver anotherDriver = new Driver("Alice", "5678");
        driverService.create(driver);
        driverService.create(anotherDriver);

        System.out.println("Manufacturers -------------------------------");
        System.out.println(manufacturerService.get(manufacturer.getId()));
        manufacturer.setName("TeslaNew");
        manufacturerService.update(manufacturer);
        System.out.println(manufacturerService.get(manufacturer.getId()));
        System.out.println(manufacturerService.getAll());
        manufacturerService.delete(anotherManufacturer.getId());
        System.out.println(manufacturerService.getAll());
        System.out.println("---------------------------------------------");

        System.out.println("Cars ----------------------------------------");
        System.out.println(carService.get(car.getId()));
        carService.addDriverToCar(driver, car);
        System.out.println(carService.get(car.getId()));
        System.out.println(carService.getAll());
        carService.delete(anotherCar.getId());
        System.out.println(carService.getAll());
        System.out.println("---------------------------------------------");

        System.out.println("Driver --------------------------------------");
        System.out.println(driverService.get(driver.getId()));
        driver.setName("BobNew");
        driverService.update(driver);
        System.out.println(driverService.get(driver.getId()));
        System.out.println(driverService.getAll());
        driverService.delete(anotherDriver.getId());
        System.out.println(driverService.getAll());
        System.out.println("---------------------------------------------");
    }
}
