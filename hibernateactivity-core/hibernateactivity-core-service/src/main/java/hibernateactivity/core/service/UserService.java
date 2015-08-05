package hibernateactivity.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import hibernateactivity.core.dao.UserDaoImpl;
import hibernateactivity.core.model.Users;

public class UserService {

    UserDaoImpl userDaoImpl = new UserDaoImpl();
    @Autowired
    public void setUserDaoImpl(UserDaoImpl userDaoImpl){
        this.userDaoImpl = userDaoImpl;
    }

    public List<Users> getUsers() {
        return userDaoImpl.getUsers();
    }

    public String deleteUser(int id) {
        return userDaoImpl.deleteUser(id);
    }

    public String addUser(Users user) {
        return userDaoImpl.addUser(user);
    }

    public String updateUser(Users user) {
        return userDaoImpl.updateUser(user);
    }

}
