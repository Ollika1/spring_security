package web.dao;


import web.model.User;

import java.util.List;

public interface UserDao {
    User getUserByName(String name);

    @SuppressWarnings("unchecked")
    List<User> getAllUsers();

    void save(User user);

    void delete(Long id);

    void edit(Long id, User user);

    User getById(Long id);
}
