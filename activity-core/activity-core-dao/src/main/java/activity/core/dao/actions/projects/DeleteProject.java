package activity.core.dao.actions.projects;

import activity.core.dao.Command;
import activity.core.model.Projects;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class DeleteProject implements Command {

    private Session session;
    private Integer id;

    public DeleteProject(Integer id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        Projects project = (Projects) session.get(Projects.class, id);
        session.delete(project);
        return session.createQuery("FROM Projects WHERE project_id =" + id).list().isEmpty();
    }
}
