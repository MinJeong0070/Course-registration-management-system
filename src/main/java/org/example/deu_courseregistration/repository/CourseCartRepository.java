package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.entity.CourseCart;
import org.example.deu_courseregistration.entity.courseCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseCartRepository extends JpaRepository<CourseCart, courseCartId> {
    // 장바구니 데이터 출력용 커스텀 JPQL 쿼리
    // JPQL에서 Course c와 같은 명시적인 별칭(alias)을 생략할 수 있는 이유는
    // JPQL이 엔티티의 필드 및 관계를 기반으로 한 객체 지향 쿼리이기 때문
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
            "FROM CourseCart cc " +
            "JOIN cc.courseId c " +                // 장바구니와 강좌 JOIN
            "JOIN c.subject s " +                  // 강좌와 교과목 JOIN
            "JOIN s.department d " +               // 교과목과 학과 JOIN
            "JOIN c.professor p " +                // 강좌와 교수 JOIN
            "WHERE cc.studentId.studentId = :studentId")
    // 특정 학생의 장바구니
    List<CourseDto> findCoursesInCartByStudentId(@Param("studentId") String studentId);
}