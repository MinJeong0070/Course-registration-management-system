package org.example.deu_courseregistration.repository;

import org.example.deu_courseregistration.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface studentRepository extends JpaRepository<Student, String> {
}
