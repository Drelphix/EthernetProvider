package by.training.ethernetprovider.controller.command;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandProvider {
    private static final Logger LOGGER = LogManager.getLogger();

    public static CommandProvider getInstance() {
        return CommandProviderHolder.commandProvider;
    }

    public Command getCommand(String commandName) {
        CommandType commandType;
        if (commandName == null || commandName.isEmpty()) {
            LOGGER.error("Can't find commandName: {}", commandName);
            commandType = CommandType.DEFAULT;
        } else {
            try {
                commandType = CommandType.valueOf(commandName.toUpperCase());
            } catch (IllegalArgumentException e) {
                LOGGER.error("Can't find command: {}", commandName, e);
                commandType = CommandType.DEFAULT;
            }

        }
        return commandType.getCommand();
    }

    private static final class CommandProviderHolder {
        private static final CommandProvider commandProvider = new CommandProvider();
    }


}
