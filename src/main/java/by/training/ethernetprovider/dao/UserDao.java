package by.training.ethernetprovider.dao;

import by.training.ethernetprovider.entity.User;

public interface UserDao extends ProviderDao<User> { //TODO 15.08.2021 15:20 :

    public boolean registerUser(String username, String password, String email);

    public User findUserByName(String username);

}
