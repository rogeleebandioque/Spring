package activity.core.model;

import java.util.*;
import com.fasterxml.jackson.annotation.*;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="Role")
@Cacheable
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="roles")
public class Roles {

    @Id 
    @Column(name="id")
    private int id;

    @Column(name="roleName")
    private String roleName;

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="PER_ROLE", joinColumns={@JoinColumn(name="role_id")},inverseJoinColumns={@JoinColumn(name="person_id")})      
    @JsonBackReference
    private Set<Person> personRole;
    
    public Roles() {}
    public Roles(int id, String roleName) {
        this.id = id;        
        this.roleName = roleName;
    }
    
    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    public String getRoleName() {
        return this.roleName;
    }
    
    public void setPersonRole(Set<Person> personRole) {
        this.personRole = personRole;
    }
    
    public Set<Person> getPersonRole() {
        return this.personRole;
    }  

    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        
        Roles obj2 = (Roles)obj;

        if((this.id == obj2.getId()) && (this.roleName.equals(obj2.getRoleName()))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int tmp = 0;
        tmp = ( id + roleName ).hashCode();
        return tmp;
    } 
}
