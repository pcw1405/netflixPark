    var $originalElement=null
$(document).ready(function () {

   //var $searchResultItems = $(".searchresult img, .searchresult video");


   $(".searchresult ,.searchlist").click(function popupVideoHandler(e)  {
       e.stopPropagation();
       var $clickedElement = $(this);
        $originalElement= $(this);
        // $("video").each(function() {
        //     this.pause();
        // });

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

        // var $video = $parent.find("video").clone();
//        var videoUrl = $(this).attr('data-video-url');
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
//            $textContainer.empty().append($textSpace);
        }

//        $textSpace.empty().append("<hr>");


//videoNm

 var videoNm = $clickedElement.find('[data-video-nm]').attr('data-video-nm');
  var videoGenres = $clickedElement.find('[data-video-genres]').attr('data-video-genres');
  var videoDescription = $clickedElement.find('[data-video-description]').attr('data-video-description');
  var videoActors = $clickedElement.find('[data-video-actors]').attr('data-video-actors');
  var videoCast = $clickedElement.find('[data-video-cast]').attr('data-video-cast');
  var videoRecent = $clickedElement.find('[data-video-recent]').attr('data-video-recent');
   console.log(videoRecent);
   $('.popup').find('.saveLike i').attr('data-video-id', videoRecent);
         var $descriptionText = $("<div>").text(videoDescription);
//var videoIColor = $('.video-description .like-button.saveLike i').attr('data-video-iColor');
 var videoIColor = $clickedElement.find('[data-iColor]').attr('data-iColor');
 console.log(videoIColor);

 $('.popup').find('.saveLike i').attr('data-iColor', videoIColor);


           $('.subject1').empty().text(videoNm);
             $('.story').empty().text(videoDescription);
                $('.titleV').empty().append( $("<span>").text( videoNm));
//            $('.story').empty().append($descriptionText);

          $('.ganre').empty().append( $("<span>").text( videoCast));
            $('.actor').empty().append( $("<span>").text( videoActors));
             $('.videoCast').empty().append( $("<span>").text( videoCast));

//        $('.story_box').empty().append($descriptionText);

              $('.saveLike i').each(function() {
                    var iColor = $(this).attr('data-iColor');
                    $(this).css('color', iColor);
                });

        $popup.css("display", "block");


    });


    $(document).on("click", function(event) {
      var $target = $(event.target);

      // 팝업이 열려있을 때
      if ($target.closest(".popup").length === 0) {

        // 팝업 외부를 클릭한 경우, 팝업 제거
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

//var header = $("meta[name='_csrf_header']").attr('content');
//var token = $("meta[name='_csrf']").attr('content');
//var url = "/save-like";
//
//    $.ajax({
//        url: url,
//        beforeSend: function(xhr){
//            xhr.setRequestHeader(header, token);
//        },
//        success: function(res) {
//            console.log(res);
//        }
//    });
//
//   $('.saveLike').click(function() {
//    event.stopPropagation(); // Prevent click event propagation
//           var videoId = $(this).data('video-id');
//
//           var data = {
//               videoId: videoId
//           };
//
//           $.ajax({
//               url: "/save-like",
//               type: "POST",
//               beforeSend: function(xhr) {
//                   xhr.setRequestHeader(header, token);
//               },
//               dataType: 'json',
//               contentType: 'application/json; charset=utf-8',
//               data: JSON.stringify(data),
//               success: function(response) {
//                   console.log(response);
//               },
//               error: function(xhr, status, error) {
//                   console.error('Failed to save like:', error);
//               }
//           });
//       });


 });
