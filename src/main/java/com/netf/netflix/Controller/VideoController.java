package com.netf.netflix.Controller;

import com.netf.netflix.Constant.VideoMaturityLevel;
import com.netf.netflix.Constant.VideoRole;
import com.netf.netflix.Dto.VideoFormDto;
import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Entity.VideoFile;
import com.netf.netflix.Entity.VideoImg;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoFileRepository;
import com.netf.netflix.Repository.VideoImgRepository;
import com.netf.netflix.Repository.VideoRepository;
import com.netf.netflix.Service.ProfileService;
import com.netf.netflix.Service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@Log
public class VideoController {

    private final VideoService videoService;
    private final VideoImgRepository videoImgRepository;
    private final VideoRepository videoRepository;
    private final ProfileRepository profileRepository;
    private final ProfileService profileService;
    private final VideoFileRepository videoFileRepository;
//    private static final List<VideoImgDto> uploadedVideoList = new ArrayList<>();


    @GetMapping(value = "/video/new")
    public String videoForm(Model model) {
        model.addAttribute("videoFormDto", new VideoFormDto());
        return "videos/videoForm";
    }

    @ResponseBody
    @PostMapping(value = "/video/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> videoCreateFrom(@ModelAttribute("videoFormDto") @Valid VideoFormDto videoFormDto,
                                             BindingResult bindingResult,
                                             @RequestParam("videoImgFile") MultipartFile videoImgFile,
                                             @RequestParam("videoFile") MultipartFile videoFile) throws Exception {
        if (bindingResult.hasErrors()) {
            // Handle validation errors
            Map<String, Object> response = new HashMap<>();
            response.put("code", 400);
            response.put("message", "업로드에 실패하였습니다. 다시 확인하시고 시도해주세요");
            response.put("errors", bindingResult.getAllErrors());
            return ResponseEntity.badRequest().body(response);
        }
        videoService.saveVideo(videoFormDto, videoImgFile, videoFile);
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("message", "업로드가 성공적으로 완료되었습니다.");
        return ResponseEntity.ok(response);
    }

    // 키워드를 통해 검색 결과를 가져온다
    @GetMapping("/search")
    public String videoList(Model model,String searchKeyword,HttpSession session) {

        Long profileId = (Long) session.getAttribute("profileNm");
        Profile profile = profileRepository.findById(profileId).orElse(null);

        System.out.println("나의 등급");
        System.out.println(profile.getMaturityLevel());

        Long selectedId = profileId;

        Optional<Profile> selectedProfile = profileRepository.findById(selectedId);
        model.addAttribute("selectedProfile", selectedProfile.orElse(null));

        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        String profileImageUrl = selectedProfile.map(Profile::getImageUrl).orElse(null);

        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        model.addAttribute("profileImageUrl", profileImageUrl);

        List<Video> list = null;
        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs", videoImgs);


        if (searchKeyword == null) {
            list = null;
        } else {
            list = videoService.videoSearchList(searchKeyword);
        }
// Containing은 Spring Data JPA의 메서드 네이밍 규칙 중 하나로, 문자열 필드를 포함하는 데이터를 검색하는데 사용됩니다 (비디오서비스 )

        if (profile != null && profile.getMaturityLevel() != null && profile.getMaturityLevel().equals(Profile.MaturityLevel.KID)) {
            System.out.println("키드입니다 "+ profile.getMaturityLevel() );
            List<Video> kidFilter = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);

            if (list != null) {
                list.retainAll(kidFilter);
                System.out.println("키드에 대한 영상만 추출 ");
            }
        }else{
            System.out.println("어른입니다");
        }


