$(document).ready(function() {
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
  // Handle "수정" button click event
  $("#editButton").click(function(e) {
    e.preventDefault(); // Prevent the default form submission

    // Get the selected values
    var memberRole = $(".member-role-select").val();
    var membershipRole = $(".membership-role-select").val();
    var memberId = $(".memberId").val();

    var data = {
      memberRole: memberRole,
      membershipRole: membershipRole,
      memberId : memberId
    };

    // Send the JSON data via AJAX
    $.ajax({
      type: "POST",
      url: "/members/memberEdit",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(header, token);
      },
      data: JSON.stringify(data),
      contentType: "application/json",
      success: function(response) {
        alert(response.message)
         location.href = '/members/memberList';
      },
      error: function(response) {
        alert(response.error)
      }
    });
  });
});