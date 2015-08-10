package activity.core.dao.actions.projects;

import activity.core.dao.Command;
import activity.core.model.Projects;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class UpdateProject implements Command {

    private Session session;
    private Projects project;

    public UpdateProject(Projects project) {
        this.project = project;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        session.update(project);
        return session.createQuery("FROM Projects WHERE id =" + project.getProject_id()).list().isEmpty();
    }
}
