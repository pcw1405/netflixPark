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
        String loggedInUser = (String) session.getAttribute("loggedInUser");

        System.out.println(loggedInUser);
        Long profileId = (Long) session.getAttribute("profileNm");
        System.out.println(profileId);
        // 처리 결과를 리턴합니다.

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
