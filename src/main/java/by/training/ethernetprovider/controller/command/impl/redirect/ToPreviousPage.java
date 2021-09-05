package by.training.ethernetprovider.controller.command.impl.redirect;

import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.Router;
import by.training.ethernetprovider.controller.command.impl.process.WelcomeCommand;
import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public class ToPreviousPage implements Command {

    @Override
    public Router execute(HttpServletRequest request) throws CommandException {
        String path = request.getHeader("Referer");
        if(path.isEmpty()){
            return new WelcomeCommand().execute(request);
        }
        return new Router(path, Router.RouterType.REDIRECT);
    }
}
