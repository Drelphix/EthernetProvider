package by.training.ethernetprovider.entity;

import java.math.BigDecimal;

public class Tariff extends ProviderEntity{
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isArchive;
    private Promotion promotion;

    public Tariff(int id){
        super(id);
    }
    public Tariff(int id, String name, String description, BigDecimal price, boolean isArchive, Promotion promotion) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.isArchive = isArchive;
        this.promotion = promotion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isArchive() {
        return isArchive;
    }

    public void setArchive(boolean archive) {
        isArchive = archive;
    }

    public Promotion getPromotion() {
        return promotion;
    }

    public void setPromotion(Promotion promotion) {
        this.promotion = promotion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;
        if (!super.equals(o)) return false;

        Tariff tariff = (Tariff) o;

        if (isArchive() != tariff.isArchive()) return false;
        if (!getName().equals(tariff.getName())) return false;
        if (!getDescription().equals(tariff.getDescription())) return false;
        if (!getPrice().equals(tariff.getPrice())) return false;
        return getPromotion().equals(tariff.getPromotion());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getPrice().hashCode();
        result = 31 * result + (isArchive() ? 1 : 0);
        result = 31 * result + getPromotion().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tariff{ " + super.toString()+
                ", name = '" + name + '\'' +
                ", description = '" + description + '\'' +
                ", price = " + price +
                ", isArchive = " + isArchive +
                ", promotion = " + promotion +
                '}';
    }
}
