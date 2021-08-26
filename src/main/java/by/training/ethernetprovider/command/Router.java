package by.training.ethernetprovider.command;

public record Router(String path, RouterType routerType) {

    public enum RouterType {
        FORWARD, REDIRECT
    }
}
