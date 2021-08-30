package by.training.ethernetprovider.controller.command;

public record Router(String path, RouterType routerType) {

    public enum RouterType {
        FORWARD, REDIRECT
    }
}
