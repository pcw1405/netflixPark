$(document).ready(function(){
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
    var dateText = $("#XXdate");
    $.ajax({
      type: "GET",
      url: "/user/membershipCheck",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(header, token);
      },
      data:{
        membershipdate : 'value'
      },
      success: function (response) {
        dateText.text(response.membershipdate);
      },
    });
});