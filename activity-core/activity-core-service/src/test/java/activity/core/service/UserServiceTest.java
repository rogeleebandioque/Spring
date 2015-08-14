package activity.core.service;

import activity.core.dao.UserDaoImpl;
import activity.core.model.Users;
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
public class UserServiceTest {
    @Mock
    UserDaoImpl userDaoImpl;

    @Mock
    private final Users trialUsers = new Users();

    private final List<Users> listusers = new ArrayList<>();

    @InjectMocks
    UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getusersTest() {
        System.out.println("Get Users Test");
        Mockito.when(userDaoImpl.getUsers()).thenReturn(listusers);
        assertEquals(listusers, userService.getUsers());
    }

    @Test
    public void deleteUserTest() {
        System.out.println("Delete User Test");
        Mockito.when(userDaoImpl.deleteUser(1)).thenReturn("Deleted!");
        assertEquals("Deleted!", userService.deleteUser(1));
    }

    @Test
    public void addUserTest() {
        System.out.println("Add User Test");
        Mockito.when(userDaoImpl.addUser(trialUsers)).thenReturn("Added!");
        assertEquals("Added!", userService.addUser(trialUsers));
    }

    @Test
    public void updateUserTest() {
        System.out.println("Update User Test");
        Mockito.when(userDaoImpl.updateUser(trialUsers)).thenReturn("Updated!");
        assertEquals("Updated!", userService.updateUser(trialUsers));
    }
}
