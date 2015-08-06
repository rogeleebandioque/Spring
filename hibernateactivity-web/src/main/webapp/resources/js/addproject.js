$(document).ready(function() {

    var addperson = function(e){
        var name = $(e).html();
        $("#chosen").append('<option class=\"remove\" value=\"'+ $(e).val() +'\">'+name+'</option>');
        $(e).remove();
    };
     var rmperson = function(t){
        var name = $(t).html();
        $("#team").append('<option class=\"choice\" value=\"'+ $(t).val() +'\">'+name+'</option>');
        $(t).remove();
    };
    $("#chosen").on("dblclick",".remove",function(e){
        rmperson($(this));
        e.preventDefault();
    });
    $("#team").on("dblclick",".choice",function(e){
        addperson($(this));
        e.preventDefault();
    });

    $('#projectForm').submit(function(event) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        var id = $('#project_id').val();
        var project_name = $('#project_name').val();
        var start_date= $('#start_date').val();
        var end_date = $('#end_date').val();
        var persons= [];
        $('#chosen option').each(function(e){
            persons.push({"id":$(this).val()});
        });
        
        var json = {"project_id": id, 
                    "project_name" : project_name, 
                    "start_date": start_date, 
                    "end_date": end_date,
                    "per_proj": persons
        };

        console.log(json);

        var ajaxCall = $.ajax({
            url: "/AddProject",
            type: "POST",
            data: JSON.stringify(json),
            beforeSend: function(xhr) {
                xhr.setRequestHeader(header, token);
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }        
        });
        ajaxCall.done(function(data) {
            if (data == true) {
                alert("Project Added!");
               $("#message").html("Project Added!");
            } else {
               $("#message").html("Unable to add Project");
            }
        });
        ajaxCall.fail(function() {
            alert("Unable to Add.");
        });
        event.preventDefault();
    });
}); 
