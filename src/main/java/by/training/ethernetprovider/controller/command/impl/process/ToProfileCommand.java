package by.training.ethernetprovider.controller.command.impl.process;

import by.training.ethernetprovider.controller.command.Command;
import by.training.ethernetprovider.controller.command.PagePath;
import by.training.ethernetprovider.controller.command.Router;
import jakarta.servlet.http.HttpServletRequest;

public class ToProfileCommand implements Command {
    @Override
    public Router execute(HttpServletRequest request) { //TODO 26.08.2021 13:21 :
        return new Router(PagePath.PROFILE, Router.RouterType.REDIRECT);
    }
}
