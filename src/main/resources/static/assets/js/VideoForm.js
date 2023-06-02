function validateForm() {
    var form = document.querySelector('form');
    var videoNm = form.querySelector('input[name="videoNm"]');
    var cast = form.querySelector('input[name="cast"]');
    var actors = form.querySelector('input[name="actors"]');
    var description = form.querySelector('input[name="description"]');
    var form = document.querySelector('form');
    var videoRole = form.querySelector('input[name="videoRole"]:checked');
    var videoMaturityLevel = form.querySelector('input[name="videoMaturityLevel"]:checked');
    var genres = form.querySelector('input[name="genres"]:checked');
    var imageFile = form.querySelector('input[name="videoImgFile"]');
    var videoFile = form.querySelector('input[name="videoFile"]');

    if (videoNm.value.trim() === '') {
      alert('영상명을 입력해주세요.');
      return false;
    }

    if (cast.value.trim() === '') {
      alert('감독명을 입력해주세요.');
      return false;
    }

    if (actors.value.trim() === '') {
      alert('배우명을 입력해주세요.');
      return false;
    }

    if (description.value.trim() === '') {
      alert('줄거리를 입력해주세요.');
      return false;
    }
    if (!videoRole) {
      alert('영상 역할을 선택해주세요.');
      return false;
    }

    if (!videoMaturityLevel) {
      alert('시청 등급을 선택해주세요.');
      return false;
    }

    if (!genres) {
      alert('장르를 선택해주세요.');
      return false;
    }
    if (imageFile.files.length === 0) {
          alert('이미지 파일을 선택해주세요.');
          return false;
        } else {
          var imageExtension = imageFile.files[0].name.split('.').pop().toLowerCase();
          if (!['jpg', 'jpeg', 'png', 'gif'].includes(imageExtension)) {
            alert('이미지 파일은 JPG, JPEG, PNG, GIF 형식만 허용됩니다.');
            return false;
          }
        }

        if (videoFile.files.length === 0) {
          alert('동영상 파일을 선택해주세요.');
          return false;
        } else {
          var videoExtension = videoFile.files[0].name.split('.').pop().toLowerCase();
          if (!['mp4', 'avi', 'mov'].includes(videoExtension)) {
            alert('동영상 파일은 MP4, AVI, MOV 형식만 허용됩니다.');
            return false;
          }
        }
    return true;
}
