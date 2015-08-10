package activity.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import org.apache.commons.io.FileUtils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;
import java.util.*;
import org.apache.commons.validator.routines.DateValidator;

import activity.core.model.Person;
import activity.core.model.Contacts;
import activity.core.service.PersonService;
import activity.core.model.Name;
import activity.core.model.Roles;
import activity.core.model.FileUpload;

@Controller
public class ActivityController{
    private PersonService personService;
    private final Logger logger = LoggerFactory.getLogger(ActivityController.class);
    
    @Autowired
    public void setPersonService(PersonService personService){
        this.personService = personService;
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

		return "/Login";
	}

	@RequestMapping(value ="/Persons", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("displayPerson()");
        Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)    
        SecurityContextHolder.getContext().getAuthentication().getAuthorities();
        model.addAttribute("person",personService.getPerson());
        for (SimpleGrantedAuthority authority : authorities) {
            model.addAttribute("role", authority.getAuthority());
            break;
        }
        return "/persons/Main";

    }

    @RequestMapping(value="Persons", 
                    method=RequestMethod.POST,
                    headers="Accept=application/json")
    @ResponseBody
    public List<Person> getPersons(@RequestParam(value="listBy",defaultValue="grade") String listBy, 
                @RequestParam(value="order",defaultValue="asc") String order,
                @RequestParam(value="search",defaultValue="") String search){
        logger.debug("Searching Person...");
        return personService.searchPerson(search,listBy,order);
    }

  	@RequestMapping(value ="SearchRole",
                   method=RequestMethod.POST,
                    headers="Accept=application/json")
    @ResponseBody
    public Set<Person> searchRole(@RequestParam(value="listBy",defaultValue="1") String listBy) {
        logger.debug("Searching role...");
        Integer orders = Integer.parseInt(listBy);
        Roles pr = personService.getByRole(orders);
        return pr.getPersonRole();
    }

    @RequestMapping(value = "AddPerson", method = RequestMethod.GET)
	public String addPersonForm(Model model) {
        logger.debug("userForm()");

		model.addAttribute("personForm", new Person());
        populateModel(model);
        return "persons/addform";
	}

    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String updatePerson(@PathVariable int id, Model model) {
        logger.debug("updatePerson()");
        Person person = personService.getPersons(id);
        Set<Roles> roles = person.getRole();
        Set<Contacts> contact = person.getContact();
        model.addAttribute("personForm", person);
        model.addAttribute("roles", roles);
        model.addAttribute("contact", contact);
        populateModel(model);
        return "persons/updateform";
    }

    @RequestMapping(value ="/UpdatePerson", 
                    method = RequestMethod.PUT,
                    produces = MediaType.APPLICATION_JSON_VALUE,        
                    consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean update(@RequestBody Person person) {
        logger.debug("Updating person..");        
        try {
            personService.updatePersons(person);
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
            personService.addPersons(person);
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
            String mes = personService.deletePersons(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    } 

    @RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
    public String uploadFileHandler(@RequestParam("file") MultipartFile file,
                                    @RequestParam("name") String name, Model model) {
        if (!file.getContentType().equals("text/plain")) {
            System.out.println(file.getContentType());
            model.addAttribute("personForm", new Person());
            model.addAttribute("message", "Invalid file format. Only text/txt files are accepted!");
            return "persons/addform";
        }else{
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
                                person.setAge(integerValid(detail[1]));
                               break;

                            case "grade":
                                person.setGrade(integerValid(detail[1]));
                               break;

                            case "birthday":
                                person.setBday(dateValid(detail[1]));
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
                                person.setDate_hired(dateValid(detail[1]));
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
               return "persons/addform";
            } else {
                return "persons/addform";
            }
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

    public Date dateValid(String date) {
        DateValidator dateVal = DateValidator.getInstance();
        Date dt = null;                    
        
        dt = dateVal.validate(date, "yyyy-MM-dd");
        if(dt == null) {
            date = "0001-01-01";
            dt = dateVal.validate(date, "yyyy-MM-dd");
        } 
        return dt; 
    }

    public int integerValid(String in) {
        boolean a = true;
        int input = 0;
        
            try {
                input = Integer.parseInt(in);
            } catch(NumberFormatException e) {
                input = 100;
            }
        return input;
    }
}
