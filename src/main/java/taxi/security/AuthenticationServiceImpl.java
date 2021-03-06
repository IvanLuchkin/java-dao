package taxi.security;

import java.util.Optional;
import taxi.exception.AuthenticationException;
import taxi.injections.Inject;
import taxi.injections.Service;
import taxi.model.Driver;
import taxi.service.DriverService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Inject
    private DriverService driverService;

    @Override
    public Driver login(String login, String password) throws AuthenticationException {
        Optional<Driver> driver = driverService.findByLogin(login);
        if (driver.isPresent() && driver.get().getPassword().equals(password)) {
            return driver.get();
        }
        throw new AuthenticationException("Wrong username or password");
    }
}
