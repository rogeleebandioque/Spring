package activity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;

import activity.core.service.ProjectService;
import activity.core.service.PersonService;
import activity.core.model.Projects;
import activity.core.model.Person;

@Controller
public class ProjectsController{
    private ProjectService projectService;
    private PersonService personService;

    private final Logger logger = LoggerFactory.getLogger(ProjectsController.class);

    @Autowired
    public void setProjectService(ProjectService projectService){
        this.projectService = projectService;
    }    
    @Autowired
    public void setPersonService(PersonService personService){
        this.personService = personService;
    } 

	@RequestMapping(value ="projects", method = RequestMethod.GET)
    public String displayProjects(ModelMap model) {
    logger.debug("Displaying projects...");
    model.addAttribute("projects",projectService.getProjects());
    return "/projects/projects";
    }   

    @RequestMapping(value = "AddProject", method = RequestMethod.GET)
	public String addProjectForm(Model model) {
        logger.debug("Add Project Form...");
        populateModel(model);
		model.addAttribute("projectForm", new Projects());
        return "/projects/addprojectform";
	}

    @RequestMapping(value ="/AddProject", 
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,        
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean addProject(@RequestBody Projects project) {
        
        logger.debug("Adding project..");        
        try {
            Set<Person> id = project.getPer_proj();
            Set<Person> members = new HashSet<Person>();

            for(Person i:id) {
                members.add(personService.getPersons(i.getId()));
                System.out.println(i.getId());
            }
            project.setPer_proj(members);        
            projectService.addProject(project);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "upproject/{id}", method = RequestMethod.GET)
    public String updateProject(@PathVariable int id, Model model) {
        logger.debug("Update Project...");
        Projects project = projectService.getProject(id);
        Set<Person> person = project.getPer_proj();
        model.addAttribute("projectForm", project);
        model.addAttribute("team", person);
        populateModel(model);
        return "/projects/updateprojectform";
    }
    
    @RequestMapping(value ="/UpdateProject", 
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,        
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean update(@RequestBody Projects project) {
        logger.debug("Updating project..");        
                try {
            Set<Person> id = project.getPer_proj();
            Set<Person> members = new HashSet<Person>();

            for(Person i:id) {
                members.add(personService.getPersons(i.getId()));
                System.out.println(i.getId());
            }
            project.setPer_proj(members);        
            projectService.updateProject(project);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value="/removeproject/{id}",
                        method=RequestMethod.DELETE,
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

    public void populateModel(Model model){
        List<Person> person = personService.getPerson();
        model.addAttribute("person", person);
    }
}
