package by.training.ethernetprovider.entity;

public abstract class ProviderEntity {
    private int id;

    public ProviderEntity() {}
    public ProviderEntity(int id) {
        this.id = id;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProviderEntity)) return false;

        ProviderEntity providerEntity = (ProviderEntity) o;

        return getId() == providerEntity.getId();
    }

    @Override
    public int hashCode() {
        return getId();
    }

    @Override
    public String toString() {
        return "id = " + id;
    }
}
