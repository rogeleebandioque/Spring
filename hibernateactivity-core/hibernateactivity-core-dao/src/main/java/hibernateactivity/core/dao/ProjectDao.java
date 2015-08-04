package hibernateactivity.core.dao;

import java.util.*;

import hibernateactivity.core.model.Projects;

interface ProjectDao {
    public List<Projects> getProjects();
    public String addProject(Projects project);
    public String updateProject(Projects project);
    public String deleteProject(int projectId);
    public Projects getProject(Integer id);
}
