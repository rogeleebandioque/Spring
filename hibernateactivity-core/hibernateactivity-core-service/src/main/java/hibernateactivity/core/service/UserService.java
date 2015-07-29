package hibernateactivity.core.service;

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

import hibernateactivity.core.dao.UserDaoImpl;
import hibernateactivity.core.model.Users;

public class UserService implements UserDetailsService {

    UserDaoImpl userDaoImpl = new UserDaoImpl();

    public void setUserDaoImpl(UserDaoImpl userDaoImpl){
        this.userDaoImpl = userDaoImpl;
    }

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {

        hibernateactivity.core.model.Users user = userDaoImpl.getUserByUsername(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getRole().getRoleName());
		return buildUserForAuthentication(user, authorities);
	}

	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(hibernateactivity.core.model.Users user, List<GrantedAuthority> authorities) {
        for(GrantedAuthority a:authorities){
            System.out.println(a);
        }
		return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(String userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();
		setAuths.add(new SimpleGrantedAuthority(userRoles));

		List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

		return Result;
	}

}
