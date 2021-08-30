package by.training.ethernetprovider.controller.command;

import by.training.ethernetprovider.controller.command.impl.UserInformation;
import by.training.ethernetprovider.controller.command.impl.process.SignInCommand;
import by.training.ethernetprovider.controller.command.impl.process.SignUpCommand;
import by.training.ethernetprovider.controller.command.impl.process.TariffListCommand;
import by.training.ethernetprovider.controller.command.impl.process.WelcomeCommand;
import by.training.ethernetprovider.controller.command.impl.redirect.*;

public enum CommandType {
    SIGN_UP(new SignUpCommand()),
    SIGN_IN(new SignInCommand()),
    LOGOUT(new LogOutCommand()),
    CONNECT_TARIFF(new ToTariffConnectCommand()),
    TARIFF_LIST(new TariffListCommand()),
    USER_INFO(new UserInformation()),
    TO_SIGN_IN(new ToSignInCommand()),
    TO_SIGN_UP(new ToSignUpCommand()),
    DEFAULT(new NotFoundCommand()),
    WELCOME(new WelcomeCommand());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
