$("#login-form").submit(function (e) {

    e.preventDefault();

    $.ajax({
        url: "login",
        type: "GET",
        data: $('#login-form').serialize(),
        success: function success(data){
            alert("You are in!");
        },
        error: function error(xhr) {
            return "404Error";
        }
    });
});