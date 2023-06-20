$(document).ready(function() {

  // 이메일 유효성 검사
  $("#loginForm").submit(function(event) {
    if (!checkEmailFormat()) {
      event.preventDefault(); // 이메일 형식이 유효하지 않으면 폼 제출을 막음
    }
  });

  function checkEmailFormat() {
    var emailInput = $("#email").val();
    var emailRegex = /^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$/;

    if (!emailRegex.test(emailInput)) {
      $("#errorEmail").show();
      return false;
    } else {
      $("#errorEmail").hide();
      return true;
    }
  }

  // 비밀번호 일치 검사
  $("#password").keyup(checkPasswordMatch);
  $("#passwordcheck").keyup(checkPasswordMatch);

  function checkPasswordMatch() {
    var password = $("#password").val();
    var passwordCheck = $("#passwordcheck").val();
    var phoneNumber = $("#phoneNumber").val();
    var errorName = document.getElementById("errorName");
    var errorEmail = document.getElementById("errorEmail");
    var passwordMatchLabel = document.getElementById("passwordMatchLabel");
    var phoneNumberError = document.getElementById("phoneNumberError");

    if (password === "" && passwordCheck === "") {
      $("#passwordMatchLabel").text("비밀번호를 입력해주세요.").css("color", "red");
      $("#signInButton").prop("disabled", true);
      return false;
    } else if (password !== passwordCheck) {
      $("#passwordMatchLabel").text("비밀번호가 일치하지 않습니다.").css("color", "red");
      $("#signInButton").prop("disabled", true);
      return false;
    } else {
      $("#passwordMatchLabel").text("비밀번호가 일치합니다.").css("color", "green");
      $("#signInButton").prop("disabled", false);
    }

    // 이름 필드가 비어있는지 확인
    if ($("#name").val().trim() === "") {
      errorName.style.display = "block";
      return false;
    } else {
      errorName.style.display = "none";
    }

    // 이메일 필드가 비어있는지 확인
    if ($("#email").val().trim() === "") {
      errorEmail.style.display = "block";
      return false;
    } else {
      errorEmail.style.display = "none";
    }

    // 비밀번호 유효성 검사
    if (password.length < 8 || password.length > 16) {
      passwordMatchLabel.innerText = "비밀번호는 8자 이상 16자 이하여야 합니다.";
      passwordMatchLabel.style.color = "red";
      return false;
    }

    // 핸드폰 번호 유효성 검사
    if (phoneNumber.length < 10 || phoneNumber.length > 11) {
      phoneNumberError.innerText = "핸드폰 번호는 10자리 이상 11자리 이하여야 합니다.";
      phoneNumberError.style.color = "red";
      return false;
    } else {
      phoneNumberError.innerText = "";
    }

    return true;
  }

  // 회원가입 버튼 클릭 시 AJAX 요청
  $('.submitButton').click(function(event) {
    event.stopPropagation(); // 기본 내장된 기능을 취소한다.
    var $clickElement = $(this);
    var memberinfo = $("#loginForm").serializeArray();
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
    var obj;
    if (memberinfo) {
      obj = {};
      jQuery.each(memberinfo, function() {
        obj[this.name] = this.value;
      });
    }
    $.ajax({
      url: "/members/new",
      type: "POST",
      beforeSend: function(xhr) {
        xhr.setRequestHeader(header, token);
      },
      contentType: 'application/json; charset=utf-8',
      data: JSON.stringify(obj),
      success: function(response) {
        console.log(response);
        if (response.code === 200) {
          alert(response.message);
          location.href = '/members/login';
        }
      },
      error: function(response) {
        console.log(response);
        alert(response.responseJSON.message);
      }
    });
  });

});