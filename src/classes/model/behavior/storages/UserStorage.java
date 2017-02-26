package classes.model.behavior.storages;

import classes.model.User;

import java.util.List;

public interface UserStorage {

    public User getUser(String login, String password);

    public void storeUser(User user);

    public User getUserById(int id);

    public User getUserByLogin(String login);

    public List<User> getAllUsers();

    public void deleteUser(int id);
}
