$(document).ready(function() {

    $(".delete").click(function(e) {
        if (!confirm("Are you sure you want to delete the employee?")) {
        return;
        }
        var id = $(this).val();
        var ajaxCall = $.ajax({
            url: "remove/" + id,
            type: "DELETE",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }
        });
        ajaxCall.done(function(data) {
            if (data == true) {
                var tr = $(e.target).closest("tr");
                tr.remove();
            } else {
                alert("Unable to delete employee.");
            }
        });
        ajaxCall.fail(function() {
            alert("Unable to delete.");
        });
    });

});
