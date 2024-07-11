/*
$("question_id").val(); is using jQuery to select an HTML element with the id question_id and
retrieve its value using the .val() method. In this case, it’s getting the value of the hidden
input field with the id question_id in your HTML code. In the context of your HTML code, the
post() function is called when the “回复” button is clicked. When this happens, the function
retrieves the id of the current question (stored in the hidden input field)
*/

/**
 * 提交回复
 */

function post() {
  var questionId = $("#question_id").val();
  var content = $("#comment_content").val();
  comment2target(questionId, 1, content);
}

function comment2target(targetId, type, content) {
  if (!content) {
    alert("不能回复空内容");
    return;
  }

  $.ajax({
    type: "POST",
    url: "/comment",
    contentType: 'application/json',
    data: JSON.stringify({
      "parentId": targetId,
      "content": content,
      "type": type
    }),
    success: function (response) {
      if (response.code == 200) {
        window.location.reload();
      } else {
        if (response.code == 2003) {
          var isAccepted = confirm(response.message);
          if (isAccepted) {  //If the user clicks OK, the isAccepted variable will be true
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


function comment(e) {
  var commentId = e.getAttribute("data-id");
  /**
   * .val(): This is a jQuery method that gets the current value of the selected input element. If the selected
   * element is an <input>, <select>, or <textarea> element, .val() will return the current value that the
   * user has entered or selected.
   */
  var content = $("#input-" + commentId).val();
  comment2target(commentId, 2, content)
}

/**
 * 展开二级评论
 */

function collapseComments(e) {
  var id = e.getAttribute("data-id");
  // The # symbol in jQuery and in CSS selectors is used to select an element by its ID
  var comments = $("#comment-" + id);

  //获取二级评论的展开状态
  let collapse = e.getAttribute("data-collapse");
  if (collapse) {
    // 折叠二级评论
    comments.removeClass("in");
    e.removeAttribute("data-collapse");
    e.classList.remove("active");  // remove the CSS class "active" from the element e
  } else {
    let subCommentContainer = $("#comment-" + id);

    if (subCommentContainer.children().length != 1) {
      //展开二级评论
      comments.addClass("in");
      //标记二级评论展开状态
      e.setAttribute("data-collapse", "in");
      e.classList.add("active");
    } else {
      //  making a GET request to the server at the URL "/comment/" + id to fetch JSON data.
      //  Once the data is fetched, it executes the callback function with the fetched data as
      //  its argument.
      $.getJSON("/comment/" + id, function (data) {
        // iterate over each item in the data.data array in reverse order. For each item, it executes
        // the provided function, passing in the index of the item and the item itself (referred to as comment)
        $.each(data.data.reverse(), function (index, comment) {
          /*
          jQuery create new HTML elements:
          $("<tag/>", {
            "property1": "value1",
            "property2": "value2",
            // ...
          });

           */
          var mediaLeftElement = $("<div/>", {
            "class": "media-left"
          }).append($("<img/>", {
            "class": "media-object img-rounded",
            "src": comment.user.avatarUrl
          }));

          var mediaBodyElement = $("<div/>", {
            "class": "media-body"
          }).append( $("<h5/>", {
            "class": "media-heading",
            "html": comment.user.name
          }) ).append($("<div/>", {
            "html": comment.content
          })).append($("<div/>", {
            "class": "menu"
          }).append($("<span/>", {
            "class": "pull-right",
            "html": moment(comment.gmtCreate).format('YYYY/MM/DD')
          })));

          var mediaElement = $("<div/>", {
            "class": "media"
          }).append(mediaLeftElement).append(mediaBodyElement);

          var commentElement = $("<div/>", {
            "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
          }).append(mediaElement);

          subCommentContainer.prepend(commentElement);
        });

        //展开二级评论
        comments.addClass("in");
        //标记二级评论展开状态
        e.setAttribute("data-collapse", "in");
        e.classList.add("active");
      });
    }
  }
}

function showSelectTag() {
  /*
  .show(): is a jQuery method that changes the CSS display property of the selected element to its default state,
  If this element was previously hidden (either by CSS or jQuery’s .hide() method), it will now be visible on
  the webpage. If the element was already visible, this command will have no noticeable effect.
   */

  $("#select-tag").show();
}


function selectTag(e) {
  var value = e.getAttribute("data-tag");
  var previous = $("#tag").val();
  if (previous.indexOf(value) == -1) {
    // 如果value不存在于previous中才添加
    if(previous) {
      $("#tag").val(previous + ',' + value);
    } else {
      $("#tag").val(value);
    }
  }
}