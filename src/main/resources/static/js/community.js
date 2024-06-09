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
                alert(response.message);
            }
            console.log(response);
        },
        dataType: "json"
    });
}