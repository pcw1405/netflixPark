package com.netf.netflix.Controller;

import com.netf.netflix.Constant.MembershipRole;
import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.MemberService;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

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
    private final MemberRepository memberRepository;

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

        List<VideoImg> kidImgFilter = videoImgRepository.findByVideoVideoMaturityLevel(VideoMaturityLevel.KID);

        //알림에 키드일 때 키드 이미지만 뜰 수 있도록 필터링 해준다 
        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
            if ( videoImgs!= null && kidImgFilter != null) {
                videoImgs.retainAll(kidImgFilter);
                System.out.println("키드에 대한 이미지만 추출 ");
            }
        } else {
            System.out.println("어른입니다");
        }



        model.addAttribute("videoImgs",videoImgs);



        //비디오 부분
        /// 비디오 내용 홈와면의 비디오 내용 필요한것 : 랜덤/ 최근 / 새로 올라운 콘텐츠 / top10
//       프로파일 이미지는 이미 있기 때문에 세션으로 가져올 필요없다

//키드만 추출하기위해 키드필터(키드비디오들) 생성
        List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);

        System.out.println("나의 MaturityLevel: " + (profile != null ? profile.getMaturityLevel() : "unknown"));



        // 랜덤 부분
        List<Video> videos = videoRepository.findAll();
        List<Video> randomVideos = new ArrayList<>();
        Set<Integer> chosenIndexes = new HashSet<>();

        while (randomVideos.size() < 4 && chosenIndexes.size() < videos.size()) {
            int randomIndex = new Random().nextInt(videos.size());
            if (chosenIndexes.contains(randomIndex)) {
                continue; // 이미 선택된 인덱스는 건너뛰기 이 while문은 중복을 없애기 위한 코드
            }
            chosenIndexes.add(randomIndex);
            Video randomVideo = videos.get(randomIndex);
            randomVideos.add(randomVideo);
        }

        //랜덤 비디오들에 대해서도 키드일 때는 키드에 해당하는 비디오들만 추출한다 (교집합 )
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





        //최근본 비디오들을 불러온다
        List<Long> recentViewId =  selectedProfile.getRecentlyViewedVideos();
        List<Video> recentVideos = new ArrayList<>();

        for (Long videoId : recentViewId) {
            Optional<Video> optionalVideo = videoRepository.findById(videoId);
            if (optionalVideo.isPresent()) {
                Video video = optionalVideo.get();
                recentVideos.add(video);
            } else {
                // 비디오를 찾지 못한 경우에 대한 처리를 여기에 추가하면 됩니다.(println)
                System.out.println("비디오를 찾을 수 없습니다. videoId: " + videoId);
            }
        }

        model.addAttribute("recentVideos", recentVideos);

        // 찜목록
        model.addAttribute("favoriteVideos", selectedProfile.getFavoriteVideos());

        // 좋아하는 비디오 리스트 가져오기( 비디오들의 아이디 )
        Set<Long> favoriteVideosId = selectedProfile.getFavoriteVideos();

        List<Video> like_videos =videoRepository.findAllById(favoriteVideosId);
        // 모델에 좋아하는 비디오 리스트 추가
        model.addAttribute("like_videos",like_videos);

//        업데이트 비디오는 프로파일넘버가 필요없다
//        찜목록이나 최근본 비디오는 애초에 키드가 볼 수 가 없으니 키드필터링할 필요가 없다

        // 최근 업데이트 된 비디오들을 가져온다
        List<Video> uploadVideos = videoRepository.findDistinctByOrderByVideoImgUploadDateDesc();

        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
            if (uploadVideos != null) {
            uploadVideos.retainAll(kidFilter);
//  키드에 대한 영상만 추출
            }
        }else{
            System.out.println("어른입니다");
        }


        if(uploadVideos!=null){
            model.addAttribute("uploadVideos",uploadVideos );
        }else{
            System.out.println("null입니다");
        }

//    조회수가 높은 top10 비디오들을 가져온다
        List<Video> top10Videos = videoRepository.findTop10ByViewCountGreaterThanOrderByViewCountDesc(0);

        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 ");
            if (top10Videos != null) {
            top10Videos.retainAll(kidFilter);
// 키드에 대한 영상만 추출
            }
        }else{
            System.out.println("어른입니다");
        }



        model.addAttribute("top10Videos", top10Videos);

        return "home";
    }

    @GetMapping("/logout")
    public  String logout(){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            SecurityContextHolder.getContext().setAuthentication(null);
        }

        return "logout";
    }


    @GetMapping("/check-membership")
    public ResponseEntity<?> checkMembership(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        MembershipRole membership = member.getMembershipRole();

        if(membership == MembershipRole.NONE){
            response.put("membership", "NONE");
        }else if(membership == MembershipRole.BASIC){
            response.put("membership", "BASIC");
        }else if(membership == MembershipRole.STANDARD){
            response.put("membership", "STANDARD");
        }else if(membership == MembershipRole.PREMIUM){
            response.put("membership","PREMIUM");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping(value="/home/header-menu")
    public ResponseEntity<?> headerControl(HttpSession session){
        Map<String, Object> response = new HashMap<>();
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Long loggedInProfile = (Long) session.getAttribute("profileNm");
        Member member = memberRepository.findByEmail(loggedInUser);
        Profile profile = profileRepository.findById(loggedInProfile).orElse(null);

        response.put("membershipRole", member.getMembershipRole());
        response.put("memberRole", member.getRole());
        response.put("maturityLevel", profile.getMaturityLevel());

        return ResponseEntity.ok(response);
    }


}
