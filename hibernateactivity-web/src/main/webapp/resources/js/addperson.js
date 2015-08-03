$(document).ready(function() {
    $('#personForm').submit(function(event) {
        var first_name = $('#names\\.first_name').val();
        var last_name = $('#names\\.last_name').val();
        var address = $('#address').val();
        var gender = $('input[name=gender]:checked').val();
        var grade = $('#grade').val();
        var bday = $('#bday').val();
        var date_hired = $('#date_hired').val();
        var age = $('#age').val();
        var currently_employed = $('input[name=currently_employed]:checked').val();

        var json = { "names": 
                        {
                        "names.first_name" : first_name, 
                        "names.last_name" : last_name
                        }, 
                    "address" : address, 
                    "gender": gender , 
                    "bday":bday, 
                    "date_hired":date_hired,
                    "age":age, 
                    "currently_employed":currently_employed
                    };

                console.log(json);
        $.ajax({
            url: "/SaveUpdate",
            type: "POST",
            data: JSON.stringify(json),
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }        
        })
        .done(function() {
            var respContent = "Added";
            $("#message").html("Added");
        });
        event.preventDefault();
    });
}); 
