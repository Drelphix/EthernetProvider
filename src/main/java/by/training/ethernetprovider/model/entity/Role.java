package by.training.ethernetprovider.model.entity;

public enum Role {
    ADMIN ("Administrator"),
    SUPPORT ("Technical support"),
    MANAGER ("Content manager"),
    USER ("User");

    private String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }
}
