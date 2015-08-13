$(document).ready(function () {
    $(".deleteproj").click(function (e) {
        var header = $("meta[name='_csrf_header']").attr("content");
        var token = $("meta[name='_csrf']").attr("content");
        if (!confirm("Delete Project?")) {
            return;
        }
        var id = $(this).val();
        var ajaxCall = $.ajax({
            url: "removeproject/" + id,
            type: "DELETE",
            beforeSend: function (xhr) {
                xhr.setRequestHeader(header, token);
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            }
        });
        ajaxCall.done(function (data) {
            if (data == true) {
                var tr = $(e.target).closest("tr");
                tr.remove();
            } else {
                alert("Unable to delete employee.");
            }
        });
        ajaxCall.fail(function () {
            alert("Unable to delete.");
        });
    });

});
