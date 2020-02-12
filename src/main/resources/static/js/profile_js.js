$("#user-form").submit(function (e) {

    e.preventDefault();

    $.ajax({
        url: "/sendRequest",
        type: "GET",
        data: $('#user-form').serialize(),
        success: function success(data) {
            alert("success!");
        },
        error: function error(xhr) {
            return "404Error";
        }
    });
});