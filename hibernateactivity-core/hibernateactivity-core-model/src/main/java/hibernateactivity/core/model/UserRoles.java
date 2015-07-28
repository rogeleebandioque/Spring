package hibernateactivity.core.model;

import javax.persistence.*;
import java.util.*;
import hibernateactivity.core.model.Users;

@Entity
@Table(name="UserRole")
public class UserRoles{
    @Id
    @Column(name="roleId")
    private Integer roleId;

    @Column(name="roleName")
    private String roleName;

    @OneToMany (cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="role_id")
    private Set<Users> userRole = new HashSet<Users>();

    public UserRoles(){}

    public UserRoles(String roleName){
        this.roleName = roleName;
    }

    public Integer getRoleId(){
        return this.roleId;
    }
    
    public void setRoleId(Integer roleId){
        this.roleId = roleId;
    }

    public String getRoleName(){
        return this.roleName;
    }
    
    public void setRoleName(String roleName){
        this.roleName = roleName;
    }

    public Set<Users> getUserRole(){
        return this.userRole;
    }

    public void setUserRole(Set<Users> userRole){
        this.userRole = userRole;
    }
}
