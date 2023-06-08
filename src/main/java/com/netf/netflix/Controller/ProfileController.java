package com.netf.netflix.Controller;

import com.netf.netflix.Dto.ProfileDto;
import com.netf.netflix.Entity.Member;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Repository.MemberRepository;
//import com.netf.netflix.Repository.ProfileImgRepository;
import com.netf.netflix.Repository.ProfileRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private final ProfileService profileService;
    private final MemberRepository memberRepository;
    private final ProfileRepository profileRepository;
//    private final ProfileImgRepository profileImgRepository;

    @GetMapping("/home/{profileId}")
    public String showProfileHome(@PathVariable("profileId") Long profileId, Model model) {
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

        return "home"; // 헤더 템플릿을 리턴하도록 수정해주세요.
    }
//    @GetMapping("/profile/{id}")
//    public String viewProfile(@PathVariable("id") Long profileId, Model model) {
//        // 프로필 ID를 사용하여 프로필 정보를 조회
//        Optional<Profile> profileOptional = profileRepository.findById(profileId);
//        if (profileOptional.isEmpty()) {
//            // 프로필이 존재하지 않을 경우 에러 처리 또는 다른 로직을 추가할 수 있습니다.
//            return "error-page";
//        }
//
//        Profile profile = profileOptional.get();
//
//        // 프로필 정보를 모델에 추가
//        model.addAttribute("profile", profile);
//
//        // 프로필 이미지 경로를 모델에 추가
//        String imagePath = profile.getImagePath(); // 이미지 경로 설정
//        model.addAttribute("profileImage", imagePath);
//
//        // 프로필 페이지로 이동
//        return "profile";
//    }
    @GetMapping("/profile")
    public String showProfile(Model model, HttpSession session) {
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        Member member = memberRepository.findByEmail(loggedInUser);
        if (member == null) {
            throw new RuntimeException("사용자 정보를 찾을 수 없습니다.");
        }
        List<Profile> profiles = profileRepository.findByMember(member);
//        System.out.println("profiles : " + profiles);
        model.addAttribute("profiles", profiles);
        return "profile";
    }

    @GetMapping("/profile-new")
    public String profileForm(Model model) {
        model.addAttribute("profileDto", new ProfileDto());
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

        profileDto.updateProfileFields(); // 프로필 필드 값을 업데이트

        Profile profile = profileDto.getProfile();
        profile.setMember(member);
        profileService.saveProfile(profile);

        logger.info("Profile saved: {}", profile);

        return "redirect:/profile/profile";
    }
}
//    @Autowired
//    private ProfileRepository profileRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    private JdbcTemplate jdbcTemplate;
//
//    private final ProfileService profileService;
//
//    private final ProfileImgService profileImgService;
//
//    @Autowired
//    private ProfileController(ProfileService profileService, ProfileImgService profileImgService) {
//        this.profileService = profileService;
//        this.profileImgService = profileImgService;
//    }
//
//    @GetMapping("/profile-new")
//    public String createProfile(Model model, @RequestParam(required = false) String image) {
//        Profile profile = new Profile();
//        profile.setLanguage("한국어");
//        profile.setMaturityLevel("모든 관람등급");
//        model.addAttribute("profile", profile);
//        String defaultImagePath = "images/icons/plus.png";
//        String imagePath = (image != null && !image.isEmpty()) ? image : defaultImagePath;
//        model.addAttribute("profileImage", imagePath);
//
//        ProfileImgDto profileImgDto = new ProfileImgDto();
//        profileImgDto.setProfileImgUrl(defaultImagePath); // 기본 이미지 URL 설정
//        model.addAttribute("profileImgDto", profileImgDto);
//
//        return "/profile-new";
//    }
//
//    @Transactional
//    @PostMapping("/profile/saveProfile/")
//    public String saveProfile(@ModelAttribute("profile") Profile profile, @ModelAttribute("profileImgDto") ProfileImgDto profileImgDto, Principal principal,Model model) {
//        try {
//            String memberId = principal.getName();
//
//            // 멤버 아이디를 디비에 저장
//            Member member = memberRepository.findByEmail(memberId);
//            profile.setMember(member);
//            member.setProfile(profile);
//
//            // 프로필 이미지 URL 설정
//            String profileImageUrl = profileImgDto.getProfileImgUrl();
//            profile.setProfileImageUrl(profileImageUrl);
//
//            // 프로필 저장 로직
//            profileRepository.save(profile);
//
//            return "redirect:/profile";
//        } catch (Exception e) {
//            // 저장 과정에서 예외가 발생하면 로그에 기록하고 예외 처리 로직을 추가합니다.
//            e.printStackTrace(); // 예외 로그 출력
//            // 예외 처리 로직 추가
//             model.addAttribute("errorMessage", "프로필 저장에 실패했습니다.");
//
//            // 로그인 페이지로 리다이렉트 또는 에러 페이지를 보여줄 수 있습니다.
//
//
//            return "error-page"; // 예외 처리 결과에 따라 적절한 페이지로 리다이렉트하거나 에러 페이지를 보여줍니다.
//        }
//    }
//
//    @PostMapping("/save-profile-image")
//    public ResponseEntity<ProfileImgDto> uploadProfileImage(@RequestParam("file") MultipartFile file) {
//        try {
//            // 이미지 저장 로직을 구현하고, 성공 여부에 따라 ResponseEntity를 반환합니다.
//            // 예시로는 ProfileImgService를 이용하여 이미지를 저장하는 로직을 사용합니다.
//            ProfileImg profileImg = profileImgService.saveProfileImg(file);
//            ProfileImgDto profileImgDto = ProfileImgDto.of(profileImg);
//            return ResponseEntity.ok(profileImgDto);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//
//    @GetMapping("/profile/{id}")
//    public String viewProfile(@PathVariable("id") Long profileId, Model model) {
//        // 프로필 ID를 사용하여 프로필 정보를 조회
//        Optional<Profile> profileOptional = profileRepository.findById(profileId);
//        if (profileOptional.isEmpty()) {
//            // 프로필이 존재하지 않을 경우 에러 처리 또는 다른 로직을 추가할 수 있습니다.
//            return "error-page";
//        }
//
//        Profile profile = profileOptional.get();
//
//        // 프로필 정보를 모델에 추가
//        model.addAttribute("profile", profile);
//
//        // 프로필 페이지로 이동
//        return "profile";
//    }

