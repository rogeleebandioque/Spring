package hibernateactivity.core.model;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class Users {
    
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
    @SequenceGenerator(name="user_generator", sequenceName="user_generator", allocationSize=1)
    @Column(name="id")
    private Integer id;

    @Id
    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;

    @Column(name="role_name")
    private String role;

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

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

}
