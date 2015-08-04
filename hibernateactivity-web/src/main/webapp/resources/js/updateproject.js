$(document).ready(function() {
    $('#projectForm').submit(function(event) {
        alert("submit");
        var id = $('#project_id').val();
        var project_name = $('#project_name').val();
        var start_date= $('#start_date').val();
        var end_date = $('#end_date').val();
        var person_id = $('select#team').val();
        var persons =[];     
        for(var i=0; i<person_id.length; i++) {
            persons[i] = {"id":person_id[i]};
        }
        
        var json = {"project_id": id, 
                    "project_name" : project_name, 
                    "start_date": start_date, 
                    "end_date": end_date,
                    "per_proj": persons
        };

        console.log(json);

        var ajaxCall = $.ajax({
            url: "/UpdateProject",
            type: "PUT",
            data: JSON.stringify(json),
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }        
        });
        ajaxCall.done(function(data) {
            if (data == true) {
               $("#message").html("Project Updated!");
            } else {
               $("#message").html("Unable to update Project");
            }
        });
        event.preventDefault();
    });
}); 
