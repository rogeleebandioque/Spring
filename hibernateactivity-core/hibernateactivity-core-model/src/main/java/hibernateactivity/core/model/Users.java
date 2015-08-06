package hibernateactivity.core.model;

import javax.persistence.*;

@Entity
@Table(name="Users")
public class Users {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_generator")
    @SequenceGenerator(name="user_generator", sequenceName="user_generator", allocationSize=1)
    @Column(name="id")
    private int id;

    @Column(name="username")
    private String username;

    @Column(name="password")
    private String password;
    
    @Column(name="enabled")
    private Boolean enabled;

    @Column(name="role")
    private String role;

    public Users(){}
    public Users(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getId(){
        return this.id;
    }
    
    public void setId(int id){
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

    public Boolean getEnabled(){
        return this.enabled;
    }

    public void setEnabled(Boolean enabled){
        this.enabled = enabled;
    }

    public String getRole(){
        return this.role;
    }

    public void setRole(String role){
        this.role = role;
    }

}
