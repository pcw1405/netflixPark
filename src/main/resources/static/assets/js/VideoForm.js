$(document).ready(function() {
  // 저장 버튼 클릭 시 이벤트 핸들러
  $('#submitButton').click(function() {
    // 영상명 필수 입력 확인
    var videoNm = $('input[name="videoNm"]').val();
    if (videoNm.trim() === '') {
      alert('영상명을 입력해주세요.');
      return;
    }

    // 감독 필수 입력 확인
    var cast = $('input[name="cast"]').val();
    if (cast.trim() === '') {
      alert('감독을 입력해주세요.');
      return;
    }

    // 배우 필수 입력 확인
    var actors = $('input[name="actors"]').val();
    if (actors.trim() === '') {
      alert('배우를 입력해주세요.');
      return;
    }

    // 줄거리 필수 입력 확인
    var description = $('input[name="description"]').val();
    if (description.trim() === '') {
      alert('줄거리를 입력해주세요.');
      return;
    }

    // 장르 필수 입력 확인
    var genres = $('input[name="genres"]:checked').val();
    if (!genres) {
      alert('장르를 선택해주세요.');
      return;
    }

    // 비디오 역할 필수 입력 확인
    var videoRole = $('input[name="videoRole"]:checked').val();
    if (!videoRole) {
      alert('영상의 종류를 movie와 drama중에서 골라주세요.');
      return;
    }

    // 비디오 연령 제한 필수 입력 확인
    var videoMaturityLevel = $('input[name="videoMaturityLevel"]:checked').val();
    if (!videoMaturityLevel) {
      alert('비디오 연령 제한을 선택해주세요.');
      return;
    }
    // 이미지 파일 필수 입력 확인
    var videoImgFile = $('input[name="videoImgFile"]').get(0).files[0];
    if (!videoImgFile) {
      alert('이미지 파일을 선택해주세요.');
      return;
    }

    // 이미지 파일 형식 확인
    var videoImgExtension = videoImgFile.name.split('.').pop().toLowerCase();
    if (!isValidImageExtension(videoImgExtension)) {
      alert('이미지 파일 형식이 올바르지 않습니다.');
      return;
    }

    // 비디오 파일 필수 입력 확인
    var videoFile = $('input[name="videoFile"]').get(0).files[0];
    if (!videoFile) {
      alert('비디오 파일을 선택해주세요.');
      return;
    }

    // 비디오 파일 형식 확인
    var videoExtension = videoFile.name.split('.').pop().toLowerCase();
    if (!isValidVideoExtension(videoExtension)) {
      alert('비디오 파일 형식이 올바르지 않습니다.');
      return;
    }




    // FormData 객체 생성
    var formData = new FormData($('.uploadContainer')[0]);

    // JSON 데이터 추가
    var json = {
        videoNm: $('input[name="videoNm"]').val(),
        cast: $('input[name="cast"]').val(),
        actors: $('input[name="actors"]').val(),
        description: $('input[name="description"]').val(),
        genres : $('input[name="genres"]:checked').val(),
        videoRole : $('input[name="videoRole"]:checked').val(),
        videoMaturityLevel : $('input[name="videoMaturityLevel"]:checked').val()
    };
    console.log(formData);
    console.log(json);

    formData.append('json', JSON.stringify(json));
    console.log(formData);
    // AJAX 요청 보내기
    $.ajax({
      url: '/video/new',
      type: 'POST',
      processData: false,
      contentType: false,
      data: formData,
      success: function(response) {
        // 성공 시 동작
        alert(response.message);
        location.href = '/video/new';
      },
      error: function(response) {
        console.log(response);
        alert(response.message);
      }
    });
  });

  function isValidImageExtension(extension) {
    var validExtensions = ['jpg', 'jpeg', 'png', 'gif'];
    return validExtensions.includes(extension);
  }

  function isValidVideoExtension(extension) {
    var validExtensions = ['mp4', 'mov', 'avi', 'mkv'];
    return validExtensions.includes(extension);
  }

});