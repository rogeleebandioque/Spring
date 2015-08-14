package activity.core.service;

import activity.core.dao.PersonDaoImpl;
import activity.core.model.Person;
import activity.core.model.Roles;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(locations = {"classpath:services.xml"})
public class PersonServiceTest {

    @Mock
    private PersonDaoImpl personDaoImpl;

    @InjectMocks
    PersonServiceImpl personService;
    
    private final List<Person> trialData = new ArrayList<>();
    @Mock
    private final Person trialPerson = new Person();
    @Mock
    private final Roles trialRole = new Roles();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPersonTest() {
        System.out.println("Get Person Test");
        List<Person> p = new ArrayList<>();
        Mockito.when(personDaoImpl.getPeople()).thenReturn(p);
        assertEquals(p, personService.getPerson());
    }

    @Test
    public void deletePersonTest() {
        System.out.println("Delete Person Test");
        Mockito.when(personDaoImpl.deletePeople(1)).thenReturn("Deleted!");
        assertEquals("Deleted!", personService.deletePersons(1));
    }

    @Test
    public void searchPersonTest() {
        System.out.println("Search Person Test");
        Mockito.when(personDaoImpl.inRecord(1)).thenReturn(true);
        assertEquals(true, personService.searchPersons(1));
    }

    @Test
    public void addPersonTest() {
        System.out.println("Add Person Test");
        Mockito.when(personDaoImpl.addPeople(trialPerson)).thenReturn("Added!");
        assertEquals("Added!", personService.addPersons(trialPerson));
    }

    @Test
    public void updatePersonTest() {
        System.out.println("Update Person Test");
        Mockito.when(personDaoImpl.updatePeople(trialPerson)).thenReturn("Updated!");
        assertEquals("Updated!", personService.updatePersons(trialPerson));
    }

    @Test
    public void getPersonsTest() {
        System.out.println("Get Single Person Test");
        Mockito.when(personDaoImpl.getPeople(1)).thenReturn(trialPerson);
        assertEquals(trialPerson, personService.getPersons(1));
    }

    @Test
    public void getByRoleTest() {
        System.out.println("Get by Role Test");
        Mockito.when(personDaoImpl.getRole(1)).thenReturn(trialRole);
        assertEquals(trialRole , personService.getByRole(1));
    }

    @Test
    public void searchPerson() {
        System.out.println("Set Person Test");
        Mockito.when(personDaoImpl.searchPeople("", "", "")).thenReturn(trialData);
        assertEquals(trialData, personService.searchPerson("","",""));
    }

}
