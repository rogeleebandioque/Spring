package activity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import activity.core.service.UserService;
import activity.core.model.Users;

@Controller
public class UsersController{
    private UserService userService;

    private final Logger logger = LoggerFactory.getLogger(UsersController.class);

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }    

	@RequestMapping(value ="/users", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("Displaying Users...");
        model.addAttribute("user",userService.getUsers());
        return "users";
    }
    @RequestMapping(value = "updateuser/{id}", 
                    method = RequestMethod.GET,
                    headers="Accept=application/json")
    @ResponseBody
    public Users updatePerson(@PathVariable int id) {
        logger.debug("Getting user details..");
        return userService.getUser(id);
    }

    @RequestMapping(value="/removeuser/{id}",
                        method=RequestMethod.DELETE,
                        produces = MediaType.APPLICATION_JSON_VALUE,
                        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean deleteUser(@PathVariable int id) {
        try {
            logger.debug("deleting user..");
            String mes = userService.deleteUser(id);
            return true;
        } catch (Exception e) {
            return false;
       }
    } 
    
    @RequestMapping(value ="/AddUser", 
                    method = RequestMethod.POST,
                    headers="Accept=application/json")
    @ResponseBody
    public List<Users> addUser(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("role") String role
                            ) {
        logger.debug("Adding user.."); 
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRole(role);
        userService.addUser(user);
        return userService.getUsers();
    }

    @RequestMapping(value ="/UpdateUser", 
                    method = RequestMethod.POST,
                    headers="Accept=application/json")
    @ResponseBody
    public List<Users> addUser(@RequestParam("id") String id,
                            @RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("role") String role
                            ) {
        logger.debug("Adding user.."); 
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        Users user = new Users();
        user.setId(Integer.parseInt(id));
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setRole(role);
        userService.updateUser(user);
        return userService.getUsers();
    }
}
