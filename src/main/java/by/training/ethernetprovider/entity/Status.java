package by.training.ethernetprovider.entity;

public enum Status {
    INACTIVE ("Inactive"),
    NOT_CONFIRMED ("Not confirmed"),
    WAITING_CONFIRM ("Waiting for confirmation"),
    ACTIVE ("Active"),
    BLOCKED("Blocked");

    private String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue(){
        return value;
    }

}
