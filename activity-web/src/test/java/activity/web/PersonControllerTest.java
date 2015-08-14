package activity.web;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import activity.core.model.Person;
import activity.core.model.Roles;
import activity.core.service.PersonServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import static org.mockito.Mockito.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-servlet.xml", "classpath:webapp.xml"})
public class PersonControllerTest {

    private MockMvc mockMvc;
    private Person p1;
    @Mock
    PersonServiceImpl personService;

    @Mock
    Person person;
    
    @InjectMocks
    private PersonController personController;

    private final String PERSON_JSON = "{\"test\" : \"test\"}";
    private final String LIST_JSON = "{{\"test\" : \"test\"}}";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(personController).build();
        p1 = new PersonBuilder()
                .id()
                .names()
                .address()
                .age()
                .bday()
                .grade()
                .gender()
                .currently_employed()
                .date_hired()
                .build();
    }

    @Test
    public void loginTest() throws Exception {
        System.out.println("Inside login test...");
        mockMvc.perform(get("/Login"))
                .andExpect(status().isOk())
                .andExpect(view().name("/tools/Login"))
                .andReturn();
    }

    @Test
    public void displayPersonsTest() throws Exception {
        System.out.println("Inside display person test...");

        when(personService.getPerson()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(get("/Persons"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/person"))
                .andExpect(model().attribute("person", hasSize(1)));
        verify(personService, times(1)).getPerson();
        verifyNoMoreInteractions(personService);
    }

    @Test
    public void addPersonFormTest() throws Exception {
        System.out.println("Inside display add person form test...");
        mockMvc.perform(get("/AddPerson"))
                .andExpect(status().isOk())
                .andExpect(view().name("persons/addform"));
    }

    @Test
    public void uploadInvalidFile() throws Exception {
        System.out.println("Invalid File Format");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "xml", "some xml".getBytes());

        mockMvc.perform(fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(status().is(200))
                .andExpect(model().attribute("message", "Invalid file format!"))
                .andExpect(view().name("persons/addform"));
    }

    @Test
    public void uploadValidFileIsEmpty() throws Exception {
        System.out.println("Valid File Format, Empty Content");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "".getBytes());

        mockMvc.perform(fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(model().attribute("message", "File Empty!"))
                .andExpect(status().is(200))
                .andExpect(view().name("persons/addform"));
    }

    @Test
    public void uploadValidFileIsNotEMpty() throws Exception {
        System.out.println("Valid File Format, With Content");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt", "text/plain", "first_name:TEST".getBytes());

        mockMvc.perform(fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(status().is(200))
                .andExpect(view().name("persons/addform"));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        System.out.println("Delete person test..");
        int i = 1;
        when(personService.deletePersons(i)).thenReturn("deleted");

        mockMvc.perform(delete("/remove/{id}", i)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void deleteEmployeeExceptionTest() {
        System.out.println("Delete person exception test..");
        int i = 1;
        when(personService.deletePersons(i)).thenThrow(Exception.class);
        try {
            mockMvc.perform(delete("/remove/{id}", i)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void addPersonTest() throws Exception {
        System.out.println("Add person test..");
        when(personService.addPersons(p1)).thenReturn("Added");
        mockMvc.perform(post("/AddPerson")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PERSON_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void updatePersonFormTest() throws Exception {
        System.out.println("Update person form test..");
        int i = 1;
        when(personService.getPersons(i)).thenReturn(p1);
        when(person.getRole()).thenReturn(p1.getRole());
        when(person.getContact()).thenReturn(p1.getContact());

        mockMvc.perform(get("/update/{id}", i))
                .andExpect(status().isOk())
                .andExpect(model().attribute("personForm", p1))
                .andExpect(model().attribute("roles", p1.getRole()))
                .andExpect(model().attribute("contact", p1.getContact()))
                .andExpect(view().name("persons/updateform"));
    }

    @Test
    public void updatePersonTest() throws Exception {
        System.out.println("Update person test..");
        when(personService.updatePersons(p1)).thenReturn("Updated");
        mockMvc.perform(put("/UpdatePerson")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PERSON_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void searchPersonTest() throws Exception {
        System.out.println("Search person test..");
        String list = "grade";
        String order = "asc";
        String search = "";
        when(personService.searchPerson(search, list, order)).thenReturn(Arrays.asList(p1));
        mockMvc.perform(post("/SearchPersons")
                .param("listBy", list)
                .param("order", order)
                .param("search", search)
                .content(LIST_JSON)
                .header("Accept", "application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void searchRoleTest() throws Exception {
        System.out.println("Search role test..");
        Roles role = mock(Roles.class);
        Set<Person> pr = new HashSet();
        pr.add(p1);
        String list = "1";
        int orders = Integer.parseInt(list);
        when(personService.getByRole(orders)).thenReturn(role);
        when(role.getPersonRole()).thenReturn(pr);
        mockMvc.perform(post("/SearchRole")
                .param("listBy", list)
                .content(LIST_JSON)
                .header("Accept", "application/json"))
                .andExpect(status().isOk());
    }
}
