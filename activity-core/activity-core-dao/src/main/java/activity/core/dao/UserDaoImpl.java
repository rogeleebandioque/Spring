package activity.core.dao;

import java.util.*;

import activity.core.dao.actions.users.GetUser;
import activity.core.dao.actions.users.GetUsername;
import activity.core.dao.actions.users.ListUser;
import activity.core.dao.actions.users.AddUser;
import activity.core.dao.actions.users.UpdateUser;
import activity.core.dao.actions.users.DeleteUser;
import activity.core.model.Users;

public class UserDaoImpl implements UserDao {

    public List<Users> getUsers() {
        return HibernateUtil.perform(new ListUser(), List.class);         
    }

    public Users getUser(int id) {
        return HibernateUtil.perform(new GetUser(id), Users.class);
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

    public Users findByUserName(String username) {
        return HibernateUtil.perform(new GetUsername(username), Users.class);
    }
}
