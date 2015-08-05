package hibernateactivity.core.service;

import hibernateactivity.core.dao.PersonDaoImpl;
import hibernateactivity.core.dao.UserDaoImpl;
import hibernateactivity.core.model.Person;
import hibernateactivity.core.model.Contacts;
import hibernateactivity.core.model.Roles;
import hibernateactivity.core.model.Users;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor=Exception.class)
public class Service {

    private PersonDaoImpl personDaoImpl;

    @Autowired
    public void setPersonDaoImpl(PersonDaoImpl personDaoImpl){
        this.personDaoImpl = personDaoImpl;
    }

    @Transactional(readOnly=true)
    public List<Person> getPerson() {
        return personDaoImpl.getPeople();
    }

    public String deletePersons(int idNum) {
        return personDaoImpl.deletePeople(idNum);
    }

    @Transactional(readOnly=true)
    public boolean searchPersons(int idNum) {
        return personDaoImpl.inRecord(idNum);
    }

    public String addPersons(Person person) {
        return personDaoImpl.addPeople(person);
    }

    public String updatePersons(Person person) {
        return personDaoImpl.updatePeople(person);
    }

    @Transactional(readOnly=true)
    public Person getPersons(int idNum) {
        return personDaoImpl.getPeople(idNum);
    }

    @Transactional(readOnly=true)
    public Roles getByRole(Integer category) {
        return personDaoImpl.getRole(category);
    }

    @Transactional(readOnly=true)
    public List<Person> searchPerson(String searchQ, String listBy, String order) {
        return personDaoImpl.searchPeople(searchQ, listBy, order);
    }

}
