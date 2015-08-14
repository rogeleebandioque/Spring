package activity.web;

import activity.core.model.Projects;
import java.util.Date;

public class ProjectBuilder {

    private int project_id;
    private String project_name;
    private Date start_date = new Date();
    private Date end_date = new Date();

    public ProjectBuilder() {
    }

    public static ProjectBuilder project()  {
        return new ProjectBuilder();
    }

    public ProjectBuilder project_id() {
        project_id = 1;
        return this;
    }

    public ProjectBuilder project_name() {
        project_name = "TEST";
        return this;
    }

    public ProjectBuilder start_date() {
        start_date = new Date();
        return this;
    }

    public ProjectBuilder end_date() {
        end_date = new Date();
        return this;
    }

    public Projects build(){
        return new Projects(project_name,start_date,end_date);
    }
}
