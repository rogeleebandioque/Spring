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
import org.hamcrest.Matchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.mockito.Mockito;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        mockMvc.perform(MockMvcRequestBuilders.get("/Login"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/tools/Login"))
                .andReturn();
    }

    @Test
    public void displayPersonsTest() throws Exception {
        System.out.println("Inside display person test...");

        Mockito.when(personService.getPerson()).thenReturn(Arrays.asList(p1));

        mockMvc.perform(MockMvcRequestBuilders.get("/Persons"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addPersonFormTest() throws Exception {
        System.out.println("Inside display add person form test...");
        mockMvc.perform(MockMvcRequestBuilders.get("/AddPerson"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("persons/addform"));
    }

    @Test
    public void uploadInvalidFile() throws Exception {
        System.out.println("Invalid File Format");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt",
                "xml", "some xml".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "Invalid file format!"))
                .andExpect(MockMvcResultMatchers.view().name("persons/addform"));
    }

    @Test
    public void uploadValidFileIsEmpty() throws Exception {
        System.out.println("Valid File Format, Empty Content");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt",
                "text/plain", "".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(MockMvcResultMatchers.model().attribute("message", "File Empty!"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.view().name("persons/addform"));
    }

    @Test
    public void uploadValidFileIsNotEMpty() throws Exception {
        System.out.println("Valid File Format, With Content");
        MockMultipartFile firstFile = new MockMultipartFile("data", "filename.txt",
                "text/plain", "first_name:TEST".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/uploadForm")
                .file(firstFile)
                .param("name", "TEST"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.view().name("persons/addform"));
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        System.out.println("Delete person test..");
        int i = 1;
        Mockito.when(personService.deletePersons(i)).thenReturn("deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/remove/{id}", i)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

    @Test
    public void deleteEmployeeExceptionTest() {
        System.out.println("Delete person exception test..");
        int i = 1;
        Mockito.when(personService.deletePersons(i)).thenThrow(Exception.class);
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/remove/{id}", i)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("false"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void updatePersonFormTest() throws Exception {
        System.out.println("Update person form test..");
        int i = 1;
        Mockito.when(personService.getPersons(i)).thenReturn(p1);
        Mockito.when(person.getRole()).thenReturn(p1.getRole());
        Mockito.when(person.getContact()).thenReturn(p1.getContact());

        mockMvc.perform(MockMvcRequestBuilders.get("/update{id}", i))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("personForm", p1))
                .andExpect(MockMvcResultMatchers.model().attribute("roles", p1.getRole()))
                .andExpect(MockMvcResultMatchers.model().attribute("contact", p1.getContact()))
                .andExpect(MockMvcResultMatchers.view().name("persons/updateform"));
    }

    @Test
    public void searchRoleTest() throws Exception {
        System.out.println("Search role test..");
        Roles role = Mockito.mock(Roles.class);
        Set<Person> pr = new HashSet();
        pr.add(p1);
        String list = "1";
        int orders = Integer.parseInt(list);
        Mockito.when(personService.getByRole(orders)).thenReturn(role);
        Mockito.when(role.getPersonRole()).thenReturn(pr);
        mockMvc.perform(MockMvcRequestBuilders.post("/SearchRole")
                .param("listBy", list)
                .content(LIST_JSON)
                .header("Accept", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
