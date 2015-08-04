package hibernateactivity.core.service;

import hibernateactivity.core.dao.ProjectDaoImpl;
import hibernateactivity.core.model.Projects;
import java.util.List;

public class ProjectService {
    private ProjectDaoImpl projectDaoImpl;

    public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl){
        this.projectDaoImpl = projectDaoImpl;
    }

    public List<Projects> getProjects() {
        return projectDaoImpl.getProjects();
    }

    public Projects getProject(Integer projectId) {
        return projectDaoImpl.getProject(projectId);
    }

    public String deleteProject(int projectId) {
        return projectDaoImpl.deleteProject(projectId);
    }

    public String addProject(Projects project) {
        return projectDaoImpl.addProject(project);
    }

    public String updateProject(Projects project) {
        return projectDaoImpl.updateProject(project);
    }
}
