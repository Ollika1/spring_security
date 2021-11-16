package web.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import web.model.User;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private Session session;

        @Override
    public User getUserByName(String name) throws UsernameNotFoundException{
        String s = "SELECT u FROM User u WHERE u.name = :username";
        Query query =  session.createQuery(s);
        query.setParameter("username", name);
        User user = (User) query.getSingleResult();
        if (user == null){
            throw new UsernameNotFoundException("User не найден");
        }
        return user;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> getAllUsers() {
        TypedQuery<User> query= session.createQuery("select a from User a");
        return query.getResultList();
    }
    @Override
    public void save(User user) {
        session.save(user);
    }

    @Override
    public void delete(Long id) {
        User user= session.get(User.class, id);
        session.remove(user);
    }

    @Override
    public void edit(Long id,User user) {
        User userEdited = session.get(User.class, id);
        userEdited.setName(user.getName());
        userEdited.setLastName(user.getLastName());
        userEdited.setEmail(user.getEmail());
    }

    @Override
    public User getById(Long id) {
        return session.get(User.class, id);
    }

}

