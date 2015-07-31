package hibernateactivity.core.dao;

import java.util.*;

import hibernateactivity.core.model.Users;

interface UserDao{
    public List<Users> getUsers();
    public Users getUserByUsername(String username);
    public String addUser(Users user);
    public String updateUser(Users user);
    public String deleteUser(int id);

}
