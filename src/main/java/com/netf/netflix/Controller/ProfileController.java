package com.netf.netflix.Controller;

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
        model.addAttribute("videoImgs",videoImgs);

        //비디오 부분
        /// 비디오 내용 홈와면의 비디오 내용 필요한것 : 랜덤/ 최근 / 새로 올라운 콘텐츠 / top10
//       프로파일 이미지는 이미 있기 때문에 세션으로 가져올 필요없다
//        Long profileId = (Long) session.getAttribute("profileNm");
//        Profile profile = profileRepository.findById(profileId)
//                .orElseThrow(() -> new RuntimeException("Profile not found"));
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

        model.addAttribute("randomVideo2", randomVideos);
        if (!randomVideos.isEmpty()) {
            model.addAttribute("randomVideo", randomVideos.get(0));
        }
//        중복 부분을 해결

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


        List<Video> uploadVideos = videoRepository.findDistinctByOrderByVideoImgUploadDateDesc();
        if(uploadVideos!=null){
            model.addAttribute("uploadVideos",uploadVideos );
        }else{
            System.out.println("null입니다");
        }

        List<Video> top10Videos = videoRepository.findTop10ByOrderByViewCountDesc();
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
    public String profileForm(Model model, @RequestParam(value = "image",required = false)String imageUrl) {
        model.addAttribute("profileDto", new ProfileDto());
        model.addAttribute("imageUrl",imageUrl);
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
        Optional<Profile> optionalProfile = profileRepository.findById(profileId);
        if (optionalProfile.isEmpty()) {
            throw new RuntimeException("프로필 정보를 찾을 수 없습니다.");
        }
        Profile profile = optionalProfile.get();
        model.addAttribute("profile", profile);
        return "profile-updateform";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("nickname") String nickname,
                                @RequestParam("maturityLevel") Profile.MaturityLevel maturityLevel,
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

