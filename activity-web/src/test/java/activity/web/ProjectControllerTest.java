package activity.web;

import activity.core.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import activity.core.model.Projects;
import activity.core.service.PersonServiceImpl;
import activity.core.service.ProjectServiceImpl;
import static org.hamcrest.Matchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-servlet.xml", "classpath:webapp.xml"})
public class ProjectControllerTest {

    private MockMvc mockMvc;

    @Mock
    ProjectServiceImpl projectService;

    @Mock
    PersonServiceImpl personService;

    @Mock
    Projects projects;

    Projects project;

    private final String PROJECT_JSON = "{\"test\" : \"test\"}";
    private final String LIST_JSON = "{{\"test\" : \"test\"}}";

    @Mock
    Person person;

    @InjectMocks
    ProjectsController projectController;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(projectController).build();
        project = new ProjectBuilder()
                .project_id()
                .project_name()
                .start_date()
                .end_date()
                .build();
        person = new PersonBuilder()
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
    public void displayProjectTest() throws Exception {
        System.out.println("Display Project Test");
        when(projectService.getProjects()).thenReturn(Arrays.asList(project));

        mockMvc.perform(get("/projects"))
                .andExpect(status().isOk())
                .andExpect(view().name("/projects/projects"))
                .andExpect(model().attribute("projects", hasSize(1)));
    }

    @Test
    public void addProjectFormTest() throws Exception {
        System.out.println("Add Project Form Test");

        when(personService.getPerson()).thenReturn(Arrays.asList(person));
        mockMvc.perform(get("/AddProject"))
                .andExpect(status().isOk())
                .andExpect(view().name("/projects/addprojectform"));
    }

    @Test
    public void updateProjectFormTest() throws Exception {
        System.out.println("Update Project Form Test");
        Set<Person> p = new HashSet<>();
        when(projectService.getProject(1)).thenReturn(project);
        when(projects.getPer_proj()).thenReturn(p);
        when(projectService.updateProject(project)).thenReturn("updated");

        mockMvc.perform(get("/upproject/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attribute("projectForm", project))
                .andExpect(model().attribute("team", p))
                .andExpect(view().name("/projects/updateprojectform"));
    }

    @Test
    public void deleteEmployeeExceptionTest() {
        System.out.println("Delete project exception Test");
        int i = 1;
        when(projectService.deleteProject(i)).thenThrow(Exception.class);
        try {
            mockMvc.perform(delete("/removeproject/{id}", i)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(content().string("false"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        System.out.println("Delete  test..");
        int i = 1;
        when(projectService.deleteProject(i)).thenReturn("deleted");

        mockMvc.perform(delete("/removeproject/{id}", i)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void addProjectTest() throws Exception {
        System.out.println("Add Project Test");
        Set<Person> id = new HashSet<>();
        Set<Person> members = new HashSet<>();
        when(projects.getPer_proj()).thenReturn(id);
        when(personService.getPersons(1)).thenReturn(person);
        when(projectService.addProject(project)).thenReturn("Added");
        mockMvc.perform(post("/AddProject")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PROJECT_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void updateProjectTest() throws Exception {
      System.out.println("Update Project Test");
        Set<Person> id = new HashSet<>();
        Set<Person> members = new HashSet<>();
        when(projects.getPer_proj()).thenReturn(id);
        when(personService.getPersons(1)).thenReturn(person);
        when(projectService.addProject(project)).thenReturn("Added");
        mockMvc.perform(put("/UpdateProject")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(PROJECT_JSON)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

}