package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.controller.command.impl.redirect.ToTariffsCommand;
import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;



public class WelcomeCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        return new ToTariffsCommand().execute(request);
    }
}
