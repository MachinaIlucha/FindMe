$("#registration-form").submit(function (e) {

    e.preventDefault();

    $.ajax({
        url: "user-registration",
        type: "POST",
        data: $('#registration-form').serialize(),
        success: function success(data){
            alert("success!");
        },
        error: function error(xhr) {
            return "404Error";
        }
    });
});

/**
 *first javascript
 *first jquery usage
 --alert($("#test-ajax").text());--
 **/

// $("#test-ajax").click(function () {
//     $.ajax({
//         type: "GET",
//         url: "/test-ajax",
//         data: {},
//         success: function success() {
//             alert("success!");
//         },
//         error: function error(xhr) {
//             alert("error!")
//         }
//     });
// });