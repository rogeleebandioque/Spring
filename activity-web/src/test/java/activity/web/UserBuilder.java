package activity.web;

import activity.core.model.Users;

public class UserBuilder {

    private int id;
    private String username;
    private String password;
    private Boolean enabled;
    private String role;

    public UserBuilder() {
    }

    public UserBuilder user() {
        return this;
    }

    public UserBuilder id() {
        id = 1;
        return this;
    }

    public UserBuilder username() {
        username = "Test";
        return this;
    }

    public UserBuilder password() {
        password = "Test";
        return this;
    }

    public UserBuilder enabled() {
        enabled = true;
        return this;
    }

    public UserBuilder role() {
        role = "Test";
        return this;
    }

    public Users build() {
        return new Users(username,password,enabled,role);
    }

}
