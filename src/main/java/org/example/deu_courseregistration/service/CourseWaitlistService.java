package org.example.deu_courseregistration.service;

import jakarta.transaction.Transactional;
import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.entity.courseWaitlistId;
import org.example.deu_courseregistration.repository.CourseWaitlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseWaitlistService {
    @Autowired
    private CourseWaitlistRepository courseWaitlistRepository;

    // 특정 학생의 수강신청 된 강좌 정보를 가져옴
    public List<CourseDto> getCoursesInWaitlistByStudentId(String studentId) {
        return courseWaitlistRepository.findCoursesInWaitlistByStudentId(studentId);
    }

    // 수강대기 취소
    @Transactional
    public void deleteCourseWaitlist(courseWaitlistId id) {
        // CourseWaitlist 삭제
        courseWaitlistRepository.deleteById(id);
    }
}