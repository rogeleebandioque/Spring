package hibernateactivity.core.dao;

import java.util.*;

import hibernateactivity.core.model.Users;

interface UserDao{
    public List<Users> getUsers();
    public Users getUserByUsername(String username);
}
