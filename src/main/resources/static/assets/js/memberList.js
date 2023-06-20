$(document).ready(function() {
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
  // Handle "수정" button click event
  $(document).on("click", "#editButton", function () {
    var row = $(this).closest("tr");
    var memberId = row.find("[name='memberId']").val();
    var memberRole = row.find(".member-role-select").val();
    var membershipRole = row.find(".membership-role-select").val();

    var data = {
      id: memberId,
      role: memberRole,
      membershipRole: membershipRole,
    };
    console.log(memberId);
    console.log(memberRole);
    console.log(membershipRole);
    console.log(typeof(memberRole));
    console.log(typeof(memberId));
    console.log(typeof(membershipRole));

    $.ajax({
      type: "POST",
      url: "/members/memberEdit",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(header, token);
      },
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function (response) {
        alert(response.message);
        location.hre = '/members/memberList';
      },
      error: function (error) {
        alert(error)
      },
    });
  });

});