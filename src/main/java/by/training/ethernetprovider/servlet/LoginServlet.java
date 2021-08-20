package by.training.ethernetprovider.servlet;

import by.training.ethernetprovider.dao.impl.UserDaoImpl;
import by.training.ethernetprovider.exception.DaoException;
import by.training.ethernetprovider.util.PasswordEncryptor;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processPost(req, resp);
    }

    private void processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
    }

    private void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        UserDaoImpl userDao = UserDaoImpl.getInstance();
        try {
            if (username.isEmpty() || password.isEmpty()) {
                request.setAttribute("message", "Not all fields are filled");
                request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
                return;
            }
            String encryptedPassword = userDao.findPasswordByUsernameOrEmail(username);
            if (!encryptedPassword.isEmpty()) {
                if (PasswordEncryptor.compare(encryptedPassword, password)) {
                    HttpSession httpSession = request.getSession(true);
                    httpSession.setAttribute("username", username);
                    response.sendRedirect("/");
                    return;
                }
            }
            request.setAttribute("message", "Incorrect credentials");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        } catch (DaoException e) {
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
