package hibernateactivity.core.dao;

import java.util.*;

import hibernateactivity.core.dao.actions.GetUserByUsername;
import hibernateactivity.core.dao.actions.ListUser;
import hibernateactivity.core.model.Users;

public class UserDaoImpl implements UserDao {

    public List<Users> getUsers() {
        return HibernateUtil.perform(new ListUser(), List.class);         
    }
    public Users getUserByUsername(String username) {
        return HibernateUtil.perform(new GetUserByUsername(username), Users.class);
    }
}
