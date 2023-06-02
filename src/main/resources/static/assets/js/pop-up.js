
$(document).ready(function () {

    $(".video-description").click(function(event) {
        event.stopPropagation();

        $("video").each(function() {
            this.pause();
        });

        var $description = $(this);
        var $parent = $description.parent();
        var descriptionText = $(this).text();

        var $popup = $(".popup");

        // 비디오 컨테이너 추가
        var $videoContainer = $(".videoContainer");
        var $videoSpace = $videoContainer.find(".videoSpace");
        if ($videoSpace.length === 0) {
            $videoSpace = $("<div>", {
                class: "videoSpace"
            });
            $videoContainer.append($videoSpace);
        }

        // 비디오 추가
        var $video = $parent.find("video").clone();
        $video.prop("autoplay", true);
        $videoSpace.empty().append($video);
        $videoSpace.css({
          width: "100%",
          height: "100%"
        });

        // 텍스트 컨테이너 추가
        var $textContainer = $(".textContainer");
        var $textSpace = $textContainer.find(".textSpace");
        if ($textSpace.length === 0) {
            $textSpace = $("<div>", {
                class: "textSpace"
            });
            $textContainer.append($textSpace);
        }

        // 구분선 추가
        $textSpace.empty().append("<hr>");

        // 텍스트 추가
        var $descriptionText = $("<div>");
        var paragraphs = descriptionText.split("\n");
        var mergedParagraphs = [];

        paragraphs.forEach(function(paragraph) {
            if (paragraph.includes("#")) {
                // "#"이 포함된 경우, 통합된 텍스트로 처리
                var trimmedParagraph = paragraph.trim();
                if (mergedParagraphs.length > 0) {
                    mergedParagraphs[mergedParagraphs.length - 1] += " " + trimmedParagraph;
                } else {
                    mergedParagraphs.push(trimmedParagraph);
                }
            } else {
                // 일반적인 경우, 개별 텍스트로 처리
                mergedParagraphs.push(paragraph);
            }
        });

        mergedParagraphs.forEach(function(paragraph) {
            $descriptionText.append($("<div>").text(paragraph));
        });

        $textSpace.append($descriptionText);

        $(".popup").css("display", "block");
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
