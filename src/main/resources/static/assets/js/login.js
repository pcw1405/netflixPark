document.addEventListener("DOMContentLoaded", function(){
  var errorEmailElement = document.getElementById('errorEmail');
  var errorPasswordElement = document.getElementById('errorPassword');
  var emailInputElement = document.getElementById('email');
  var signInButtonElement = document.getElementById('signInButton');
  var rememberMeCheckbox = document.getElementById('rememberMe');

  // 이메일 유효성 검사
  function validateEmail() {
    var email = emailInputElement.value;
    var re = /\S+@\S+\.\S+/;
    var result = re.test(email);
    if (result) {
      errorEmailElement.style.display = "none";
      signInButtonElement.disabled = false;
    } else {
      errorEmailElement.style.display = "block";
      signInButtonElement.disabled = true;
    }
  }

  //로컬스토리지내에 id값을 저장해놓는 코드, 로그인정보 저장 체크시에 작동
  //로그인정보저장체크를 해제하고 로그인하게되면 로컬스토리지내 키벨류 삭제
  var rememberMe = localStorage.getItem("rememberMe");
  var localStorageId = document.getElementById('email');

  window.addEventListener("load", function() {
    if (rememberMe === "true") {
      rememberMeCheckbox.checked = true;
      localStorageId.value = localStorage.getItem("localStorageId");
    }
  });

  signInButtonElement.addEventListener("click", function() {
    if (rememberMeCheckbox.checked) {
      localStorage.setItem("rememberMe", "true");
      localStorage.setItem("localStorageId", localStorageId.value);
    } else {
      localStorage.clear();
    }
  });
     function onClick(e) {
    e.preventDefault();
    grecaptcha.enterprise.ready(async () => {
      const token = await grecaptcha.enterprise.execute('6LfIWqYmAAAAAKzvx3iz1jngQGm3wmT9qujzQ-yr', {action: 'LOGIN'});
      // IMPORTANT: The 'token' that results from execute is an encrypted response sent by
      // reCAPTCHA Enterprise to the end user's browser.
      // This token must be validated by creating an assessment.
      // See https://cloud.google.com/recaptcha-enterprise/docs/create-assessment
    });
  }

  $('#signInButton').click(function(event) {
     event.stopPropagation(); //기본내장된기능을 취소한다.
     var $clickElement = $(this);
     var memberinfo = $("#loginForm").serializeArray();
     var header = $("meta[name='_csrf_header']").attr('content');
      var token = $("meta[name='_csrf']").attr('content');
        var obj;
        if(memberinfo){
          obj = {};
          jQuery.each(memberinfo, function() {
          obj[this.name] = this.value;
          });
          };
     $.ajax({
         url: "/members/login",
         type: "POST",
         beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
             },
         contentType: 'application/json; charset=utf-8',
         data: JSON.stringify( obj ),
         success: function(response) {
                 console.log(response);
                 if(response.code === 200){
                 location.href = '/profile/profile';
                 }
         },
         error: function(response) {
             console.log(response);
             alert(response.responseJSON.message);
         }
     });
  });

});


