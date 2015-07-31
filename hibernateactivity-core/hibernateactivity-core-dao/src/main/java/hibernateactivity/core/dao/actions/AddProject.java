package hibernateactivity.core.dao.actions;

import hibernateactivity.core.dao.Command;
import hibernateactivity.core.model.Projects;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class AddProject implements Command {

    private Session session;
    private Projects project;

    public AddProject(Projects project) {
        this.project = project;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        session.save(project);
        return session.createQuery("FROM Projects WHERE id =" + project.getProject_id()).list().isEmpty();
    }
}
