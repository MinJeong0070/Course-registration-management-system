package org.example.deu_courseregistration.service;
import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseSearchService {
    @Autowired
    private CourseRepository courseRepository;

//    public List<courseDto> searchCourses(String subjectId, String subjectName, String professorName, String departmentName, Integer grade) {
    public List<CourseDto> searchCourses(String subjectId, String subjectName, String professorName, String departmentName, Integer grade) {
        // 모든 조건이 null일 경우 전체 목록을 반환하거나, 빈 목록을 반환하는 로직을 구현할 수 있음.
        return courseRepository.searchCourses(subjectId, subjectName, professorName, departmentName, grade);
    }
}
