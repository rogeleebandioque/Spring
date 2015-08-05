$(document).ready(function() {
   var addperson = function(event) {
        var id = $('#id').val();
        var first_name = $('#names\\.first_name').val();
        var last_name = $('#names\\.last_name').val();
        var address = $('#address').val();
        var gender = $('input[name=gender]:checked').val();
        var grade = $('#grade').val();
        var bday = $('#bday').val();
        var date_hired = $('#date_hired').val();
        var age = $('#age').val();
        var currently_employed = $('input[name=currently_employed]:checked').val();

        var contact = [];
        var detail = [];
        var roles = [];
        var contacts = []

        $(".contactDetail").each(function() {
            contact.push($(this).val());
        });

        $(".contactType").each(function() {
            detail.push($(this).val());
        });
        console.log(contacts);

        for(i=0; i<contact.length; i++){
            contacts[i] = {"contact" : contact[i], "type": detail[i]};
        }

        $("input:checkbox[name=r]:checked").each(function(){
            var id = $(this).val();    
            console.log(id);        
            if(id == "Police") {
                roles.push({"id": 1, "roleName": id});
            } if(id == "Politician") {
                roles.push({"id": 2, "roleName": id});
            } if(id == "Celebrity") {
                roles.push({"id": 3, "roleName": id});
            } if(id == "Soldier") {
                roles.push({"id": 4, "roleName": id});
            } if(id == "Worker") {
                roles.push({"id": 5, "roleName": id});
            } 
        });
        console.log(roles);
        var json = {"id": id, 
                    "names": 
                    {
                        "first_name" : first_name, 
                        "last_name" : last_name
                    }, 
                    "address" : address, 
                    "gender": gender , 
                    "bday":bday, 
                    "grade":grade,
                    "date_hired":date_hired,
                    "age":age, 
                    "currently_employed":currently_employed,
                    "contact" : contacts,
                    "role": roles
        };

        var ajaxCall = $.ajax({
            url: "/AddPerson",
            type: "POST",
            data: JSON.stringify(json),
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }        
        });
        ajaxCall.done(function(data) {
            if (data == true) {
               alert("Person Added");
               $("#message").html("Person Added!");
            } else {
               $("#message").html("Unable to add Person");
            }
        });
    };

    $("#container").on("submit","#personForm",function(e){
        addperson();
        e.preventDefault();
    });
}); 

