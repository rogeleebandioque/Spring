package hibernateactivity.core.dao;

import java.util.*;

import hibernateactivity.core.dao.actions.GetUserByUsername;
import hibernateactivity.core.dao.actions.ListUser;
import hibernateactivity.core.dao.actions.AddUser;
import hibernateactivity.core.dao.actions.UpdateUser;
import hibernateactivity.core.dao.actions.DeleteUser;
import hibernateactivity.core.model.Users;

public class UserDaoImpl implements UserDao {

    public List<Users> getUsers() {
        return HibernateUtil.perform(new ListUser(), List.class);         
    }

    public Users getUser(int id) {
        return HibernateUtil.perform(new GetUserByUsername(id), Users.class);
    }

    public String addUser(Users user) {
        return HibernateUtil.perform(new AddUser(user), Boolean.class) ? "Unable to Add User" : "User Added";    
    }

    public String updateUser(Users user) {
        return HibernateUtil.perform(new UpdateUser(user), Boolean.class) ? "Unable to Update User!" : "User Updated!";     
    }

    public String deleteUser(int id) {
        return HibernateUtil.perform(new DeleteUser(id), Boolean.class) ? "User Deleted!" : "Unable to Delete User!";
    }
}
