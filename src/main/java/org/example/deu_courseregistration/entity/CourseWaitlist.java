package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "수강대기") // Oracle 테이블 이름 매핑
@NoArgsConstructor
public class CourseWaitlist {
    @EmbeddedId
    private courseWaitlistId id;

    @ManyToOne
    @MapsId("studentId")
    @JoinColumn(name = "학번", referencedColumnName = "학번")
    private Student studentId;

    @ManyToOne
    @MapsId("courseId")
    @JoinColumn(name = "강좌번호", referencedColumnName = "강좌번호")
    private Course courseId;

    @Column(name = "대기순번", nullable = false)
    private Integer waitlistPosition;
}