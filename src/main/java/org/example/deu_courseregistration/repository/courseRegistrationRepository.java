package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.entity.CourseRegistration;
import org.example.deu_courseregistration.entity.courseRegistrationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseRegistrationRepository extends JpaRepository<CourseRegistration, courseRegistrationId>{
}