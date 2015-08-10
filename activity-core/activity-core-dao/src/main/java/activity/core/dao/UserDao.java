package activity.core.dao;

import java.util.*;

import activity.core.model.Users;

interface UserDao{
    public List<Users> getUsers();
    public Users getUser(int id);
    public String addUser(Users user);
    public String updateUser(Users user);
    public String deleteUser(int id);
    public Users findByUserName(String username);
}
