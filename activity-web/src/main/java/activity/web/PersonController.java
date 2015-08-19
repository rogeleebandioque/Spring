package activity.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.ui.Model;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.commons.io.FileUtils;
import java.util.*;
import org.apache.commons.validator.routines.DateValidator;
import activity.core.model.Person;
import activity.core.model.Contacts;
import activity.core.service.PersonServiceImpl;
import activity.core.model.Name;
import activity.core.model.Roles;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class PersonController {

    private PersonFormValidator personFormValidator;
    private PersonServiceImpl personService;
    private final Logger logger = LoggerFactory.getLogger(PersonController.class);

    @Autowired
    public void setPersonFormValidator(PersonFormValidator personFormValidator) {
        this.personFormValidator = personFormValidator;
    }

    @Autowired
    public void setPersonService(PersonServiceImpl personService) {
        this.personService = personService;
    }

    @RequestMapping(value = "/Login", method = RequestMethod.GET)
    public String login(@RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            Model model) {

        if (error != null) {
            model.addAttribute("error", "Error");
        }

        if (logout != null) {
            model.addAttribute("msg", "You've been logged out successfully.");
        }

        return "/tools/Login";
    }

    @RequestMapping(value = "/Persons", method = RequestMethod.GET)
    public String displayPerson(ModelMap model) {
        logger.debug("displayPerson()");
        return "persons/person";
    }

    @RequestMapping(value = "/Persons",
            method = RequestMethod.GET,
            headers = "Accept=application/json")
    @ResponseBody
    public List<Person> populatePersons() {
        logger.debug("Getting all persons..");
        return personService.getPerson();
    }

    @RequestMapping(value = "SearchPersons",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public List<Person> getPersons(
            @RequestParam(value = "listBy", defaultValue = "grade") String listBy,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @RequestParam(value = "search", defaultValue = "") String search) {
        logger.debug("Searching Person...");
        return personService.searchPerson(search, listBy, order);
    }

    @RequestMapping(value = "SearchRole",
            method = RequestMethod.POST,
            headers = "Accept=application/json")
    @ResponseBody
    public Set<Person> searchRole(
            @RequestParam(value = "listBy", defaultValue = "1") String listBy) {
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
        return "persons/userform";
    }

    @RequestMapping(value = "/update{id}", method = RequestMethod.GET)
    public String updatePerson(@PathVariable int id, Model model) {
        logger.debug("updatePerson()");

        Person person = personService.getPersons(id);
        model.addAttribute("personForm", person);

        Set<Contacts> contact = person.getContact();
        model.addAttribute("contact", contact);

        Set<Roles> roles = person.getRole();
        model.addAttribute("roles", roles);

        populateModel(model);
        return "persons/userform";
    }

    @RequestMapping(value = "SaveUpdate", method = RequestMethod.POST)
    public String saveOrUpdateUser(@ModelAttribute("personForm") Person person,
            BindingResult result,
            @RequestParam(value = "r", required = false) String[] roles,
            @RequestParam(value = "contactDetail", required = false) String[] detail,
            @RequestParam(value = "contactType", required = false) String[] type,
            Model model,
            final RedirectAttributes redirectAttributes) {
        logger.debug("saveOrUpdateUser()");
        Set<Roles> r = new HashSet();
        Set<Contacts> c = new HashSet();
        if (roles != null) {
            r = addRole(roles);
            person.setRole(r);
        }

        if (detail != null) {
            c = contactDetails(detail, type);
            person.setContact(c);
        }

        personFormValidator.validate(person, result);
        if (result.hasErrors()) {
            model.addAttribute("roles", r);
            model.addAttribute("personForm", person);
            model.addAttribute("contact", c);
            populateModel(model);
            return "persons/userform";
        }

        if (person.isNew()) {
            redirectAttributes.addFlashAttribute("msg", "User added successfully!");
            personService.addPersons(person);
        } else {
            redirectAttributes.addFlashAttribute("msg", "User updated successfully!");
            personService.updatePersons(person);
        }

        return "redirect:Persons";

    }

    @RequestMapping(value = "/remove/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public boolean deleteEmployee(@PathVariable int id) {
        try {
            logger.debug("deleting person");
            personService.deletePersons(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @RequestMapping(value = "/uploadForm", method = RequestMethod.POST)
    public String uploadFormHandler(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "data") MultipartFile files, Model model) {
        if (!files.getContentType().equals("text/plain")) {
            model.addAttribute("personForm", new Person());
            model.addAttribute("message", "Invalid file format!");

            return "persons/userform";
        } else {
            Person person = new Person();
            Name n = person.getNames();
            Set<Contacts> c = person.getContact();
            Set<Roles> r = person.getRole();
            if (!files.isEmpty()) {
                try {
                    byte[] bytes = files.getBytes();

                    File dir = new File("Uploaded Files");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }
                    File serverFile = new File(dir.getAbsolutePath()
                            + File.separator + name);
                    BufferedOutputStream stream = new BufferedOutputStream(
                            new FileOutputStream(serverFile));
                    stream.write(bytes);
                    stream.close();

                    List<String> personDetails = FileUtils.readLines(serverFile);

                    for (String details : personDetails) {
                        String[] detail = details.split(":");
                        switch (detail[0]) {
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
                                c.add(new Contacts(contacts[1], contacts[0]));
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
                                switch (detail[1]) {
                                    case "police":
                                        r.add(new Roles(1, "Police"));
                                        break;
                                    case "politician":
                                        r.add(new Roles(2, "Politician"));
                                        break;
                                    case "soldier":
                                        r.add(new Roles(3, "Soldier"));
                                        break;
                                    case "celebrity":
                                        r.add(new Roles(4, "Celebrity"));
                                        break;
                                    case "worker":
                                        r.add(new Roles(5, "Worker"));
                                        break;
                                }
                                break;

                            default:
                                break;
                        }//switch
                    }

                } catch (Exception e) {
                    System.out.println(e);

                }
                model.addAttribute("roles", r);
                model.addAttribute("personForm", person);
                model.addAttribute("contact", c);
                populateModel(model);
                return "persons/userform";
            } else {
                model.addAttribute("message", "File Empty!");
                return "persons/userform";
            }
        }
    }

    public void populateModel(Model model) {
        List<String> roleId = new ArrayList<>();
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
        if (dt == null) {
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
        } catch (NumberFormatException e) {
            input = 100;
        }
        return input;
    }

    public Set contactDetails(String[] cDetails, String[] cType) {
        Set cD = new HashSet();
        for (int i = 0; i < cDetails.length; i++) {
            Contacts c = new Contacts(cDetails[i], cType[i].toLowerCase());
            cD.add(c);
        }
        return cD;
    }

    public Set<Roles> addRole(String[] roles) {
        Set r = new HashSet();
        for (int i = 0; i < roles.length; i++) {
            switch (roles[i].toLowerCase()) {
                case "police":
                    r.add(new Roles(1, "Police"));
                    break;
                case "politician":
                    r.add(new Roles(2, "Politician"));
                    break;
                case "soldier":
                    r.add(new Roles(3, "Soldier"));
                    break;
                case "celebrity":
                    r.add(new Roles(4, "Celebrity"));
                    break;
                case "worker":
                    r.add(new Roles(5, "Worker"));
                    break;
            }
        }
        return r;
    }

}
