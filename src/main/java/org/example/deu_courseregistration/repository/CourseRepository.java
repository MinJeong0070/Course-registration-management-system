package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    // JPQL은 JPA에서 객체 기반의 쿼리를 작성하기 위한 언어(JPA를 이용해 데이터를 조회하는 방법 중 하나)
    // JPQL은 Native Query와 다르게 테이블 이름이 아닌 엔티티 이름과 엔티티 필드 이름을 사용

    // 사용자지정 전체 강좌 목록 출력
    @Query("SELECT new org.example.deu_courseregistration.dto.CourseDto(" +
            "       c.courseId, " +                 // 강좌번호
            "       s.department.departmentName, " +// 개설학과
            "       s.subjectId, " +                // 교과목번호
            "       s.subjectName, " +              // 교과목명
            "       s.credits, " +                  // 학점
            "       c.grade, " +                    // 학년
            "       c.classroom, " +                // 강의실
            "       CONCAT(c.day, ', ', c.courseStartTime, ' - ', c.courseEndTime), " + // 강의시간
            "       c.professor.professorName, " +  // 교수 이름
            "       c.currentEnrollment, " +  // 수강인원
            "       c.enrollmentCapacity, " +      // 제한인원
            "       null) " +                      // 대기순번(null)
            "FROM Course c " +

            // 또한 JPQL의 경우 엔티티에서 관계 필드로 직접 조인이 되어있으면 ON절 생략(추가적인 JOIN이 필요할 경우에 ON절 사용)
            "JOIN c.subject s " +                   // 강좌와 교과목 JOIN
            "JOIN s.department d " +                // 교과목과 학과 JOIN
            "JOIN c.professor p")
    // 강좌와 교수 JOIN
    List<CourseDto> findCustomCourseDetails();

    // 강좌 검색
    @Query("SELECT new org.example.deu_courseregistration.dto.CourseDto(" +
            "c.courseId, d.departmentName, s.subjectId, s.subjectName, s.credits, " +
            "c.grade, c.classroom, CONCAT(c.courseStartTime, ' - ', c.courseEndTime, ', ', c.day), " +
            "p.professorName, c.currentEnrollment, c.enrollmentCapacity, null) " +
            "FROM Course c " +
            "JOIN c.subject s " +
            "JOIN s.department d " +
            "JOIN c.professor p " +
            "WHERE (:subjectId IS NULL OR s.subjectId = :subjectId) " +
            "AND (:subjectName IS NULL OR s.subjectName LIKE CONCAT('%', :subjectName, '%')) " +
            "AND (:professorName IS NULL OR p.professorName LIKE CONCAT('%', :professorName, '%')) " +
            "AND (:departmentName IS NULL OR d.departmentName LIKE CONCAT('%', :departmentName, '%')) " +
            "AND (:grade IS NULL OR c.grade = :grade)")
    List<CourseDto> searchCourses(
            @Param("subjectId") String subjectId,
            @Param("subjectName") String subjectName,
            @Param("professorName") String professorName,
            @Param("departmentName") String departmentName,
            @Param("grade") Integer grade);
}