$(document).ready(function() {
    var wrapper = $("#contactNumber");
    $("#e-mail").click(function(){ 
            $(wrapper).append('<div><input type="hidden" name="contactType" class="contactType" value="e-mail"/>E-mail:' +
                                       '<input type="text" name="contactDetail" class="contactDetail" size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });
   
    $("#cellphone").click(function(){ 
            $(wrapper).append('<div id="hey"><input type="hidden" name="contactType" class="contactType"value="cellphone"/>Cellphone#: '+
                                       '<input type="text" name="contactDetail" class="contactDetail"size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });

    $("#telephone").click(function(){ 
            $(wrapper).append('<div><input type="hidden" name="contactType" class="contactType" value="telephone"/>Telephone#:'+
                                       '<input type="text" name="contactDetail" class="contactDetail" size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });

    

    $(wrapper).on("click",".remove_field", function(){ 
        $(this).parent('div').remove();
    })

}); 

