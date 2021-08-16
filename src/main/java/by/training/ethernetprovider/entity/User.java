package by.training.ethernetprovider.entity;

import java.math.BigDecimal;

public class User extends ProviderEntity{
   private String name;
   private String surname;
   private String city;
   private String address;
   private String login;
   private String email;
   private BigDecimal balance;
   private Role role;
   private Status status;

   public User(int id){
      super(id);
   }

   public User(int id, String name, String surname, String login, String email) {
      super(id);
      this.name = name;
      this.surname = surname;
      this.login = login;
      this.email = email;
   }

   public User(int id, String name, String surname, String city, String address, String login, String email, BigDecimal balance, Role role, Status status) {
      super(id);
      this.name = name;
      this.surname = surname;
      this.city = city;
      this.address = address;
      this.login = login;
      this.email = email;
      this.balance = balance;
      this.role = role;
      this.status = status;
   }


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getSurname() {
      return surname;
   }

   public void setSurname(String surname) {
      this.surname = surname;
   }

   public String getCity() {
      return city;
   }

   public void setCity(String city) {
      this.city = city;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public String getLogin() {
      return login;
   }

   public void setLogin(String login) {
      this.login = login;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public BigDecimal getBalance() {
      return balance;
   }

   public void setBalance(BigDecimal balance) {
      this.balance = balance;
   }

   public Role getRole() {
      return role;
   }

   public void setRole(Role role) {
      this.role = role;
   }

   public Status getStatus() {
      return status;
   }

   public void setStatus(Status status) {
      this.status = status;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (!(o instanceof User)) return false;
      if (!super.equals(o)) return false;

      User user = (User) o;

      if (!getName().equals(user.getName())) return false;
      if (!getSurname().equals(user.getSurname())) return false;
      if (!getCity().equals(user.getCity())) return false;
      if (!getAddress().equals(user.getAddress())) return false;
      if (!getLogin().equals(user.getLogin())) return false;
      if (!getEmail().equals(user.getEmail())) return false;
      if (!getBalance().equals(user.getBalance())) return false;
      if (getRole() != user.getRole()) return false;
      return getStatus() == user.getStatus();
   }

   @Override
   public int hashCode() {
      int result = super.hashCode();
      result = 31 * result + getName().hashCode();
      result = 31 * result + getSurname().hashCode();
      result = 31 * result + getCity().hashCode();
      result = 31 * result + getAddress().hashCode();
      result = 31 * result + getLogin().hashCode();
      result = 31 * result + getEmail().hashCode();
      result = 31 * result + getBalance().hashCode();
      result = 31 * result + getRole().hashCode();
      result = 31 * result + getStatus().hashCode();
      return result;
   }

   @Override
   public String toString() {
      return "User{ " + super.toString()+
              ", name= '" + name + '\'' +
              ", surname='" + surname + '\'' +
              ", city='" + city + '\'' +
              ", address='" + address + '\'' +
              ", login='" + login + '\'' +
              ", email='" + email + '\'' +
              ", balance=" + balance +
              ", role=" + role +
              ", status=" + status +
              '}';
   }
}
