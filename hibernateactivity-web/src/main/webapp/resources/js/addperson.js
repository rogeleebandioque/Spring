$(document).ready(function() {
    $('#newSmartphoneForm').submit(function(event) {
        var first_name = $('#names.first_name').val();
        var last_name = $('#names.last_name').val();
        var address = $('#address').val();
        var gender = $('#gender').val();
        var grade = $('#grade').val();
        var bday = $('#bday').val();
        var date_hired = $('#date_hired').val();
        var age = $('#age').val();
        var currently_employed = $('#currently_employed').val();

        var json = { "names.first_name" : first_name, "names.last_name" : last_name, 
                    "address" : address, "gender": gender , "bday":bday, "date_hired":date_hired,
                    "age":age, "currently_employed":currently_employed};

            $.ajax({
                url: $("#personForm").attr( "action"),
                data: JSON.stringify(json),
                type: "POST",
                beforeSend: function(xhr) {
                    xhr.setRequestHeader("Accept", "application/json");
                    xhr.setRequestHeader("Content-Type", "application/json");
                    $(".error").remove();
                    },
                success: function() {
                    var respContent = "Added";
                    $("#message").html(respContent);
                    },
        
        });
        event.preventDefault();
    });
}); 
