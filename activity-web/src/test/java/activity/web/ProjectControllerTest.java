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
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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
        Mockito.when(projectService.getProjects()).thenReturn(Arrays.asList(project));

        mockMvc.perform(MockMvcRequestBuilders.get("/projects"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/projects/projects"))
                .andExpect(MockMvcResultMatchers.model().attribute("projects", hasSize(1)));
    }

    @Test
    public void addProjectFormTest() throws Exception {
        System.out.println("Add Project Form Test");

        Mockito.when(personService.getPerson()).thenReturn(Arrays.asList(person));
        mockMvc.perform(MockMvcRequestBuilders.get("/AddProject"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/projects/projectForm"));
    }

    @Test
    public void updateProjectFormTest() throws Exception {
        System.out.println("Update Project Form Test");
        Set<Person> p = new HashSet<>();
        Mockito.when(projectService.getProject(1)).thenReturn(project);
        Mockito.when(projects.getPer_proj()).thenReturn(p);
        Mockito.when(projectService.updateProject(project)).thenReturn("updated");

        mockMvc.perform(MockMvcRequestBuilders.get("/upproject{id}", 1))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("projectForm", project))
                .andExpect(MockMvcResultMatchers.model().attribute("team", p))
                .andExpect(MockMvcResultMatchers.view().name("/projects/projectForm"));
    }

    @Test
    public void deleteEmployeeExceptionTest() {
        System.out.println("Delete project exception Test");
        int i = 1;
        Mockito.when(projectService.deleteProject(i)).thenThrow(Exception.class);
        try {
            mockMvc.perform(MockMvcRequestBuilders.delete("/removeproject/{id}", i)
                    .contentType(MediaType.APPLICATION_JSON_VALUE)
                    .accept(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string("false"));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        System.out.println("Delete  test..");
        int i = 1;
        Mockito.when(projectService.deleteProject(i)).thenReturn("deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/removeproject/{id}", i)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("true"));
    }

}
