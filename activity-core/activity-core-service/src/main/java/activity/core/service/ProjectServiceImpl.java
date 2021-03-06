package activity.core.service;

import activity.core.dao.ProjectDaoImpl;
import activity.core.model.Projects;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl implements ProjectServiceInterface {

    private ProjectDaoImpl projectDaoImpl;

    @Autowired
    public void setProjectDaoImpl(ProjectDaoImpl projectDaoImpl) {
        this.projectDaoImpl = projectDaoImpl;
    }

    @Transactional(readOnly = true)
    public List<Projects> getProjects() {
        return projectDaoImpl.getProjects();
    }

    @Transactional(readOnly = true)
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
