package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.entity.CourseRegistration;
import org.example.deu_courseregistration.entity.courseRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRegistrationRepository extends JpaRepository<CourseRegistration, courseRegistrationId> {

    // 수강신청 기간을 체크하는 저장 프로시저 호출
    @Procedure(name = "checkCourseRegistrationPeriod")
    Integer checkCourseRegistrationPeriod();
}

