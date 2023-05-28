document.addEventListener("DOMContentLoaded", function() {
    var likeButton = document.querySelector(".like-button");
    likeButton.addEventListener("click", function likeVideo(videoId) {
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "/like", true);
        xhr.setRequestHeader("Content-Type", "application/json");
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                alert("동영상을 좋아요 했습니다!");
            }
        };
        var data = {
            videoId: videoId
        };
        xhr.send(JSON.stringify(data));
    });
});