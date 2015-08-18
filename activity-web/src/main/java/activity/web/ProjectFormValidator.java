package activity.web;

import activity.core.model.Person;
import activity.core.model.Projects;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class ProjectFormValidator implements Validator {
    public boolean supports(Class<?> clazz) {
       return Projects.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Projects project = (Projects) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "project_name", "erform.projname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "start_date", "erform.startdate");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "end_date", "erform.enddate");
   }
}
