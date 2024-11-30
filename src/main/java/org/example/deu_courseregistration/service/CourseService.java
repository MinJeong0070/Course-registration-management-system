package org.example.deu_courseregistration.service;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class CourseService {
    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<CourseDto> getAllCustomCourseDetails() {
        return courseRepository.findCustomCourseDetails();
    }
}
