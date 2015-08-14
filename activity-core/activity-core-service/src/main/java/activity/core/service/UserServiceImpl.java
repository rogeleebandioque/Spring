package activity.core.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import activity.core.dao.UserDaoImpl;
import activity.core.model.Users;

@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserDetailsService, UserServiceInterface {

    private UserDaoImpl userDaoImpl;

    @Autowired
    public void setUserDaoImpl(UserDaoImpl userDaoImpl) {
        this.userDaoImpl = userDaoImpl;
    }

    @Override
    public UserDetails loadUserByUsername(final String username)
            throws UsernameNotFoundException {

        activity.core.model.Users user = userDaoImpl.findByUserName(username);
        List<GrantedAuthority> authorities = buildUserAuthority(user.getRole());
        System.out.println(user.getUsername());
        return buildUserForAuthentication(user, authorities);
    }

    private User buildUserForAuthentication(activity.core.model.Users user,
            List<GrantedAuthority> authorities) {
        return new User(user.getUsername(),
                user.getPassword(), user.getEnabled(),
                true, true, true, authorities);
    }

    private List<GrantedAuthority> buildUserAuthority(String role) {

        Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
        setAuths.add(new SimpleGrantedAuthority(role));

        List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);
        return Result;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Users> getUsers() {
        return userDaoImpl.getUsers();
    }

    @Transactional(readOnly = true)
    @Override
    public Users getUser(int id) {
        return userDaoImpl.getUser(id);
    }

    @Override
    public String deleteUser(int id) {
        return userDaoImpl.deleteUser(id);
    }

    @Override
    public String addUser(Users user) {
        return userDaoImpl.addUser(user);
    }

    @Override
    public String updateUser(Users user) {
        return userDaoImpl.updateUser(user);
    }

}
