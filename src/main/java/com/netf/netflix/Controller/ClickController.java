package com.netf.netflix.Controller;

import com.netf.netflix.Service.ProfileService;
import com.netf.netflix.Service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ClickController {

    private final ProfileService profileService;
    private final VideoService videoService;

    @PostMapping("/save-like")
    public ResponseEntity<?> saveLike(@RequestBody Map<String, Object> requestData, HttpSession session) {
        String videoIdString = (String) requestData.get("videoId");

        long videoId = Long.parseLong(videoIdString);
        System.out.println("Received videoId: " + videoId); // videoId 출력

//        ajax를 통해 클릭을 통해 비디오 아이디값 정보를 가져온다

        String loggedInUser = (String) session.getAttribute("loggedInUser");

        System.out.println(loggedInUser);
        Long profileId = (Long) session.getAttribute("profileNm");
        //프로파일 아이디를 통해 현재 프로파일을 찾는다
        // 현재의 프로필 (데이터베이스)에 favoriteVIdeos목록에 좋아요 정보를 저장한다
        //(프로파일 엔티티의 FavoriteVideos 필드 = 좋아요한 영상의 아이디값을 담는 hashset  )
        System.out.println(profileId);


//        정보를 저장하는 과정에서 서비스를 이용하고
        //ajax 요청이 성공했을 때는 영상에 대한 좋아요 정보를 저장했다는 의미로 하트를 빨간색으로 하는 자바스크립트를 만들었다
        //ajax요청이 실패했을 때는 좋아요를 눌렀을 때 이미 좋아요가 되어있든 영상이라면 취소라는 의미로 다시 하얀색으로 변경하도록 만들었다
        //자바스크립트를 통해 바로 바뀌게 해준다
        try {
            profileService.addVideoToFavorites(profileId, videoId);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Like saved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/addRecentlyViewedVideo")
    public ResponseEntity<?> addRecentlyViewedVideo(@RequestBody Map<String,
            Object> videoData,
            HttpSession session) {

        String recentVideoIdString = (String) videoData.get("recentVideoId");
        long videoId = Long.parseLong(recentVideoIdString);
        // recentVideoId를 사용하여 비즈니스 로직 수행
        System.out.println("Received videoId: " + videoId); // videoId 출력

        Long profileId = (Long) session.getAttribute("profileNm");
        System.out.println(profileId);

        String loggedInUser = (String) session.getAttribute("loggedInUser");
        System.out.println(loggedInUser);
        // 적절한 응답 생성

        try{
            profileService.addRecentlyViewedVideo(profileId,  videoId);
            videoService.addViewCount(videoId);

            // ResponseEntity를 사용하여 JSON 데이터와 HTTP 상태 코드 반환
            String jsonResponse = "{\"message\": \"Video added to recently viewed videos\"}";
            return new ResponseEntity<>(jsonResponse, HttpStatus.OK);

        }catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

//   반환할 때 제이슨으로 반환해야한다
    }

}
