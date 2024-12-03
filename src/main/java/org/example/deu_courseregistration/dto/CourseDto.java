package org.example.deu_courseregistration.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private long courseId;              // 강좌번호
    private String departmentName;      // 개설학과 (학과명)
    private String subjectId;           // 교과목번호
    private String subjectName;         // 교과목명
    private BigDecimal credits;         // 학점
    private Integer grade;              // 학년
    private String classroom;           // 강의실
    private String lectureTime;         // 강의시간 (10:00 - 13:00, 화)
    private String professorName;       // 담당교수 (교수명)
    private Integer currentEnrollment;  // 현재수강인원
    private Integer enrollmentCapacity; // 제한 인원
    private Integer waitlistPosition;   // 대기순번
}

