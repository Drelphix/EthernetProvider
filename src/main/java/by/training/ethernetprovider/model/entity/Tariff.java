package by.training.ethernetprovider.model.entity;

import java.math.BigDecimal;

public class Tariff extends ProviderEntity{
    private String name;
    private String description;
    private BigDecimal price;
    private boolean isArchive;
    private Promotion promotion;
    private BigDecimal discountPrice;

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

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tariff)) return false;
        if (!super.equals(o)) return false;

        Tariff tariff = (Tariff) o;

        if (isArchive() != tariff.isArchive()) return false;
        if (getName() != null ? !getName().equals(tariff.getName()) : tariff.getName() != null) return false;
        if (getDescription() != null ? !getDescription().equals(tariff.getDescription()) : tariff.getDescription() != null)
            return false;
        if (getPrice() != null ? !getPrice().equals(tariff.getPrice()) : tariff.getPrice() != null) return false;
        if (getPromotion() != null ? !getPromotion().equals(tariff.getPromotion()) : tariff.getPromotion() != null)
            return false;
        return getDiscountPrice() != null ? getDiscountPrice().equals(tariff.getDiscountPrice()) : tariff.getDiscountPrice() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        result = 31 * result + (getPrice() != null ? getPrice().hashCode() : 0);
        result = 31 * result + (isArchive() ? 1 : 0);
        result = 31 * result + (getPromotion() != null ? getPromotion().hashCode() : 0);
        result = 31 * result + (getDiscountPrice() != null ? getDiscountPrice().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Tariff{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", isArchive=" + isArchive +
                ", promotion=" + promotion +
                ", discountPrice=" + discountPrice +
                '}';
    }
}
