package org.example.deu_courseregistration.service;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.repository.CourseCartRepository;
import org.example.deu_courseregistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSearchService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseCartRepository courseCartRepository;

    public List<CourseDto> searchCourses(String studentId, String subjectId, String subjectName, String professorName, String departmentName, Integer grade, String type) {

        if ("장바구니".equals(type)) {
            // 장바구니에서 강좌 검색
            return courseCartRepository.findCoursesInCartByStudentId(studentId);
        } else {
            // 전체 강좌 검색
            return courseRepository.searchCourses(subjectId, subjectName, professorName, departmentName, grade);
        }
    }
}