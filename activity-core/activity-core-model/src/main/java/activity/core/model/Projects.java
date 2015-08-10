package activity.core.model;

import java.util.*;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="Projects")
public class Projects{
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="project_generator")
    @SequenceGenerator(name="project_generator", sequenceName="project_generator", allocationSize=1)
    @Column(name="project_id")
    private int project_id;
    
    @Column(name="project_name")
    private String project_name;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="start_date")
    private Date start_date = new Date();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="end_date")
    private Date end_date = new Date();

    @ManyToMany(fetch=FetchType.EAGER)
    @JoinTable(name="PER_PRO", joinColumns={@JoinColumn(name="project_id")},inverseJoinColumns={@JoinColumn(name="person_id")})      
    @JsonBackReference 
    private Set<Person> per_proj = new HashSet<Person>();

    public Projects(){}
    public Projects(String project_name, Date start_date, Date end_date){
        this.project_name = project_name;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public int getProject_id() {
        return this.project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;    
    }

    public String getProject_name() {
        return this.project_name;
    }
    
    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Date getStart_date(){
        return this.start_date;
    }
    
    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date(){
        return this.end_date;
    }
    
    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public Set<Person> getPer_proj(){
        return this.per_proj;
    }
    
    public void setPer_proj(Set<Person> per_proj) {
        this.per_proj = per_proj;
    }
}
