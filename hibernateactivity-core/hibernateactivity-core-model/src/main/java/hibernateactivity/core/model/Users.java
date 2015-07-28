package hibernateactivity.core.model;

import javax.persistence.*;
import hibernateactivity.core.model.UserRoles;

@Entity
@Table(name="Users")
public class Users {
    
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="userid_generator")
    @SequenceGenerator(name="userid_generator", sequenceName="userid_generator", allocationSize=1)
    @Column(name="id")
    private Integer id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="role_id", referencedColumnName="roleId")    
    private UserRoles role = new UserRoles();

    public Users(){}
    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }


    public Integer getId(){
        return this.id;
    }
    
    public void setId(Integer id){
        this.id = id;
    }

    public String getUsername(){
        return this.username;
    }
    
    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return this.password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public UserRoles getRole(){
        return this.role;
    }

    public void setRole(UserRoles role){
        this.role = role;
    }

}
