$(document).ready(function() {
    var c = $('#personForm');

    $('#personForm').submit(function(event) {
        
        var ajaxCall = $.ajax({
            url: "/uploadForm",
            type: "PUT",
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }        
        });
        ajaxCall.done(function(data) {
            if (data === true) {
               $("#message").html("Person Updated!");
            } else {
               $("#message").html("Unable to update Person");
            }
        });
        event.preventDefault();
    });
}); 
