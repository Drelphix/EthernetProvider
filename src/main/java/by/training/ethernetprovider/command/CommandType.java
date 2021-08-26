package by.training.ethernetprovider.command;

import by.training.ethernetprovider.command.impl.UserInformation;
import by.training.ethernetprovider.command.impl.process.FindTariff;
import by.training.ethernetprovider.command.impl.process.SignIn;
import by.training.ethernetprovider.command.impl.process.SignUp;
import by.training.ethernetprovider.command.impl.process.TariffList;
import by.training.ethernetprovider.command.impl.redirect.LogOut;
import by.training.ethernetprovider.command.impl.redirect.NotFound;
import by.training.ethernetprovider.command.impl.redirect.ToSignIn;
import by.training.ethernetprovider.command.impl.redirect.ToSignUp;

public enum CommandType {
    SIGN_UP(new SignUp()),
    SIGN_IN(new SignIn()),
    LOGOUT(new LogOut()),
    CONNECT_TARIFF(new FindTariff()),
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
