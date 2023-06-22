    var $originalElement=null
    var membershipValue;
$(document).ready(function () {
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
$.ajax({
  url: '/check-membership', // 컨트롤러의 URL
  type: 'GET', // 요청 방식 (GET, POST, 등)
  beforeSend: function(xhr) {
     xhr.setRequestHeader(header, token);
  },
  data: { // 필요한 데이터 (선택적)
    membership : 'value1'
  },
  success: function(response) {
    console.log(response.membership);
    console.log(typeof(response.membership));
    membershipValue = response.membership;

    if(membershipValue != ""){
    $(".searchresult ,.searchlist").click(function popupVideoHandler(e)  {
       e.stopPropagation();
       var $clickedElement = $(this);
        $originalElement= $(this);

//데이터 속성인 data-video-url 를 비롯해 여러가지 정보를
//자바스크립의 변수에 저장합니다. 이렇게 가져온 데이터는 팝업 내에서 해당 데이터를 표시하거나 처리하는 데 사용됩니다.

        var $description = $(this);
        // var $parent = $description.parent();

        var $popup = $(".popup");

        var $videoContainer = $(".videoContainer");
        var $videoSpace = $videoContainer.find(".videoSpace");
        if ($videoSpace.length === 0) {
            $videoSpace = $("<div>", {
                class: "videoSpace"
            });
            $videoContainer.append($videoSpace);
        }

        var videoUrl = $(this).find('[data-video-url]').attr('data-video-url');

        var $video = $('<video>', {
            src: videoUrl,
           controls: false, // 컨트롤러 비활성화
             autoplay: true, // 자동 재생 활성화
//             muted: false, // 음소거 비활성화 (선택적)
            width: '100%',
            height: '100%',
            class:"popupVideo"
        });

        $video.prop("autoplay", true);
        $videoSpace.empty().append($video);
        $videoSpace.css({
            width: "100%",
            height: "100%"
        });

        var $textContainer = $(".textContainer");
        var $textSpace = $textContainer.find(".textSpace");
        if ($textSpace.length === 0) {
            $textSpace = $("<div>", {
                class: "textSpace",
                css: {
                height: "250px"
                }
            });

        }


// 데이터 속성인 data-video-url외에도 html에서 동적으로 생성된 여러가지
//    자바스크립의 변수에 저장해서 팝업에 출력해준다

  var videoNm = $clickedElement.find('[data-video-nm]').attr('data-video-nm');
  var videoGenres = $clickedElement.find('[data-video-genres]').attr('data-video-genres');
  var videoDescription = $clickedElement.find('[data-video-description]').attr('data-video-description');
  var videoActors = $clickedElement.find('[data-video-actors]').attr('data-video-actors');
  var videoCast = $clickedElement.find('[data-video-cast]').attr('data-video-cast');
  var videoRecent = $clickedElement.find('[data-video-recent]').attr('data-video-recent');
  var videoLevel = $clickedElement.find('[data-video-level]').attr('data-video-level');
   console.log(videoRecent);
   $('.popup').find('.saveLike i').attr('data-video-id', videoRecent);
         var $descriptionText = $("<div>").text(videoDescription);
//var videoIColor = $('.video-description .like-button.saveLike i').attr('data-video-iColor');
 var videoIColor = $clickedElement.find('[data-iColor]').attr('data-iColor');
 console.log(videoIColor);

 // 요소를 선택합니다.
var dataCheckValue = $('.popup [data-video-id]').attr('data-video-id');

console.log("dataCheckValue: " + dataCheckValue);

if (videoLevel === "KID") {
  $('.level').css('color', 'green'); // 녹색으로 색상 변경
} else if (videoLevel === "ALL") {
  $('.level').css('color', 'red'); // 빨강으로 색상 변경
} else {
  $('.level').css('color', ''); // 등급이 없는 경우 기본 색상으로 되돌림
}



 $('.popup').find('.saveLike i').attr('data-iColor', videoIColor);

//span태그들로 감싸서 팝업에 출력해준다
           $('.subject1').empty().text(videoNm);
             $('.story').empty().text(videoDescription);
                $('.titleV').empty().append( $("<span>").text( videoNm));


          $('.ganre').empty().append( $("<span>").text( videoGenres));
            $('.actor').empty().append( $("<span>").text( videoActors));
             $('.videoCast').empty().append( $("<span>").text( videoCast));
              $('.level').empty().append( $("<span>").text( videoLevel));



              $('.saveLike i').each(function() {
                    var iColor = $(this).attr('data-iColor');
                    $(this).css('color', iColor);
                });

         var imageElements = $('.searchlist img');

         // 각 이미지 요소에 대해서 처리합니다.
      imageElements.each(function() {
          var rdVideoId = $(this).attr('data-video-id');
          console.log("VideoId: " + rdVideoId);

          // 이미지 요소의 부모 요소 중에 `.popup` 클래스를 갖는지 확인합니다.
          if ($(this).parents('.popup').length > 0) {

              // rdVideoId와 dataCheckValue를 비교하여 조건을 처리합니다.
              if (rdVideoId === dataCheckValue) {
                  // 값이 동일한 경우 해당하는 searchlist를 숨깁니다.
                  $(this).closest('.searchlist').hide();
                  $('.alwaysShow').show();
                  console.log("popup filter: hide" );
              } else {
                  $(this).closest('.searchlist').show();
                  $('.alwaysShow').show();
                        console.log("popup filter :show " );
              }
          }
      });

  var videoRole = $clickedElement.find('[data-video-Role]').attr('data-video-Role');
    if(membershipValue == "NONE"){
                $popup.css("display", "none");
                alert("맴버쉽 결재 후 이용이 가능합니다.");

            }
        if(membershipValue == "BASIC" && videoLevel != "ALL"){
        $popup.css("display", "block");
        }
        if(membershipValue == "BASIC" && videoLevel != "KID"){
            $popup.css("display", "none");
            if(videoRole == "MOVIE" ){
                alert("PREMIUM등급 이상부터 상위 맴버쉽을 결재해주세요");
            }
            if(videoRole == "DRAMA"){
                alert("STANDARD등급 이상부터 상위 맴버쉽을 결재해주세요");
            }
        }
        if(membershipValue == "STANDARD"){
        $popup.css("display", "block");
            if(videoRole == "MOVIE" && videoLevel != "KID"){
                $popup.css("display", "none");
                alert("PREMIUM등급 이상부터 상위 맴버쉽을 결재해주세요");
            }
        }
        if(membershipValue == "PREMIUM"){
            $popup.css("display", "block");
        }

    });

// 팝업을 끄는 기능
    $(document).on("click", function(event) {
      var $target = $(event.target);

      // 팝업이 열려있을 때
      if ($target.closest(".popup").length === 0) {

        // 팝업 외부를 클릭한 경우, 팝업을 dispaly none으로 만들어준다
         $(".popup").css("display","none");
         $(".videoSpace").remove();
          $(".textSpace").remove();
      }
    });

    $(document).ready(function() {
      $(".popup-close").click(function() {
        $(".popup").css("display", "none");
        $(".videoSpace").remove();
        $(".textSpace").remove();
      });
    });

      $('.saveLike i').each(function() {
            var iColor = $(this).attr('data-iColor');
            $(this).css('color', iColor);
        });



}
  }
});
});

