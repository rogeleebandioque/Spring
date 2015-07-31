package hibernateactivity.web;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
 
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;
import java.util.*;

import hibernateactivity.core.model.Person;
import hibernateactivity.core.model.Contacts;
import hibernateactivity.core.service.Service;
import hibernateactivity.core.model.Name;
import hibernateactivity.core.model.Roles;
import hibernateactivity.web.PersonFormValidator;
import hibernateactivity.web.Operations;
import hibernateactivity.core.model.FileUpload;

@Controller
public class ActivityController{
    private Operations operations;    
    private Service service;
    private PersonFormValidator personFormValidator;
    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    
    public void setOperations(Operations operations){
        this.operations = operations;
    }   
 
    public void setPersonFormValidator(PersonFormValidator personFormValidator){
        this.personFormValidator = personFormValidator;
    } 

    public void setService(Service service){
        this.service = service;
    }    

	@RequestMapping(value = "/Login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
	    @RequestParam(value = "logout", required = false) String logout, 
        HttpServletRequest request, Model model) {

		if (error != null) {
			model.addAttribute("error", "Error");
		}

		if (logout != null) {
			model.addAttribute("msg", "You've been logged out successfully.");
		}

		return "Login";
	}

	@RequestMapping(value ="Persons", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("displayPerson()");

        List<Person> persons = new ArrayList<Person>();
        persons.addAll(getPersons());
        model.addAttribute("person",persons);
        return "Main";
    }

    @RequestMapping(value="Persons", method=RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Person> getPersons(){
        return service.getPerson();
    }

    @RequestMapping(value = "AddPerson", method = RequestMethod.GET)
	public String addPersonForm(Model model) {
        logger.debug("userForm()");

		Person person = new Person();
		model.addAttribute("personForm", person);
        populateModel(model);
        return "UserForm";
	}

    @RequestMapping(value ="/SaveUpdate", method = RequestMethod.GET,
                   produces = MediaType.APPLICATION_JSON_VALUE, 
                   consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void saveOrUpdateUser(@RequestBody Person person) {
        logger.debug("saveOrUpdateUser()");        
            service.addPersons(person);
    }

    @RequestMapping("person/random")
    @ResponseBody
    public String randomPerson() {
        System.out.println("yehet");
        return "done";
    }
 

    @RequestMapping(value="delete/{id}", method=RequestMethod.DELETE,
                    produces = MediaType.APPLICATION_JSON_VALUE, 
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public void deletePerson(@PathVariable int id) {
        logger.debug("delete persons()");
        String mes = service.deletePersons(id);
    }

    public void populateModel(Model model){
        List<String> roleId = new ArrayList<String>();
        roleId.add("Police");
        roleId.add("Politician");
        roleId.add("Soldier");
        roleId.add("Celebrity");
        roleId.add("Worker");
        model.addAttribute("roleId", roleId);
    }

}
