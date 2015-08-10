package activity.core.dao;

import java.util.*;
import activity.core.dao.actions.projects.ListProject;
import activity.core.dao.actions.projects.AddProject;
import activity.core.dao.actions.projects.UpdateProject;
import activity.core.dao.actions.projects.DeleteProject;
import activity.core.dao.actions.projects.GetProject;

import activity.core.model.Projects;

public class ProjectDaoImpl implements ProjectDao {
    public List<Projects> getProjects() {
        return HibernateUtil.perform(new ListProject(), List.class);         
    }

    public Projects getProject(Integer id) {
        return HibernateUtil.perform(new GetProject(id), Projects.class);         
    }

    public String addProject(Projects project) {
        return HibernateUtil.perform(new AddProject(project), Boolean.class) ? "Unable to Add Project" : "Project Added";    
    }

    public String updateProject(Projects project) {
       return HibernateUtil.perform(new UpdateProject(project), Boolean.class) ? "Unable to Update Project!" : "Project Updated!";     
    }

    public String deleteProject(int projectId) {
       return HibernateUtil.perform(new DeleteProject(projectId), Boolean.class) ? "Project Deleted!" : "Unable to Delete Project!";
    }


}
