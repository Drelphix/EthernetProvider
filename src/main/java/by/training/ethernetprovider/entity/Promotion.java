package by.training.ethernetprovider.entity;

import java.time.LocalDate;

public class Promotion extends ProviderEntity{
    private String name;
    private String description;
    private byte discount;
    private LocalDate startDate;
    private LocalDate endDate;

    public Promotion(int id){
        super(id);
    }

    public Promotion(int id, String name, String description, byte discount, LocalDate startDate, LocalDate endDate) {
        super(id);
        this.name = name;
        this.description = description;
        this.discount = discount;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public byte getDiscount() {
        return discount;
    }

    public void setDiscount(byte discount) {
        this.discount = discount;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Promotion)) return false;
        if (!super.equals(o)) return false;

        Promotion promotion = (Promotion) o;

        if (getDiscount() != promotion.getDiscount()) return false;
        if (!getName().equals(promotion.getName())) return false;
        if (!getDescription().equals(promotion.getDescription())) return false;
        if (!getStartDate().equals(promotion.getStartDate())) return false;
        return getEndDate().equals(promotion.getEndDate());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + (int) getDiscount();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Promotion{ " + super.toString()+
                ", name = '" + name + '\'' +
                ", description = '" + description + '\'' +
                ", discount = " + discount +
                ", startDate = " + startDate +
                ", endDate = " + endDate +
                '}';
    }
}
