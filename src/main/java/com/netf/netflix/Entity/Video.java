//package com.netf.netflix.Entity;
//
//import com.netf.netflix.constant.VideoRole;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//import javax.persistence.*;
//import java.util.List;
//
//@Entity
//@Getter
//@Setter
//@ToString
//@Table(name = "Video")
//public class Video {
//
//    @Id
//    @Column(name = "Video_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    @Column(nullable = false, length = 50)
//    private String VideoNm;
//
//    private String director;
//
//    @ElementCollection
//    private List<String> mainActors;
////    양방향 매핑이 아니라면 @ElementCollection 을 @mappedby 와 같이 써도 괜찮다는 말이 있어서 일단 @ElementCollection 적용
//
//    @ElementCollection
//    private List<String> genres;
//
//    private int views;
//
//    @Enumerated(EnumType.STRING)
//    private VideoRole videoRole;
//
//}
