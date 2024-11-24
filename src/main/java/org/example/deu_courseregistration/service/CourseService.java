package org.example.deu_courseregistration.service;

import org.example.deu_courseregistration.dto.courseDto;
import org.example.deu_courseregistration.repository.courseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CourseService {
    private final courseRepository courseRepository;

    @Autowired
    public CourseService(courseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<courseDto> getAllCustomCourseDetails() {
        return courseRepository.findCustomCourseDetails();
    }
}
