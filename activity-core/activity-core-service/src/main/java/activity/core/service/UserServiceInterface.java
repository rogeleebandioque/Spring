package activity.core.service;

import activity.core.model.Users;
import java.util.List;

public interface UserServiceInterface {

    public List<Users> getUsers();

    public Users getUser(int id);

    public String deleteUser(int id);

    public String addUser(Users user);

    public String updateUser(Users user);
}
