//package com.netf.netflix.Repository;
//
//import com.netf.netflix.Entity.Profile;
////import com.netf.netflix.Entity.ProfileImg;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//
//@Repository
//public interface ProfileImgRepository  extends JpaRepository<ProfileImg, Long> {
//  ProfileImg getFirstByOrderById();
//
//  @Query("SELECT p.profileImgUrl FROM ProfileImg p WHERE p.id = :id")
//  String getProfileImageUrl(@Param("id") Long id);
//
//  List<ProfileImg> findByProfile(Profile profile);
//
//}
