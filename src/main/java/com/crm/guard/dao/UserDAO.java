package com.crm.guard.dao;

import com.crm.guard.entity.User;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAO extends AbstractDao<User, String> {

    public void saveUser(User user) {
        ShaPasswordEncoder passwordEncoder = new ShaPasswordEncoder();
        String hashedPassword = passwordEncoder.encodePassword(user.getPassword(), user.getName());

        user.setPassword(hashedPassword);
        saveOrUpdate(user);
    }

    @Override
    public User get(String username) {

        return (User) createCriteria()
                .add(Restrictions.like("name", username))
                .uniqueResult();
    }

}
