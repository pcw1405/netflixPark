package com.netf.netflix.Controller;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Dto.ProfileDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.MemberRepository;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;

import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
    private final VideoImgRepository videoImgRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/home/{profileId}")
    public String showProfileHome(@PathVariable("profileId") Long profileId, Model model,HttpSession session) {
        session.setAttribute("profileNm",profileId);
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }
        // 모델에 선택된 프로필 정보를 추가
        model.addAttribute("selectedProfile", selectedProfile);

        // 나머지 프로필 정보를 가져와서 모델에 추가
        List<Profile> otherProfiles = profileRepository.findByMember(selectedProfile.getMember())
                .stream()
                .filter(profile -> !profile.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);
//        (하단내용 movie drama mylist 추가해야함)
        List<VideoImg> videoImgs = videoImgRepository.findAll();

        List<VideoImg> kidImgFilter = videoImgRepository.findByVideoVideoMaturityLevel(VideoMaturityLevel.KID);

        if (selectedProfile != null && selectedProfile.getMaturityLevel() != null && selectedProfile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
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
//        Long profileId = (Long) session.getAttribute("profileNm");
//        Profile profile = profileRepository.findById(profileId)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
        // 랜덤 부분

        //키드만 추출하는 키드필터(키드비디오들) 생성
        List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);
        System.out.println("나의 MaturityLevel: " + (selectedProfile != null ? selectedProfile.getMaturityLevel() : "unknown"));

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

        if (selectedProfile != null && selectedProfile.getMaturityLevel() != null && selectedProfile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
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
//        중복 부분을 해결

        //최근본
        List<Long> recentViewId =  selectedProfile.getRecentlyViewedVideos();
        List<Video> recentVideos = new ArrayList<>();

        for (Long videoId : recentViewId) {
            Optional<Video> optionalVideo = videoRepository.findById(videoId);
            if (optionalVideo.isPresent()) {
                Video video = optionalVideo.get();
                recentVideos.add(video);
            } else {
                // 비디오를 찾지 못한 경우에 대한 처리를 여기에 추가하면 됩니다.
                // 예를 들어, 로그를 출력하거나 다른 예외를 던지는 등의 작업을 수행할 수 있습니다.
                System.out.println("비디오를 찾을 수 없습니다. videoId: " + videoId);
            }
        }

        model.addAttribute("recentVideos", recentVideos);

        // 찜목록
        model.addAttribute("favoriteVideos", selectedProfile.getFavoriteVideos());

        // 좋아하는 비디오 리스트 가져오기
        Set<Long> favoriteVideosId = selectedProfile.getFavoriteVideos();

        List<Video> like_videos =videoRepository.findAllById(favoriteVideosId);
        // 모델에 좋아하는 비디오 리스트 추가
        model.addAttribute("like_videos",like_videos);


        List<Video> uploadVideos = videoRepository.findDistinctByOrderByVideoImgUploadDateDesc();

        if (selectedProfile != null && selectedProfile.getMaturityLevel() != null && selectedProfile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
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




        List<Video> top10Videos = videoRepository.findTop10ByViewCountGreaterThanOrderByViewCountDesc(0);

        if (selectedProfile != null && selectedProfile.getMaturityLevel() != null && selectedProfile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
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


        return "home"; // 헤더 템플릿을 리턴하도록 수정해주세요.

    }
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
        List<Profile> profiles = profileRepository.findByMember(member);

        List<String> imageUrls = new ArrayList<>();
        for (Profile profile : profiles) {
            String imageUrl = profile.getImageUrl(); // 이미지 URL을 가져오는 로직
            imageUrls.add(imageUrl);
        }
        model.addAttribute("profiles", profiles);
        model.addAttribute("imageUrls", imageUrls);

        return "profile";
    }
    @GetMapping("/profile-new")
    public String profileForm(Model model, @RequestParam(value = "image", required = false) String imageUrl, RedirectAttributes redirectAttributes,HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }

        List<Profile> profiles = profileRepository.findByMember(member);
        String message="";
        if (profiles.size() >= 5) {
            // 프로필 생성 갯수가 5개 이상인 경우 에러 메시지를 알림으로 설정하고 이전 페이지로 이동합니다.
            redirectAttributes.addFlashAttribute("message", "프로필 생성 갯수가 제한을 초과하였습니다.");
            return "redirect:/profile/profile";
        }

        model.addAttribute("profileDto", new ProfileDto());
        model.addAttribute("imageUrl", imageUrl);
        return "profile-new";
    }


    @PostMapping("/saveProfile")
    public String saveProfile(@Valid @ModelAttribute("profileDto") ProfileDto profileDto, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            logger.error("Validation errors occurred: {}", bindingResult.getAllErrors());
            return "profile-new";
        }

        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }


        //프로필이미지연동 하단 4줄
        profileDto.getProfile().setMember(member); // 멤버와 프로필 연결

        String imageUrl = profileDto.getImageUrl(); // 이미지 URL 가져오기
        profileDto.getProfile().setImageUrl(imageUrl); // 프로필 엔티티의 imageUrl 필드에 저장
        profileDto.updateProfileFields(); // 프로필 필드 값을 업데이트

        profileService.saveProfile(profileDto.getProfile()); // 프로필 저장

        return "redirect:/profile/profile";
    }
    @GetMapping("/profile-update")
    public String updateProfile(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
        List<Profile> profiles = profileRepository.findByMember(member);
        model.addAttribute("profiles", profiles);
        return "profile-update";
    }

    @GetMapping("/profile-updateform/{profileId}")
    public String updateProfileForm(@PathVariable("profileId") Long profileId, Model model, HttpSession session) {
        session.setAttribute("profileId", profileId);
        Profile profile = profileRepository.findById(profileId).orElse(null);
        if (profile == null) {
            throw new RuntimeException("프로필 정보를 찾을 수 없습니다.");
        }
        model.addAttribute("profile", profile);
        return "profile-updateform";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("nickname") String nickname,
                                @RequestParam(value = "maturityLevel") Profile.MaturityLevel maturityLevel,
                                @RequestParam("language") String language,
                                HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileId");
        Profile profile = profileRepository.findById(profileId).orElse(null);

        profile.setNickname(nickname);
        profile.setName(name);
        profile.setLanguage(language);
        profile.setMaturityLevel(maturityLevel);

        profileRepository.save(profile);

        return "redirect:/profile/profile";
    }
    @PostMapping(value = "/profile-delete")
    public String userProfileDelete(@RequestParam("profileId") Long profileId){

        profileRepository.deleteById(profileId);

        return "redirect:/profile/profile";
    }
}

