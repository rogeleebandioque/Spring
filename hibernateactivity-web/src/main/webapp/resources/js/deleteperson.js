$(document).ready(function() {
    var deleteLink = $("a:contains('Delete')");
    $(deleteLink).click(function(event) {
    console.log(deleteLink);
    $.ajax({
        url: $(event.target).attr("href"),
        type: "DELETE",

        beforeSend: function(xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function() {
            var respContent = "";
            var rowToDelete = $(event.target).closest("tr");
            rowToDelete.remove();
            respContent += "<span class='success'>Person was deleted:";
            respContent += "]</span>";
            $("#response").html(respContent);
            }
        });
    event.preventDefault();
    });
}); 
