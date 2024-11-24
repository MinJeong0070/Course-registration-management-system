package org.example.deu_courseregistration.service;

import jakarta.transaction.Transactional;
import org.example.deu_courseregistration.entity.Course;
import org.example.deu_courseregistration.entity.CourseCart;
import org.example.deu_courseregistration.entity.Student;
import org.example.deu_courseregistration.entity.courseCartId;
import org.example.deu_courseregistration.repository.courseCartRepository;
import org.example.deu_courseregistration.repository.courseRepository;
import org.example.deu_courseregistration.repository.studentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Optional;

@Service
public class CourseCartService {
    @Autowired
    private courseCartRepository courseCartRepository;

    @Autowired
    private courseRepository courseRepository;

    @Autowired
    private studentRepository studentRepository;

    @Transactional
    public String addToCart(String studentId, Long courseId) {
        // 학생 정보와 강좌 정보 가져오기
        Optional<Student> studentOptional = studentRepository.findById(studentId);
        Optional<Course> courseOptional = courseRepository.findById(courseId);

        if (studentOptional.isEmpty()) {
            return "해당 학번의 학생을 찾을 수 없습니다.";
        }
        if (courseOptional.isEmpty()) {
            return "해당 교과목번호의 강좌를 찾을 수 없습니다.";
        }

        Student student = studentOptional.get();
        Course course = courseOptional.get();

        // 이미 장바구니에 있는지 확인
        courseCartId courseCartId = new courseCartId(student.getStudentId(), course.getCourseId());
        if (courseCartRepository.existsById(courseCartId)) {
            return "이미 장바구니에 담긴 강좌입니다.";
        }

        // 장바구니에 추가
        CourseCart courseCart = new CourseCart();
        courseCart.setId(courseCartId);
        courseCart.setStudentId(student);
        courseCart.setCourseId(course);

        courseCartRepository.save(courseCart);

        return "장바구니에 강좌를 성공적으로 담았습니다.";
    }
}