/*
$("question_id").val(); is using jQuery to select an HTML element with the id question_id and
retrieve its value using the .val() method. In this case, it’s getting the value of the hidden
input field with the id question_id in your HTML code. In the context of your HTML code, the
post() function is called when the “回复” button is clicked. When this happens, the function
retrieves the id of the current question (stored in the hidden input field)
*/
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    $.ajax({
        type: "POST",
        url: "/comment",
        contentType: 'application/json',
        data: JSON.stringify({
            "parentId": questionId,
            "content": content,
            "type": 1
        }),
        success: function (response) {
            if(response.code == 200) {
                $("#comment_section").hide();
            } else {
                if (response.code== 2003) {
                    var isAccepted = confirm(response.message);
                    if( isAccepted){  //If the user clicks OK, the isAccepted variable will be true
                        /*
                        window is a global object in JavaScript. It represents the browser’s window in which the
                        JavaScript is currently executing. All global JavaScript objects, functions, and variables
                        automatically become members of the window object.
                         */
                        window.open("https://github.com/login/oauth/authorize?client_id=39bebcd732c20993eed0&redirect_uri=http://localhost:8887/callback&scope=user&state=1")
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message);
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}