package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "강좌") // Oracle 테이블 이름 매핑
@NoArgsConstructor
public class Course {
    @Id
    @Column(name = "강좌번호")
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가
    private long courseId;              // 강좌번호

    @ManyToOne
    @JoinColumn(name = "교과목번호", referencedColumnName = "교과목번호")
    private Subject subject; // 교과목 엔티티와의 관계 매핑

    @ManyToOne
    @JoinColumn(name = "교수번호", referencedColumnName = "교수번호")
    private Professor professor; // 교수 엔티티와의 관계 매핑

    @Column(name = "강의실", length = 6)
    private String classroom;           // 강의실

    @Column(name = "수강학년")
    private Integer grade;              // 수강학년

    @Column(name = "수강인원")
    private Integer enrollmentCapacity; // 최대 수강 인원

    @Column(name = "강의시작시간", length = 5)
    private String courseStartTime;     // 강의 시작 시간

    @Column(name = "강의종료시간", length = 5)
    private String courseEndTime;       // 강의 종료 시간

    @Column(name = "요일", length = 10)
    private String day;                 // 요일
}