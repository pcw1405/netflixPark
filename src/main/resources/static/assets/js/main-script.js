
$(document).ready(function () {

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

