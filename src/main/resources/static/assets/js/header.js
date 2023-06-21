$(document).ready(function(){
    var header = $("meta[name='_csrf_header']").attr('content');
    var token = $("meta[name='_csrf']").attr('content');
    var headerHomeLink = $("#header-home");
    var headerMovieLink = $("#header-movie");
    var headerDramaLink = $("#header-drama");
    var headerKidsLink = $("#header-kids");

$.ajax({
         url: "/home/header-menu",
         type: "GET",
         beforeSend: function(xhr) {
                 xhr.setRequestHeader(header, token);
             },
         contentType: 'application/json; charset=utf-8',
         data: {
            membershipRole : "value1",
            memberRole : "value2",
            maturityLevel : "value3"
         },
         success: function(response) {
            var memberRole = response.memberRole; // USER, ADMIN
            var membershipRole = response.membershipRole;
            var maturityLevel = response.maturityLevel; // ALL, KID
            console.log(maturityLevel);
            if(maturityLevel == "KID"){
                headerMovieLink.css("display", "none");
                headerDramaLink.css("display", "none");
            }

         }
     });
});