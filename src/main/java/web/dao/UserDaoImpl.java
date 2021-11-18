package web.dao;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import web.model.Role;
import web.model.User;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.*;


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
        User userNew = new User();
        userNew.setName(user.getName());
        userNew.setLastName(user.getLastName());
        userNew.setEmail(user.getEmail());
        Set<Role> roles= new HashSet<>();
        Optional<String> roleString = user.getSet().stream().findFirst();
        if ((roleString.get().contains("ADMIN"))) {
            roles.add(session.get(Role.class, 2L));
        } else {
            roles.add(session.get(Role.class, 1L));
        }
        userNew.setRoles(roles);
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        userNew.setPassword(pass);
        session.save(userNew);
    }

    @Override
    public void delete(Long id) {
        User user= session.get(User.class, id);
        session.remove(user);
    }

    @Override
    public void edit(Long id,User user) {
        User userEdited = session.get(User.class, id);
        Set<Role> roles = userEdited.getRoles();
        Optional<Role> roleAdmin = roles.stream().filter(s -> s.getRole().contains("ADMIN")).findAny();
        if (user.getSet().stream().findFirst().get().contains("USER")){
            if (roleAdmin.isPresent()){
                roles.clear();
                roles.add(session.get(Role.class, 1L));
            }
        } else {
            if(roleAdmin.isEmpty()){
                roles.add(session.get(Role.class, 2L));
            }
        }
        userEdited.setRoles(roles);
        userEdited.setName(user.getName());
        userEdited.setLastName(user.getLastName());
        userEdited.setEmail(user.getEmail());
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
        String pass = bCryptPasswordEncoder.encode(user.getPassword());
        userEdited.setPassword(pass);

    }

    @Override
    public User getById(Long id) {
        return session.get(User.class, id);
    }

}

