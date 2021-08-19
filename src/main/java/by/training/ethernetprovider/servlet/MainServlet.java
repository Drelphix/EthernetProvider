package by.training.ethernetprovider.servlet;

import by.training.ethernetprovider.dao.impl.TariffDaoImpl;

import by.training.ethernetprovider.entity.Tariff;

import by.training.ethernetprovider.exception.DaoException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "mainServlet")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        TariffDaoImpl tariffDao = TariffDaoImpl.getInstance();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffDao.getNotArchiveTariffs();
        } catch (DaoException e) {
            logger.error("Can't get tariffs.",e);
            throw new ServletException("Can't get tariffs", e);
        }
        request.setAttribute("tariffs", tariffs);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
