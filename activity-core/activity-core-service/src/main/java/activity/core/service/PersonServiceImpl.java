package activity.core.service;

import activity.core.dao.PersonDaoImpl;
import activity.core.model.Person;
import activity.core.model.Roles;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class PersonServiceImpl implements PersonServiceInterface {

    private PersonDaoImpl personDaoImpl;

    @Autowired
    public void setPersonDaoImpl(PersonDaoImpl personDaoImpl) {
        this.personDaoImpl = personDaoImpl;
    }

    @Transactional(readOnly = true)
    @Override
    public List<Person> getPerson() {
        return personDaoImpl.getPeople();
    }

    @Override
    public String deletePersons(int idNum) {
        return personDaoImpl.deletePeople(idNum);
    }

    @Transactional(readOnly = true)
    @Override
    public boolean searchPersons(int idNum) {
        return personDaoImpl.inRecord(idNum);
    }

    @Override
    public String addPersons(Person person) {
        return personDaoImpl.addPeople(person);
    }

    @Override
    public String updatePersons(Person person) {
        return personDaoImpl.updatePeople(person);
    }

    @Transactional(readOnly = true)
    @Override
    public Person getPersons(int idNum) {
        return personDaoImpl.getPeople(idNum);
    }

    @Transactional(readOnly = true)
    @Override
    public Roles getByRole(Integer category) {
        return personDaoImpl.getRole(category);
    }

    @Transactional(readOnly = true)
    public List<Person> searchPerson(String searchQ, String listBy, String order) {
        return personDaoImpl.searchPeople(searchQ, listBy, order);
    }

}
