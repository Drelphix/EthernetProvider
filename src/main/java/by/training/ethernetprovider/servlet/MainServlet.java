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
import org.apache.logging.log4j.EventLogger;


import java.io.IOException;
import java.util.List;

@WebServlet(name = "mainServlet", urlPatterns = "")
public class MainServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(MainServlet.class);

    @Override
    public void init() throws ServletException {
        super.init();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        TariffDaoImpl tariffDao = TariffDaoImpl.getInstance();
        List<Tariff> tariffs = null;
        try {
            tariffs = tariffDao.findNotArchiveTariffs();
        } catch (DaoException e) {
            logger.error("Can't get tariffs.",e);
            log("Can't get tariffs.",e);
        }
        request.setAttribute("tariffs", tariffs);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }


    @Override
    public void destroy() {
        super.destroy();
    }
}
