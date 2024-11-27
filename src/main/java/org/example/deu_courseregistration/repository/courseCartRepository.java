package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.entity.CourseCart;
import org.example.deu_courseregistration.entity.courseCartId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface courseCartRepository extends JpaRepository<CourseCart, courseCartId> {
}