package hibernateactivity.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import hibernateactivity.core.dao.UserDaoImpl;
import hibernateactivity.core.model.Users;

@Transactional(rollbackFor=Exception.class)
public class UserService {

    UserDaoImpl userDaoImpl;
    @Autowired
    public void setUserDaoImpl(UserDaoImpl userDaoImpl){
        this.userDaoImpl = userDaoImpl;
    }
    
    @Transactional(readOnly=true)
    public List<Users> getUsers() {
        return userDaoImpl.getUsers();
    }

    @Transactional(readOnly=true)
    public Users getUser(int id) {
        return userDaoImpl.getUser(id);
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
