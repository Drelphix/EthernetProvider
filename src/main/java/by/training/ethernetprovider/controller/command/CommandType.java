package by.training.ethernetprovider.controller.command;

import by.training.ethernetprovider.controller.command.impl.process.*;
import by.training.ethernetprovider.controller.command.impl.redirect.*;

public enum CommandType {
    SIGN_UP(new SignUpCommand()),
    SIGN_IN(new SignInCommand()),
    LOGOUT(new LogOutCommand()),
    TO_TARIFF(new ToTariffCommand()),
    TO_TARIFFS(new ToTariffsCommand()),
    TO_SIGN_IN(new ToSignInCommand()),
    TO_SIGN_UP(new ToSignUpCommand()),
    DEFAULT(new NotFoundCommand()),
    WELCOME(new WelcomeCommand()),
    TO_FORGOT_PASS(new ToForgotPass()),
    TO_PROFILE(new ToProfileCommand()),
    SWITCH_LANGUAGE(new SwitchLanguageCommand()),
    TO_PREVIOUS(new ToPreviousPage());

    private final Command command;

    CommandType(Command command) {
        this.command = command;
    }

    Command getCommand() {
        return command;
    }
}
