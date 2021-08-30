package by.training.ethernetprovider.controller.command;

import by.training.ethernetprovider.controller.command.impl.UserInformation;
import by.training.ethernetprovider.controller.command.impl.process.SignIn;
import by.training.ethernetprovider.controller.command.impl.process.SignUp;
import by.training.ethernetprovider.controller.command.impl.process.TariffList;
import by.training.ethernetprovider.controller.command.impl.redirect.*;

public enum CommandType {
    SIGN_UP(new SignUp()),
    SIGN_IN(new SignIn()),
    LOGOUT(new LogOut()),
    CONNECT_TARIFF(new ToTariffConnect()),
    TARIFF_LIST(new TariffList()),
    USER_INFO(new UserInformation()),
    TO_SIGN_IN(new ToSignIn()),
    TO_SIGN_UP(new ToSignUp()),
    DEFAULT(new NotFound());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
