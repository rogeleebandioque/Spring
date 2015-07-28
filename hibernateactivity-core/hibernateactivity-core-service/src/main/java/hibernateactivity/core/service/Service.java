package hibernateactivity.core.service;

import hibernateactivity.core.dao.PersonDaoImpl;
import hibernateactivity.core.dao.UserDaoImpl;
import hibernateactivity.core.model.Person;
import hibernateactivity.core.model.Contacts;
import hibernateactivity.core.model.Roles;
import hibernateactivity.core.model.Users;
import java.util.List;

public class Service {

    PersonDaoImpl personDaoImpl = new PersonDaoImpl();

    public void setPersonDaoImpl(PersonDaoImpl personDaoImpl){
        this.personDaoImpl = personDaoImpl;
    }

    UserDaoImpl userDaoImpl = new UserDaoImpl();

    public void setUserDaoImpl(UserDaoImpl userDaoImpl){
        this.userDaoImpl = userDaoImpl;
    }

    public List<Person> getPersons() {
        return personDaoImpl.getPeople();
    }

    public String deletePersons(int idNum) {
        return personDaoImpl.deletePeople(idNum);
    }

    public boolean searchPersons(int idNum) {
        return personDaoImpl.inRecord(idNum);
    }

    public String addPersons(Person person) {
        return personDaoImpl.addPeople(person);
    }

    public String updatePersons(Person person) {
        return personDaoImpl.updatePeople(person);
    }

    public Person getPersons(int idNum) {
        return personDaoImpl.getPeople(idNum);
    }

    public Roles getByRole(Integer category) {
        return personDaoImpl.getRole(category);
    }

    public List<Person> searchPerson(String searchQ, String listBy, String order) {
        return personDaoImpl.searchPeople(searchQ, listBy, order);
    }
    
    public List<Users> getUsers(){
        return userDaoImpl.getUsers();
    }

}
