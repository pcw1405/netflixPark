package com.netf.netflix.Controller;

import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final ProfileService profileService;

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
}
