
$(document).ready(function () {
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');

   $('.saveLike').click(function(event) {
       event.stopPropagation(); // Prevent click event propagation
            var $clickedElement = $(this);
            $clickedElement.find('i').css('color', 'red');

        var videoId = $clickedElement.find('[data-video-id]').attr('data-video-id');
       var data = {
           videoId: videoId
       };

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
                  $clickedElement.find('i').css('color', 'red');

           },
           error: function(xhr, status, error) {
               console.error('Failed to save like:', error);
              $clickedElement.find('i').css('color', 'white');

           }
       });
   });

   // 최근 시청한 비디오
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


    $('.saveLike i').each(function() {
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


});

