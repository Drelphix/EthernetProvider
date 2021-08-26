package by.training.ethernetprovider.command;

public final class UrlRedirect {
    public static final String MAIN = "/controller?command=";
    public static final String TO_TARIFF_LIST = MAIN + CommandType.TARIFF_LIST.name();

    private UrlRedirect() {
    }
}