        model.addAttribute("list", list);
        model.addAttribute("uploadedVideoList", videoImgRepository.findAll());
        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());

        return "/rightmain/search";
    }

    @GetMapping("/drama/{profileId}")
    public String dramaList(@PathVariable("profileId") Long profileId, Model model, HttpSession session){

        session.setAttribute("profileNm",profileId);
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }
//        profileId를 통해 현재 프로파일을 찾는다

        // 헤더이미지부르는부분
        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        // favoriteVideos 값을 모델에 추가합니다 이것은 좋아요 data-iColor에서 videoid가 favoriteVideos에 포함된다면 red를 저장
        // 좋아요의 색깔을 표현할 때 활용한다
        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());

        // 레포지토리를 통해 모든 장르를 불러온다
        List<String> subjects = videoRepository.findAllGenres();
        // 레포지토리를 통해 videoRole이 드라마에 해당하는 비디오를 저장한다
        List<Video> videos = videoRepository.findByVideoRole(VideoRole.DRAMA);

        for (Video video : videos) {
            System.out.println(video.getId()+video.getVideoNm()+video.getGenres()+video.getDescription());
        }

//        랜덤비디오를 랜덤으로 5개 만들어줍니다

        List<Video> allVideos = videoRepository.findAll();
        List<Video> randomVideos = new ArrayList<>();
        Set<Integer> chosenIndexes = new HashSet<>();

        while (randomVideos.size() < 4 && chosenIndexes.size() < allVideos.size()) {
            int randomIndex = new Random().nextInt(allVideos.size());
            if (chosenIndexes.contains(randomIndex)) {
                continue; // 이미 선택된 인덱스는 건너뛰기 이 while문은 코드는 중복을 방지하기 위한 코드
            }
            chosenIndexes.add(randomIndex);
            Video randomVideo = allVideos.get(randomIndex);
            randomVideos.add(randomVideo);
        }

        model.addAttribute("randomVideo2", randomVideos);
        if (!randomVideos.isEmpty()) {
            model.addAttribute("randomVideo", randomVideos.get(0));
        }

        model.addAttribute("subjects",subjects);
        model.addAttribute("videos",videos);
            // 이 정보를 전달해주고 html에서 타임리프를 이용해서 각각의 장르마다 비디오에 해당하는 드라마들을 정리해준다
        // th:if문을 통해 장르에 해당하는 비디오를 출력한다 ( th:if="${videos != null and video.genres.contains(subject)}" )

        String profileImageUrl = profile.getImageUrl();
        // 프로필 이미지 URL이 null인 경우 기본값을 설정합니다
        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);
        model.addAttribute("profileImageUrl", profileImageUrl);

        return "/leftmain/drama";
    }

    // 무비는 드라마와 방식의 거이 비슷하다 List<Video> videos = videoRepository.findByVideoRole(VideoRole.MOVIE); 이것만 다르다
    @GetMapping("/movie/{profileId}")
    public String movieList(@PathVariable("profileId")Long profileId, Model model,HttpSession session){

        session.getAttribute("profileNm");
        // 프로필 ID를 통해 프로필을 조회하고 favoriteVideos 값을 가져옵니다
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }

        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());

        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        List<String> subjects = videoRepository.findAllGenres();
        List<Video> videos = videoRepository.findByVideoRole(VideoRole.MOVIE);

        for (Video video : videos) {
            System.out.println(video.getId()+video.getVideoNm()+video.getGenres()+video.getDescription());
        }
//랜덤비디오
        List<Video> allVideos = videoRepository.findAll();
        List<Video> randomVideos = new ArrayList<>();
        Set<Integer> chosenIndexes = new HashSet<>();

        while (randomVideos.size() < 4 && chosenIndexes.size() < allVideos.size()) {
            int randomIndex = new Random().nextInt(allVideos.size());
            if (chosenIndexes.contains(randomIndex)) {
                continue; // 이미 선택된 인덱스는 건너뛰기
            }
            chosenIndexes.add(randomIndex);
            Video randomVideo = allVideos.get(randomIndex);
            randomVideos.add(randomVideo);
        }

        model.addAttribute("randomVideo2", randomVideos);
        if (!randomVideos.isEmpty()) {
            model.addAttribute("randomVideo", randomVideos.get(0));
        }

        String profileImageUrl = profile.getImageUrl();
        // 프로필 이미지 URL이 null인 경우 기본값을 설정합니다
        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        model.addAttribute("profileImageUrl", profileImageUrl);

        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);
        model.addAttribute("subjects",subjects);
        model.addAttribute("videos",videos);


        return "/leftmain/movie";
    }

    // 키드도 드라마 , 영화와 비슷한 방식
    @GetMapping("/kid/{profileId}")
    public String kidList(@PathVariable("profileId")Long profileId, Model model,HttpSession session){

        session.getAttribute("profileNm");
        // 프로필 ID를 통해 프로필을 조회하고 favoriteVideos 값을 가져옵니다
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }

        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());

        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

