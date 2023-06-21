var $originalElement
$(document).ready(function () {
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

//    좋아요 버튼을 눌렀을 때 저장하는 기능
   $('.saveLike').click(function(event) {
       event.stopPropagation(); // Prevent click event propagation
            var $clickedElement = $(this);
                   $originalElement= $(this);

        //클릭된 요소 내부에서 data-video-id속성을 가진 요소를 찾아 해당 속성의 값을 가져옵니다.( data-video-id는 비디오에 따라 동적인 값을 가지고 있다 )
        var videoId = $clickedElement.find('[data-video-id]').attr('data-video-id');
       var data = {
           videoId: videoId
       };
// POST방식으로 /save-like 이라는 URL로 요청을 보낸다 비디오의 아이디값을 데이터로 보낸다
       $.ajax({
           url: "/save-like",
           type: "POST",
           beforeSend: function(xhr) {
               xhr.setRequestHeader(header, token);
           },
           dataType: 'json',
           contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({ videoId: videoId }), // 문자열 변환
           success: function(response) {
               console.log(response);
//        요청이 성공했을 때는 영상에 대한 좋아요 정보를 저장했다는 의미로 하트를 빨간색으로 한다

if ($clickedElement !== null) {
  $clickedElement.find('i').css('color', 'red');
  $clickedElement.find('i').attr('data-iColor', 'red');

}

// 여기서부터 복잡한 부분이 있는데 그냥 새로고침 필요없이 좋아요 아이콘을 빨간색이나 하얀색으로 변환시키기위한 코드이다
if ($('.popup') !== null) {

if (videoId === $('.popup').find('[data-video-id]').attr('data-video-id')) {
  // 조건이 참인 경우 실행할 코드
    $('.popup').find('.saveLike i').css('color', 'red');
    $('.popup').find('.saveLike i').attr('data-iColor', 'red');
//    팝업에도 대해서 좋아요에 대한 정보를 data-iColor에 저장해준다

}

}

if ($originalElement !== null) {
  $originalElement.find('i').css('color', 'red');
  $originalElement.find('i').attr('data-iColor', 'red');
}


$('[data-video-id]').each(function() {
  var currentVideoId = $(this).attr('data-video-id');

  // data-video-id 값이 videoId와 일치하는 경우 해당 요소의 save-like 버튼을 찾아 색상을 변경한다
  // 즉 같은 비디오인 경우에는 색상을 변경해준다
  if (currentVideoId == videoId) {

var saveLikeButton = $(this).find(' i');
 $(this).attr('data-iColor', 'red');



    var iColor = saveLikeButton.attr('data-iColor');
    var saveLikeButtonColor = saveLikeButton.attr('data-iColor');
    saveLikeButtonColor='red'

 // data-iColor 값이 'red'인 경우 색상을 red로, 그렇지 않은 경우 색상을 white로 변경한다
if (saveLikeButtonColor == 'red') {
      saveLikeButton.css('color', 'red');
    } else {
      saveLikeButton.css('color', 'white');
    }
  }
});



                     $originalElement = $();
           },
           error: function(xhr, status, error) {
               console.error('Failed to save like:', error);
if ($clickedElement !== null) {
  $clickedElement.find('i').css('color', 'white');
  $clickedElement.find('i').attr('data-iColor', 'white');
}

// 실패 error일 때는 반대로 한다

if ($('.popup') !== null) {
if (videoId === $('.popup').find('[data-video-id]').attr('data-video-id')) {
  // 조건이 참인 경우 실행할 코드
    $('.popup').find('.saveLike i').css('color', 'white');
    $('.popup').find('.saveLike i').attr('data-iColor', 'white');

}


}

    if ($originalElement !== null) {
  $originalElement.find('i').css('color', 'white');
  $originalElement.find('i').attr('data-iColor', 'white');
    }



    $('[data-video-id]').each(function() {
         var currentVideoId = $(this).attr('data-video-id');


    // data-video-id 값이 videoId와 일치하는 경우 해당 요소의 save-like 버튼을 찾아 색상을 변경한다
        if (currentVideoId === videoId) {
        //    var saveLikeButton = $(this).siblings('.video-description').find('.saveLike i');
        var saveLikeButton = $(this).find('i');
      $(this).attr('data-iColor', 'white');


    $(this).parents('.owl-item:has(.mylike)').remove();


    // data-iColor 값이 'red'인 경우 색상을 red로, 그렇지 않은 경우 색상을 white로 변경한다
    var iColor = saveLikeButton.attr('data-iColor');

    var saveLikeButtonColor = saveLikeButton.attr('data-iColor');
    saveLikeButtonColor='white'
    if (saveLikeButtonColor == 'red') {
      saveLikeButton.css('color', 'red');
    } else {
      saveLikeButton.css('color', 'white');
    }

    }
    });



            $originalElement = $();
           }
       });
   });

   // 최근 시청한 비디오
   // POST방식으로 //addRecentlyViewedVideo 이라는 URL로 요청을 보낸다 비디오의 아이디값을 데이터로 보낸다
   // 컨트롤러를 통해 이 아이디값으로 비디오를 찾아서 현재 프로필에서 최근 시청한 비디오 목록을 저장한다
   $('.getRecent').click(function(event) {
       event.stopPropagation(); // Prevent click event propagation
       var $clickElement = $(this);

       var recentVideoId = $clickElement.find('[data-video-recent]').attr('data-video-recent');
       console.log(recentVideoId);
       var data = {
           recentVideoId: recentVideoId
       };

       $.ajax({
           url: "/addRecentlyViewedVideo",
           type: "POST",
           beforeSend: function(xhr) {
               xhr.setRequestHeader(header, token);
           },
           dataType: 'json',
           contentType: 'application/json; charset=utf-8',
           data: JSON.stringify({ recentVideoId: recentVideoId }),
           success: function(response) {
               console.log(response);
           },
           error: function(xhr, status, error) {
               console.error('Failed to addVideoToFavorites:', error);
           }
       });
   });

//  각각의 아이콘을 찾고 그 아이콘에 해당하는 data-iColor를 찾아서 그 값에 저장된 색깔로 아이콘의 색깔을 css를 변경해준다
// html과 호환을 위해서 작성한 코드
    $('.saveLike i ,.findI i').each(function() {
        var iColor = $(this).attr('data-iColor');
        $(this).css('color', iColor);
    });


    //autoplay hero section video 

    setTimeout(function () {
        document.getElementById("hero-video").play();
    }, 5000);

    //onscroll change header color 
    $(window).on("scroll", function() {
        if($(window).scrollTop() > 100) {
            $("header").css("background-color", "black");
            document.getElementById("hero-video").pause();
        } else if($(window).scrollTop() < 10) {
            //remove the background property so it comes transparent again (defined in your css)
            $("header").css("background-color", "transparent");
            document.getElementById("hero-video").play();
        }
    });

    //owl carousel settings

    $(".owl-carousel").owlCarousel({
        loop: false,
        margin: 10,
        nav: true,
        responsive: {
            0: {
                items: 2
            },
            600: {
                items: 2
            },
            1000: {
                items: 5
            }
        }
    });

    //autoplay video on hover over an video item
    var figure = $(".video").hover(hoverVideo, hideVideo);

    function hoverVideo(e) {
        $('video', this).get(0).play();
    }

    function hideVideo(e) {
        $('video', this).get(0).pause();
        $('video', this).get(0).load();
    } 


    $(".like-button").click(function(event) {
      event.stopPropagation();
    });

// 동영상을 전체화면으로 만들어주는 코드 팝업이 있다는 것도 보여주기 위해서 1초 정도 느리게 작동된다
    $(".full").click(function() {
      setTimeout(function() {
          var $video = $('.popup .videoContainer video');

          if ($video.length > 0) {
            var videoElement = $video.get(0);

            if (videoElement.requestFullscreen) {
              videoElement.requestFullscreen();
            } else if (videoElement.mozRequestFullScreen) {
              videoElement.mozRequestFullScreen();
            } else if (videoElement.webkitRequestFullscreen) {
              videoElement.webkitRequestFullscreen();
            } else if (videoElement.msRequestFullscreen) {
              videoElement.msRequestFullscreen();
            }
          }
        }, 1000);
    });
// 동영상을 전체화면으로 만들어주는 이미 팝업이 켜져있기 때문에 바로 전체화면으로 전환시켜준다
        $(".fullSpeed").click(function() {
          setTimeout(function() {
              var $video = $('.popup .videoContainer video');

              if ($video.length > 0) {
                var videoElement = $video.get(0);

                if (videoElement.requestFullscreen) {
                  videoElement.requestFullscreen();
                } else if (videoElement.mozRequestFullScreen) {
                  videoElement.mozRequestFullScreen();
                } else if (videoElement.webkitRequestFullscreen) {
                  videoElement.webkitRequestFullscreen();
                } else if (videoElement.msRequestFullscreen) {
                  videoElement.msRequestFullscreen();
                }
              }
            }, 50);
        });


});

