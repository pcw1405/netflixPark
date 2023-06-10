package com.netf.netflix.Service;

import com.netf.netflix.Entity.Profile;
import com.netf.netflix.Entity.Video;
import com.netf.netflix.Repository.ProfileRepository;
import com.netf.netflix.Repository.VideoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class ProfileService {
    private final ProfileRepository profileRepository;
    private final VideoRepository videoRepository;
    @Autowired
    public ProfileService(ProfileRepository profileRepository,VideoRepository videoRepositoryRepository) {
        this.profileRepository = profileRepository;
        this.videoRepository =videoRepositoryRepository;

    }

    public void saveProfile(Profile profile) {
        profileRepository.save(profile);
    }



    public void addVideoToFavorites(Long profileId, Long videoId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));

        // 좋아요한 동영상을 중복 추가하지 않도록 체크
        if (profile.getFavoriteVideos() == null) {
            profile.setFavoriteVideos(new HashSet<>());
        } else if (profile.getFavoriteVideos().contains(videoId)) {
            throw new RuntimeException("Video already added to favorites");
        }

        profile.getFavoriteVideos().add(videoId);

        profileRepository.save(profile);
        printFavoriteVideoIds(profileId);
    }

    public void printFavoriteVideoIds(long profileId) {
        System.out.println(profileId+"의 좋아요목록");
        Optional<Profile> profile = profileRepository.findById(profileId);
        if (profile.isPresent()) {
            Set<Long> favoriteVideos = profile.get().getFavoriteVideos();
            for (Long videoId : favoriteVideos) {
                System.out.println("Favorite Video ID: " + videoId);
            }
        }
    }
}