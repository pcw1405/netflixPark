$(document).ready(function() {
  // 저장 버튼 클릭 시 이벤트 핸들러
  $('#submitButton').click(function() {
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
        console.log(response);
      },
      error: function(response) {
        console.log(response);

      }
    });
  });
});