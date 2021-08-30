package by.training.ethernetprovider.controller.command;

import by.training.ethernetprovider.exception.CommandException;
import jakarta.servlet.http.HttpServletRequest;

public interface Command {

    Router execute(HttpServletRequest request) throws CommandException;
}
