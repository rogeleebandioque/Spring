package hibernateactivity.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

import java.util.*;
import hibernateactivity.core.model.Person;
import hibernateactivity.core.model.Contacts;

public class PersonFormValidator implements Validator {
    private EmailValidator valid = EmailValidator.getInstance();  
    private Operations operations;

    public void setOperations(Operations operations){
        this.operations = operations;
    }

    public boolean supports(Class<?> clazz) {
       return Person.class.equals(clazz);
    }

    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "names.first_name", "erform.name.firstname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "names.last_name", "erform.name.lastname");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "address", "erform.address");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "bday", "erform.bday");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "date_hired", "erform.date_hired");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "gender", "erform.gender");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "currently_employed", "erform.currently_employed");
        
        if(person.getAge()==null||person.getAge() <= 0){
            errors.rejectValue("age","erform.age");
        }
        if(person.getGrade()==null||person.getGrade() <= 0){
            errors.rejectValue("grade","erform.grade");
        }
   }

}
