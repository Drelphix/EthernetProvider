package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Locale;


public class WelcomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession();
        Locale locale = new Locale("ru","RU");
        session.setAttribute(AttributeAndParameter.LOCALE, locale);
        return new TariffListCommand().execute(request);
    }
}
