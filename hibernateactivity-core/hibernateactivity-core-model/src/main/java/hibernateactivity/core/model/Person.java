package hibernateactivity.core.model;

import java.util.*;
import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name="Person")
public class Person {//implements Comparable<Person> {
    @Id 
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="personid_generator")
    @SequenceGenerator(name="personid_generator", sequenceName="personid_generator", allocationSize=1)
    @Column(name="id")
    private int id;

    @Embedded    
    private Name names = new Name();

    @Column(name="address")
    private String address;

    @OneToMany (cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="person_id")
    private Set<Contacts> contact = new HashSet<Contacts>();

    @Column(name="age")
    private Integer age;

    @Column(name="gender")    
    private String gender;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="bday")
    private Date bday = new Date();

    @Column(name="grade")
    private Integer grade;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name="date_hired")    
    private Date date_hired = new Date();

    private String currently_employed;
    @Column(name="currenty_employed")

    @ManyToMany(cascade=CascadeType.PERSIST, fetch=FetchType.EAGER)
    @JoinTable(name="PER_ROLE", joinColumns={@JoinColumn(name="person_id")},inverseJoinColumns={@JoinColumn(name="role_id")})   
    private Set<Roles> role = new HashSet<Roles>();

    public Person() {}
    public Person(Name names, String address, int age, String gender, 
                  Date bday, int grade, Date date_hired, String currently_employed) {
        this.names = names;
        this.address = address;
        this.age = age;
        this.gender = gender;
        this.bday = bday;
        this.grade = grade;
        this.date_hired = date_hired;
        this.currently_employed = currently_employed;
    }
    public boolean isNew() {
		return (this.id == 0);
	}

    public void setId(int id){
        this.id = id;
    }

    public int getId(){
        return id;
    }    

    public void setNames(Name names){
        this.names = names;
    }

    public Name getNames(){
        return names;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getAddress(){
        return address;
    }

    public void setContact(Set<Contacts> contact){
        this.contact = contact;
    }

    public Set<Contacts> getContact(){
        return contact;
    }

    public void setAge(Integer age){
        this.age = age;
    }

    public Integer getAge(){
        return age;
    }

    public void setGender(String gender){
       this.gender = gender;
    }

    public String getGender(){
        return gender;
    }

    public void setBday(Date bday){
        this.bday = bday;
    }
    
    public Date getBday(){
        return bday;
    }

    public void setGrade(Integer grade){
        this.grade = grade;
    }

    public Integer getGrade(){
        return grade;
    }

    public void setDate_hired(Date date_hired){
        this.date_hired = date_hired;
    }

    public Date getDate_hired(){
        return date_hired;
    }

    public void setCurrently_employed(String currently_employed){
        this.currently_employed = currently_employed;
    }

    public String getCurrently_employed(){
        return currently_employed;
    }
    
    public void setRole(Set<Roles> role){
        this.role = role;
    }

    public Set<Roles> getRole(){
        return role;
    }

    public int getComparison(){
        return grade;
    }

/*    public int compareTo(Person person){
        //return (this.getComparison().compareTo(person.getComparison());
  	    return (this.grade < person.grade ) ? -1: (this.grade > person.grade ) ? 1:0 ;        
    }*/
}

