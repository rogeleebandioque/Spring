package activity.web;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import org.apache.commons.validator.routines.DateValidator;

public class Operations {
    
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
