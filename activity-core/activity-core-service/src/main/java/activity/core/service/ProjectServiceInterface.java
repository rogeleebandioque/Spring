package activity.core.service;

import activity.core.model.Projects;
import java.util.List;

interface ProjectServiceInterface {
    public List<Projects> getProjects();
    public Projects getProject(Integer projectId);
    public String deleteProject(int projectId);
    public String addProject(Projects project);
    public String updateProject(Projects project);
}
