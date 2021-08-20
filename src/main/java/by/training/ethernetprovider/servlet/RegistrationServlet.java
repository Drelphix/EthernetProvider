package by.training.ethernetprovider.servlet;

import by.training.ethernetprovider.dao.impl.UserDaoImpl;
import by.training.ethernetprovider.entity.User;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.util.PasswordEncryptor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "RegistrationServlet", urlPatterns = "/registration")
public class RegistrationServlet extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPost(req, resp);
    }


    private void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        UserDaoImpl userDao = UserDaoImpl.getInstance();
        String username = request.getParameter("username");
        CharSequence password = request.getParameter("password");
        String email = request.getParameter("email");
        Optional<User> user;
        try {
            if(username.isEmpty() || password.isEmpty() || email.isEmpty()){
                request.setAttribute("message", "Not all fields are filled");
                request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
                return;
            }
            user = userDao.findUserByUsername(username);
            if(user.isPresent()){
                request.setAttribute("message", "Current username is taken");
                request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
                return;
            }
            user = userDao.findUserByEmail(email);
            if(user.isPresent()){
                request.setAttribute("message", "Current email is taken");
                request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
                return;
            }
            userDao.registerUser(username, PasswordEncryptor.encode(password), email);
            HttpSession httpSession = request.getSession(true);
            httpSession.setAttribute("username", username);
            response.sendRedirect("/");
        } catch (DaoException e) {
            LOGGER.error("Can't register user", e);
            request.setAttribute("message", "Can't register user, please try later");
            request.getRequestDispatcher("/pages/registration.jsp").forward(request, response);
        }

    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }


}
