package com.netf.netflix.Controller;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.MemberService;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberService memberService;
    private final ProfileRepository profileRepository;

    private final VideoImgRepository videoImgRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/home/{profileId}")
    public String home(@PathVariable("profileId") Long profileId, Model model, Principal principal, HttpSession session) {
        // 로그인된 멤버의 정보 가져오기
        session.setAttribute("profileNm",profileId);
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }

        // 헤더이미지부르는부분
        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        String email = principal.getName();
        Member member = memberService.findMemberByEmail(email);





        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);

        //비디오 부분
        /// 비디오 내용 홈와면의 비디오 내용 필요한것 : 랜덤/ 최근 / 새로 올라운 콘텐츠 / top10
//       프로파일 이미지는 이미 있기 때문에 세션으로 가져올 필요없다
//        Long profileId = (Long) session.getAttribute("profileNm");
//        Profile profile = profileRepository.findById(profileId)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));

//키드만 추출하는 키드필터(키드비디오들) 생성
        List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);
        System.out.println("나의 MaturityLevel: " + (profile != null ? profile.getMaturityLevel() : "unknown"));


        // 랜덤 부분
        List<Video> videos = videoRepository.findAll();
        List<Video> randomVideos = new ArrayList<>();
        Set<Integer> chosenIndexes = new HashSet<>();

        while (randomVideos.size() < 4 && chosenIndexes.size() < videos.size()) {
            int randomIndex = new Random().nextInt(videos.size());
            if (chosenIndexes.contains(randomIndex)) {
                continue; // 이미 선택된 인덱스는 건너뛰기
            }
            chosenIndexes.add(randomIndex);
            Video randomVideo = videos.get(randomIndex);
            randomVideos.add(randomVideo);
        }

        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
            if (randomVideos != null && kidFilter != null) {
                randomVideos.retainAll(kidFilter);
                System.out.println("키드에 대한 영상만 추출 ");
            }
        } else {
            System.out.println("어른입니다");
        }

        model.addAttribute("randomVideo2", randomVideos);
        if (!randomVideos.isEmpty()) {
            model.addAttribute("randomVideo", randomVideos.get(0));
        }





        //최근본
        List<Long> recentViewId =  selectedProfile.getRecentlyViewedVideos();
        List<Video> recentVideos = new ArrayList<>();

        for (Long videoId : recentViewId) {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid video ID: " + videoId));
            recentVideos.add(video);
        }

        model.addAttribute("recentVideos", recentVideos);

        // 찜목록
        model.addAttribute("favoriteVideos", selectedProfile.getFavoriteVideos());

        // 좋아하는 비디오 리스트 가져오기
        Set<Long> favoriteVideosId = selectedProfile.getFavoriteVideos();

        List<Video> like_videos =videoRepository.findAllById(favoriteVideosId);
        // 모델에 좋아하는 비디오 리스트 추가
        model.addAttribute("like_videos",like_videos);

//        업데이트 비디오는 프로파일넘버가 필요없다
//        찜목록이나 최근본 비디오는 애초에 키드가 볼 수 가 없으니 키드필터링할 필요가 없다

        List<Video> uploadVideos = videoRepository.findDistinctByOrderByVideoImgUploadDateDesc();

        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
//            List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);
            if (uploadVideos != null) {
            uploadVideos.retainAll(kidFilter);
//                System.out.println("키드에 대한 영상만 추출 ");
            }
        }else{
            System.out.println("어른입니다");
        }


        if(uploadVideos!=null){
            model.addAttribute("uploadVideos",uploadVideos );
        }else{
            System.out.println("null입니다");
        }


        List<Video> top10Videos = videoRepository.findTop10ByOrderByViewCountDesc();


        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
//            List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);
            if (top10Videos != null) {
            top10Videos.retainAll(kidFilter);
//                System.out.println("키드에 대한 영상만 추출 ");
            }
        }else{
            System.out.println("어른입니다");
        }



        model.addAttribute("top10Videos", top10Videos);

        return "home";
    }



}
