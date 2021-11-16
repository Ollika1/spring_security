package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDaoImpl;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImp implements UserService{

    private UserDaoImpl userDaoImp;
    public UserServiceImp() {
    }

    @Autowired
    public void setUserDaoImp(UserDaoImpl userDaoImp) {
        this.userDaoImp = userDaoImp;
    }

    @Transactional
    @Override
    public List<User> getAllUsers() {
        return userDaoImp.getAllUsers();
    }
    @Transactional
    @Override
    public void save(User user) {
        userDaoImp.save(user);
    }
    @Transactional
    @Override
    public void delete(Long id) {
        userDaoImp.delete(id);
    }
    @Transactional
    @Override
    public void edit(Long id, User user) {
        userDaoImp.edit(id, user);
    }
    @Transactional
    @Override
    public User getById(Long id) {
        return userDaoImp.getById(id);
    }
}
