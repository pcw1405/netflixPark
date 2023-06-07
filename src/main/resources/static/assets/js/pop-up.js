
$(document).ready(function () {

   //var $searchResultItems = $(".searchresult img, .searchresult video");

   $(".searchresult ,.searchlist").click(function popupVideoHandler(e)  {
       e.stopPropagation();
       var $clickedElement = $(this);
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
            width: '495px',
            height: '400px'
        });

        $video.prop("autoplay", true);
        $videoSpace.empty().append($video);
        $videoSpace.css({
            width: "500px",
            height: "400px"
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
            $textContainer.empty().append($textSpace);
        }

        $textSpace.empty().append("<hr>");

        var $descriptionText = $("<div>");

//videoNm

 var videoNm = $clickedElement.find('[data-video-nm]').attr('data-video-nm');
  var videoGenres = $clickedElement.find('[data-video-genres]').attr('data-video-genres');
  var videoDescription = $clickedElement.find('[data-video-description]').attr('data-video-description');
  var videoActors = $clickedElement.find('[data-video-actors]').attr('data-video-actors');
  var videoCast = $clickedElement.find('[data-video-cast]').attr('data-video-cast');

        // var $descriptionText = $("<div>").text(videoDescription);

        $descriptionText.empty().append(
          $("<div>").text("제목: " + videoNm),
          $("<div>").text("장르: " + videoGenres),
          $("<div>").text("설명: " + videoDescription),
          $("<div>").text("배우: " + videoActors),
          $("<div>").text("감독: " + videoCast)
        );

        $textSpace.empty().append($descriptionText);

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

 });
