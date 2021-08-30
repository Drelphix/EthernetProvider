package by.training.ethernetprovider.controller;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.CommandProvider;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebServlet(name = "controller", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final CommandProvider COMMAND_PROVIDER = CommandProvider.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            String commandName = request.getParameter(AttributeAndParameter.COMMAND);
            var command = COMMAND_PROVIDER.getCommand(commandName);
            var router = command.execute(request);
            switch (router.routerType()) {
                case FORWARD -> {
                    RequestDispatcher dispatcher = request.getRequestDispatcher(router.path());
                    dispatcher.forward(request, response);
                }
                case REDIRECT -> {
                    response.sendRedirect(router.path());
                }
                default -> {
                    LOGGER.error("Unknown router type: {}", router);
                    response.sendRedirect(PagePath.ERROR_PAGE);
                }
            }
        } catch (CommandException e){
            LOGGER.error("Can't process controller", e);
            RequestDispatcher dispatcher = request.getRequestDispatcher(PagePath.ERROR_PAGE);
            request.setAttribute(AttributeAndParameter.ERROR, e);
            dispatcher.forward(request, response);
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
