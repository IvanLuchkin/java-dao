package taxi.web.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import taxi.injections.Injector;
import taxi.model.Driver;
import taxi.service.DriverService;

public class RegistrationController extends HttpServlet {
    private static final Injector injector = Injector.getInstance("taxi");
    private final DriverService driverService =
            (DriverService) injector.getInstance(DriverService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String name = req.getParameter("driver_name");
        String licenseNumber = req.getParameter("driver_license");
        String login = req.getParameter("driver_login");
        String password = req.getParameter("password");
        String pwdRepeat = req.getParameter("pwd-repeat");
        if (password.equals(pwdRepeat)) {
            Driver driver = new Driver(name, licenseNumber);
            driver.setLogin(login);
            driver.setPassword(password);
            driverService.create(driver);
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            req.setAttribute("message", "Passwords do not match");
            req.setAttribute("name", name);
            req.setAttribute("license", licenseNumber);
            req.setAttribute("login", login);
            req.getRequestDispatcher("/WEB-INF/views/registration.jsp").forward(req, resp);
        }
    }
}
