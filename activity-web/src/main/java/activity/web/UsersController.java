package activity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import activity.core.service.UserServiceImpl;
import activity.core.model.Users;

@Controller
public class UsersController {

    private UserServiceImpl userService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public void setUserService(UserServiceImpl userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/userslist", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("Displaying Users...");
        List<Users> users = userService.getUsers();
        model.addAttribute("user", users);
        return "/users/user";
    }

    @RequestMapping(value = "updateuser/{id}",
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public Users updatePerson(@PathVariable int id) {
        logger.debug("Getting user details..");
        return userService.getUser(id);
    }

    @RequestMapping(value = "/removeuser/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean deleteUser(@PathVariable int id) {
        try {
            logger.debug("Deleting user..");
            String mes = userService.deleteUser(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "/AddUser",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public List<Users> addUser(@RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role) {
        logger.debug("Adding user..");

        Users user = new Users();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRole(role);
        
        userService.addUser(user);
        return userService.getUsers();
    }

    @RequestMapping(value = "/UpdateUser",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public List<Users> addUser(@RequestParam("id") String id,
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("role") String role) {
        logger.debug("Adding user..");
        
        Users user = new Users();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRole(role);
        
        userService.updateUser(user);
        return userService.getUsers();
    }
}
