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
import org.springframework.web.bind.annotation.ResponseStatus;
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

	@RequestMapping(value ="/Persons", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("displayPerson()");
        model.addAttribute("person",service.getPerson());
        return "Main";
    }

    @RequestMapping(value="Persons", 
                    method=RequestMethod.POST,
                    headers="Accept=application/json")
    @ResponseBody
    public List<Person> getPersons(@RequestParam(value="listBy",defaultValue="grade") String listBy, 
                @RequestParam(value="order",defaultValue="asc") String order,
                @RequestParam(value="search",defaultValue="") String search){
        logger.debug("Searching Person...");
        return service.searchPerson(search,listBy,order);
    }

    @RequestMapping(value = "AddPerson", method = RequestMethod.GET)
	public String addPersonForm(Model model) {
        logger.debug("userForm()");

		model.addAttribute("personForm", new Person());
        populateModel(model);
        return "addform";
	}

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updatePerson(@PathVariable int id, Model model) {
        logger.debug("updatePerson()");
        Person person = service.getPersons(id);
        Set<Roles> roles = person.getRole();
        Set<Contacts> contact = person.getContact();
        model.addAttribute("personForm", person);
        model.addAttribute("roles", roles);
        model.addAttribute("contact", contact);
        populateModel(model);
        return "updateform";
    }

    @RequestMapping(value ="/UpdatePerson", 
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,        
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean update(@RequestBody Person person) {
        logger.debug("Updating person..");        
        try {
            service.updatePersons(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value ="/AddPerson", 
                    method = RequestMethod.POST,
                    produces = MediaType.APPLICATION_JSON_VALUE,        
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean add(@RequestBody Person person) {
        logger.debug("Adding person..");        
        try {
            service.addPersons(person);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

     @RequestMapping(value="/remove/{id}",
                        method=RequestMethod.DELETE,
                        produces = MediaType.APPLICATION_JSON_VALUE,
                        consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean deleteEmployee(@PathVariable int id) {
        try {
            logger.debug("deleting person");
            String mes = service.deletePersons(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    } 

    @RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("file") MultipartFile file,@RequestParam("name") String name, Model model) {
        Person person = new Person();
        Name n = person.getNames();
        Set<Contacts> c = person.getContact();
        Set<Roles> r = person.getRole();
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
 
                File dir = new File("Uploaded Files");
                if (!dir.exists())
                    dir.mkdirs();
                File serverFile = new File(dir.getAbsolutePath()
                        + File.separator + name);
                BufferedOutputStream stream = new BufferedOutputStream(
                        new FileOutputStream(serverFile));
                stream.write(bytes);
                stream.close();
            
            
            List<String> personDetails = FileUtils.readLines(serverFile);
                    System.out.println(personDetails.size());
            
                for(String details: personDetails){
                    System.out.println(details);
                    String[] detail = details.split(":");        
                    switch (detail[0]){
                        case "first name":
                            n.setFirst_name(detail[1]);
                            break;

                        case "last name":
                            n.setLast_name(detail[1]);
                            break;

                        case "address":
                            person.setAddress(detail[1]);
                            break;

                        case "age":
                            person.setAge(operations.integerValid(detail[1]));
                           break;

                        case "grade":
                            person.setGrade(operations.integerValid(detail[1]));
                           break;

                        case "birthday":
                            person.setBday(operations.dateValid(detail[1]));
                            System.out.println(person.getBday());
                            break;

                        case "contact":
                            System.out.println(detail[1]);
                            String[] contacts = detail[1].split("=");
                            c.add(new Contacts(contacts[1],contacts[0]));
                            break;

                        case "gender":
                            person.setGender(detail[1]);
                            break;

                        case "date hired":
                            person.setDate_hired(operations.dateValid(detail[1]));
                            break;

                        case "currently employed":
                            person.setCurrently_employed(detail[1]);
                            break;

                        case "role":
                            switch(detail[1]){
                                case "police": 
                                    r.add(new Roles(1,"Police"));
                                    break;
                                case "politician":
                                    r.add(new Roles(2,"Politician"));
                                    break;
                                case "soldier":
                                    r.add(new Roles(3,"Soldier"));
                                    break;
                                case "celebrity":
                                    r.add(new Roles(4,"Celebrity"));
                                    break;
                                case "worker":
                                    r.add(new Roles(5,"Worker"));
                                    break;
                            }
                            break;
                            
                        default:
                            break;
                    }//switch
                }
		    
            } catch (Exception e) {
            }
            model.addAttribute("roles", r);
            model.addAttribute("personForm", person);
            model.addAttribute("contact", c);
            populateModel(model);
           return "addform";
        } else {
            return "addform";
        }
        
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
