package by.training.ethernetprovider.entity;

import java.time.LocalDate;

public class Contract extends ProviderEntity {
    private LocalDate startDate;
    private LocalDate endDate;
    private byte discount;
    private Tariff tariff;
    private boolean isActive;
    private User user;

    public Contract(int id){
        super(id);
    }

    public Contract(int id, LocalDate startDate, LocalDate endDate, byte discount, Tariff tariff, boolean isActive, User user) {
        super(id);
        this.startDate = startDate;
        this.endDate = endDate;
        this.discount = discount;
        this.tariff = tariff;
        this.isActive = isActive;
        this.user = user;
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

    public byte getDiscount() {
        return discount;
    }

    public void setDiscount(byte discount) {
        this.discount = discount;
    }

    public Tariff getTariff() {
        return tariff;
    }

    public void setTariff(Tariff tariff) {
        this.tariff = tariff;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contract)) return false;
        if (!super.equals(o)) return false;

        Contract contract = (Contract) o;

        if (getDiscount() != contract.getDiscount()) return false;
        if (isActive() != contract.isActive()) return false;
        if (!getStartDate().equals(contract.getStartDate())) return false;
        if (!getEndDate().equals(contract.getEndDate())) return false;
        if (!getTariff().equals(contract.getTariff())) return false;
        return getUser().equals(contract.getUser());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + getStartDate().hashCode();
        result = 31 * result + getEndDate().hashCode();
        result = 31 * result + (int) getDiscount();
        result = 31 * result + getTariff().hashCode();
        result = 31 * result + (isActive() ? 1 : 0);
        result = 31 * result + getUser().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Contract{ " + super.toString()+
                ", startDate = " + startDate +
                ", endDate = " + endDate +
                ", discount = " + discount +
                ", tariff = " + tariff +
                ", isActive = " + isActive +
                ", user = " + user +
                '}';
    }
}
