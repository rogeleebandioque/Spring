package activity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import activity.core.service.ProjectServiceImpl;
import activity.core.service.PersonServiceImpl;
import activity.core.model.Projects;
import activity.core.model.Person;
import com.sun.net.httpserver.HttpServer;
import javax.servlet.http.HttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProjectsController {

    private ProjectFormValidator projectFormValidator;
    private ProjectServiceImpl projectService;
    private PersonServiceImpl personService;
    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    @Autowired
    public void setProjectFormValidator(ProjectFormValidator projectFormValidator) {
        this.projectFormValidator = projectFormValidator;
    }

    @Autowired
    public void setProjectService(ProjectServiceImpl projectService) {
        this.projectService = projectService;
    }

    @Autowired
    public void setPersonService(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "projects", method = RequestMethod.GET)
    public String displayProjects(ModelMap model) {
        logger.debug("Displaying projects...");
        List<Projects> project = projectService.getProjects();
        model.addAttribute("projects", project);
        return "/projects/projects";
    }

    @RequestMapping(value = "AddProject", method = RequestMethod.GET)
    public String addProjectForm(Model model) {
        logger.debug("Add Project Form...");
        populateModel(model);
        model.addAttribute("projectForm", new Projects());
        return "/projects/projectForm";
    }

    @RequestMapping(value = "upproject{id}", method = RequestMethod.GET)
    public String updateProject(@PathVariable int id, Model model) {
        logger.debug("Update Project...");

        Projects project = projectService.getProject(id);
        model.addAttribute("projectForm", project);

        Set<Person> person = project.getPer_proj();
        model.addAttribute("team", person);
        populateModel(model);
        return "/projects/projectForm";
    }

    @RequestMapping(value = "SaveUpdateProject", method = RequestMethod.POST)
    public String saveOrUpdateProject(@ModelAttribute("projectForm") Projects project,
            BindingResult result,
            @RequestParam(value = "members", required = false) String[] members,
            Model model,
            final RedirectAttributes redirectAttributes) {
        logger.debug("Save projects");
        Set<Person> team = new HashSet<>();
        if (members != null) {
            
            System.out.println("hello");
            for (String i : members) {
                team.add(personService.getPersons(Integer.parseInt(i)));
            }
        }
        project.setPer_proj(team);

        projectFormValidator.validate(project, result);
        if (result.hasErrors()) {
            model.addAttribute("projectForm", project);
            populateModel(model);
            return "projects/projectForm";
        }

        if (project.getProject_id() == 0) {
            redirectAttributes.addFlashAttribute("msg", "Project added successfully!");
            projectService.addProject(project);
        } else {
            redirectAttributes.addFlashAttribute("msg", "Project updated successfully!");
            projectService.updateProject(project);
        }

        return "redirect:projects";

    }

    @RequestMapping(value = "/removeproject/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean deleteProject(@PathVariable int id) {
        try {
            logger.debug("deleting project ...");
            projectService.deleteProject(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void populateModel(Model model) {
        List<Person> person = personService.getPerson();
        model.addAttribute("person", person);
    }
}
