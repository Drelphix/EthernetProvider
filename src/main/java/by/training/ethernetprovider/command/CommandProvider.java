package by.training.ethernetprovider.command;

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
            commandType = CommandType.DEFAULT;
            LOGGER.error("Can't find commandName: {}", commandName);
        } else {
            commandType = CommandType.valueOf(commandName.toUpperCase());
        }
        return commandType.getCommand();
    }

    private static final class CommandProviderHolder {
        private static final CommandProvider commandProvider = new CommandProvider();
    }


}
