package activity.core.service;

import activity.core.dao.ProjectDaoImpl;
import activity.core.model.Projects;
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
public class ProjectServiceTest {

    @Mock
    ProjectDaoImpl projectDaoImpl;

    @Mock
    private final Projects trialProject = new Projects();

    private final List<Projects> listproj = new ArrayList<>();

    @InjectMocks
    ProjectServiceImpl projectService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getProjectTest() {
        System.out.println("Get Project Test");
        Mockito.when(projectDaoImpl.getProjects()).thenReturn(listproj);
        assertEquals(listproj, projectService.getProjects());
    }

    @Test
    public void deleteProjectTest() {
        System.out.println("Delete Project Test");
        Mockito.when(projectDaoImpl.deleteProject(1)).thenReturn("Deleted!");
        assertEquals("Deleted!", projectService.deleteProject(1));
    }

    @Test
    public void searchProjectTest() {
        System.out.println("Search Project Test");
        Mockito.when(projectDaoImpl.getProject(1)).thenReturn(trialProject);
        assertEquals(trialProject, projectService.getProject(1));
    }

    @Test
    public void addProjectTest() {
        System.out.println("Add Project Test");
        Mockito.when(projectDaoImpl.addProject(trialProject)).thenReturn("Added!");
        assertEquals("Added!", projectService.addProject(trialProject));
    }

    @Test
    public void updateProjectTest() {
        System.out.println("Update Project Test");
        Mockito.when(projectDaoImpl.updateProject(trialProject)).thenReturn("Updated!");
        assertEquals("Updated!", projectService.updateProject(trialProject));
    }

}
