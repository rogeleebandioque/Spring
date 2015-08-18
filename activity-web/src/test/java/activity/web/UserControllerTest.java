package activity.web;

import activity.core.model.Users;
import activity.core.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.*;
import org.hamcrest.Matchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:Spring-servlet.xml", "classpath:webapp.xml"})
public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    UserServiceImpl userService;

    @InjectMocks
    UsersController userController;

    @Mock
    Users user;

    Users mockUser;

    private final String USER_JSON = "{\"test\" : \"test\"}";

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockUser = new UserBuilder()
                .id()
                .username()
                .password()
                .enabled()
                .role()
                .build();
    }

    @Test
    public void displayUserTest() throws Exception {
        System.out.println("Display User Test");
        Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/userslist"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("/users/user"))
                .andExpect(MockMvcResultMatchers.model().attribute("user", Matchers.hasSize(1)));
    }

    @Test
    public void updateUserFormtest() throws Exception {
        System.out.println("Update User Form Test");

        Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(mockUser));

        mockMvc.perform(MockMvcRequestBuilders.get("/updateuser/{id}",1)
                .content(USER_JSON)
                .header("Accept", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void addUserTest() throws Exception {
        System.out.println("Add User Test");
        String username = "TEST";
        String password = "TEST";
        String role = "TEST";
        String enabled = "true";

        Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(mockUser));
        Mockito.when(userService.addUser(mockUser)).thenReturn("added");

        mockMvc.perform(MockMvcRequestBuilders.post("/AddUser")
                .param("username", username)
                .param("password", password)
                .param("enabled", enabled)
                .param("role", role)
                .content(USER_JSON)
                .header("Accept", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void updateUserTest() throws Exception {
        System.out.println("Update User Test");
        String username = "TEST";
        String password = "TEST";
        String role = "TEST";

        Mockito.when(userService.getUsers()).thenReturn(Arrays.asList(mockUser));
        Mockito.when(userService.updateUser(mockUser)).thenReturn("added");

        mockMvc.perform(MockMvcRequestBuilders.post("/UpdateUser")
                .param("username", username)
                .param("id", "1")
                .param("password", password)
                .param("role", role)
                .content(USER_JSON)
                .header("Accept", "application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void deleteEmployeeTest() throws Exception {
        System.out.println("Delete User Test");
        int i = 1;
        Mockito.when(userService.deleteUser(i)).thenReturn("deleted");

        mockMvc.perform(MockMvcRequestBuilders.delete("/removeuser/{id}", i)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
