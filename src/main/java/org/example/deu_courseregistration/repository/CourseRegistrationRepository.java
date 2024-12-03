package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.entity.CourseRegistration;
import org.example.deu_courseregistration.entity.courseRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, courseRegistrationId> {

    // 수강신청 기간을 체크하는 저장 프로시저 호출
    @Procedure(name = "checkCourseRegistrationPeriod")
    Integer checkCourseRegistrationPeriod();

    // 수강신청 데이터 출력용 커스텀 JPQL 쿼리
    @Query("SELECT new org.example.deu_courseregistration.dto.CourseDto(" +
            "       c.courseId, " +                 // 강좌번호
            "       d.departmentName, " +          // 개설학과
            "       s.subjectId, " +               // 교과목번호
            "       s.subjectName, " +             // 교과목명
            "       s.credits, " +                 // 학점
            "       c.grade, " +                   // 학년
            "       c.classroom, " +               // 강의실
            "       CONCAT(c.day, ', ', c.courseStartTime, ' - ', c.courseEndTime), " + // 강의시간
            "       p.professorName, " +           // 교수 이름
            "       c.currentEnrollment, " +       // 수강인원
            "       c.enrollmentCapacity, " +      // 제한인원
            "       null) " +                      // 대기순번(null)
            "FROM CourseRegistration cr " +
            "JOIN cr.courseId c " +                // 장바구니와 강좌 JOIN
            "JOIN c.subject s " +                  // 강좌와 교과목 JOIN
            "JOIN s.department d " +               // 교과목과 학과 JOIN
            "JOIN c.professor p " +                // 강좌와 교수 JOIN
            "WHERE cr.studentId.studentId = :studentId") // 특정 학생의 수강신청내역
    List<CourseDto> findCoursesInRegistrationByStudentId(@Param("studentId") String studentId);
}

