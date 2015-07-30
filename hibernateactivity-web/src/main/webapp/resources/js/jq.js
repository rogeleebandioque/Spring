$(document).ready(function() {
    var wrapper = $("#contactNumber");
    $("#e-mail").click(function(){ 
            $(wrapper).append('<div><input type="hidden" name="contactType" value="e-mail"/>E-mail:' +
                                       '<input type="text" name="contactDetail" size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });
   
    $("#cellphone").click(function(){ 
            $(wrapper).append('<div id="hey"><input type="hidden" name="contactType" value="cellphone"/>Cellphone#: '+
                                       '<input type="text" name="contactDetail"size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });

    $("#telephone").click(function(){ 
            $(wrapper).append('<div><input type="hidden" name="contactType" value="telephone"/>Telephone#:'+
                                       '<input type="text" name="contactDetail"size="10" required="true"/>'+
                                       '<button class="remove_field">Remove</button></div>');
    });

    $("#person").click(function(){ 
            $("#displist").empty();
            $("#displist").append('<form action="SearchPerson" method="GET">' +
                                    '<input type="text" name="search" placeHolder="Search.."/><br/>'+
                                    '<input type="radio" name="listBy" value="last_name"> Last Name '+
                                    '<input type="radio" name="listBy" value="date_hired"> Date Hired '+
                                    '<input type="radio" name="listBy" value="grade"> Grade <br/> '+
                                    '<input type="radio" name="order" value="asc"> Ascending '+
                                    '<input type="radio" name="order" value="desc"> Descending <br/>'+
                                    '<input type="submit" value="Search">'+
                                    '</form>');
    });

    $("#prole").click(function(){ 
            $("#displist").empty();
            $("#displist").append('<form action="SearchRole" method="GET">' +
                                    '<input type="radio" name="listBy" value="1"> Police '+
                                    '<input type="radio" name="listBy" value="2"> Politician '+
                                    '<input type="radio" name="listBy" value="3"> Soldier '+
                                    '<input type="radio" name="listBy" value="4"> Celebrity '+
                                    '<input type="radio" name="listBy" value="5"> Worker <br/>'+
                                    '<input type="submit" value="Search">'+
                                    '</form>');
    });

    $(wrapper).on("click",".remove_field", function(){ 
        $(this).parent('div').remove();
    })
}); 

