package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.AttributeAndParameter;
import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.controller.command.impl.redirect.ToPreviousPage;
import by.training.ethernetprovider.exception.CommandException;
import by.training.ethernetprovider.util.LocaleSwitcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


import java.util.Locale;

public class SwitchLanguageCommand implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(true);
        Locale locale = (Locale) session.getAttribute(AttributeAndParameter.LOCALE);
        locale = LocaleSwitcher.change(locale);
        session.setAttribute(AttributeAndParameter.LOCALE, locale);
        return new ToPreviousPage().execute(request);
    }
}