//        여기서 부터 키드 영상에 대한 코드
        
        List<String> subjects = videoRepository.findAllGenres();
        List<Video> videos = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID);

        for (Video video : videos) {
            System.out.println(video.getId()+video.getVideoNm()+video.getGenres()+video.getDescription());
        }
//랜덤비디오 이것도 키즈비디오에 대한 랜덤비디오로 바꿔야한다
        List<Video> allVideos = videoRepository.findByVideoMaturityLevel(VideoMaturityLevel.KID); //드라마, 무비와 차이점
        List<Video> randomVideos = new ArrayList<>();
        Set<Integer> chosenIndexes = new HashSet<>();

        while (randomVideos.size() < 4 && chosenIndexes.size() < allVideos.size()) {
            int randomIndex = new Random().nextInt(allVideos.size());
            if (chosenIndexes.contains(randomIndex)) {
                continue; // 이미 선택된 인덱스는 건너뛰기
            }
            chosenIndexes.add(randomIndex);
            Video randomVideo = allVideos.get(randomIndex);
            randomVideos.add(randomVideo);
        }

        model.addAttribute("randomVideo2", randomVideos);
        if (!randomVideos.isEmpty()) {
            model.addAttribute("randomVideo", randomVideos.get(0));
        }

        String profileImageUrl = profile.getImageUrl();
        // 프로필 이미지 URL이 null인 경우 기본값을 설정합니다
        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        model.addAttribute("profileImageUrl", profileImageUrl);

        List<VideoImg> videoImgs = videoImgRepository.findAll();

        List<VideoImg> kidImgFilter = videoImgRepository.findByVideoVideoMaturityLevel(VideoMaturityLevel.KID);

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
        model.addAttribute("subjects",subjects);
        model.addAttribute("videos",videos);


        return "/leftmain/kid";
    }


    @GetMapping("/mylist/{profileId}")
    public String myList(@PathVariable("profileId")Long profileId, Model model,HttpSession session){

        session.getAttribute("profileNm");
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }
        profileService.printFavoriteVideoIds(profileId);

        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());

        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);
        // 좋아하는 비디오 리스트 가져오기
        Set<Long> favoriteVideosId = profile.getFavoriteVideos();

        String profileImageUrl = profile.getImageUrl();
        // 프로필 이미지 URL이 null인 경우 기본값을 설정합니다
        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);
        model.addAttribute("profileImageUrl", profileImageUrl);
        List<Video> videos =videoRepository.findAllById(favoriteVideosId);
        // 모델에 좋아하는 비디오 리스트 추가
        model.addAttribute("videos", videos);
        return "/leftmain/mylist";
    }

    @GetMapping("/recent/{profileId}")
    public String recentView(@PathVariable("profileId")Long profileId ,Model model,HttpSession session){

        session.getAttribute("profileNm");
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        Profile selectedProfile = profileRepository.findById(profileId).orElse(null);
        if (selectedProfile == null) {
            throw new RuntimeException("프로필을 찾을 수 없습니다.");
        }
        profileService.printRecentlyViewedVideos(profileId);

        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());
        model.addAttribute("selectedProfile", selectedProfile);
        List<Profile> otherProfiles = profileRepository.findByMember(profile.getMember())
                .stream()
                .filter(p -> !p.getId().equals(profileId))
                .collect(Collectors.toList());
        model.addAttribute("otherProfiles", otherProfiles);

        // 좋아하는 비디오 리스트 가져오기
        List<Long> recentViewId = profile.getRecentlyViewedVideos();
        List<Video> recentVideos = new ArrayList<>();
        List<VideoImg> videoImgs = videoImgRepository.findAll();
        model.addAttribute("videoImgs",videoImgs);

        for (Long videoId : recentViewId) {
            Optional<Video> optionalVideo = videoRepository.findById(videoId);
            if (optionalVideo.isPresent()) {
                Video video = optionalVideo.get();
                recentVideos.add(video);
            } else {
                // 비디오를 찾지 못한 경우에 대한 처리를 여기에 추가하면 됩니다. ( println문 출력 )
                System.out.println("비디오를 찾을 수 없습니다. videoId: " + videoId);
            }
        }
        String profileImageUrl = profile.getImageUrl();
        // 프로필 이미지 URL이 null인 경우 기본값을 설정합니다
        if (profileImageUrl == null) {
            profileImageUrl = "/images/default-profile-image.jpg";  // 기본 이미지 URL 설정
        }
        model.addAttribute("profileImageUrl", profileImageUrl);

        model.addAttribute("recentVideos", recentVideos);
        return "/leftmain/recent";
    }

    @GetMapping(value = "/videoListForm")
    public String videoList(@RequestParam(defaultValue = "0") int page, Model model) {
        int pageSize = 10; // 페이지당 아이템 개수

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Video> videoPage = videoRepository.findPaginated(pageable);
        List<Video> videoList = videoPage.getContent();
        int totalPages = videoPage.getTotalPages();

        model.addAttribute("videoList", videoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "videos/videoListForm";

    }

    @GetMapping("/videoList-search")
    public String searchVideos(@RequestParam("editSearch") String searchKeyword,
                               @RequestParam(defaultValue = "0") int page,
                               Model model) {
        int pageSize = 10;

        List<Video> searchResults = videoService.videoSearchList(searchKeyword);

        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, searchResults.size());

        List<Video> videoList = searchResults.subList(startIndex, endIndex);

        model.addAttribute("videoList", videoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", (int) Math.ceil((double) searchResults.size() / pageSize));

        return "videos/videoListForm";
    }

    @PostMapping("/video-Delete")
    public String deleteVideo(@RequestParam("videoId") Long videoId,
                              @RequestParam(defaultValue = "0") int page, Model model) {

        videoService.deleteVideo(videoId);

        int pageSize = 10; // 페이지당 아이템 개수

        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Video> videoPage = videoRepository.findPaginated(pageable);
        List<Video> videoList = videoPage.getContent();
        int totalPages = videoPage.getTotalPages();

        model.addAttribute("videoList", videoList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "videos/videoListForm";
    }

    @GetMapping("/videoEdit{videoId}")
    public String editVideo(@RequestParam("videoId") Long videoId, Model model) {

        Video video = videoRepository.findById(videoId).orElse(null);

        model.addAttribute("video", video);

        return "videos/videoEditForm";
    }

    @PostMapping("/videoUpdate")
    public String videoUpdate(@ModelAttribute("videoFormDto") VideoFormDto videoFormDto,
                              @RequestParam("videoId") Long videoId,
                              @RequestParam("videoImgFile") MultipartFile videoImgFile,
                              @RequestParam("videoFile") MultipartFile videoFile,
                              Model model) throws Exception {
        videoFormDto.setId(videoId);

        videoService.updateVideo(videoFormDto, videoImgFile, videoFile);

        return "redirect:/videoEdit?videoId=" + videoId;
    }

    @GetMapping("/viewCount")
    public String countView(Model model, HttpSession session) {
        Long profileId = (Long) session.getAttribute("profileNm");
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        model.addAttribute("favoriteVideos", profile.getFavoriteVideos());
        // 조회수의 순서대로 상위 10개의 비디오를 가져오기
        List<Video> top10Videos = videoRepository.findTop10ByOrderByViewCountDesc();
        model.addAttribute("top10Videos", top10Videos);

        return "/leftmain/viewTest";
    }


}
