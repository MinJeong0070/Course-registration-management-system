package org.example.deu_courseregistration.service;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.entity.*;
import org.example.deu_courseregistration.exception.CustomEnrollmentException;
import org.example.deu_courseregistration.repository.CourseRegistrationRepository;
import org.example.deu_courseregistration.repository.CourseRepository;
import org.example.deu_courseregistration.repository.StudentRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.GenericJDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseRegistrationService {
    @Autowired
    private CourseRegistrationRepository courseRegistrationRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public String addToCourseRegistration(String studentId, Long courseId) {
        try {
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

            // 이미 수강신청목록에 있는지 확인
            courseRegistrationId courseRegistrationId = new courseRegistrationId(student.getStudentId(), course.getCourseId());
            if (courseRegistrationRepository.existsById(courseRegistrationId)) {
                return "이미 수강 신청한 강좌입니다.";
            }

            // 수강신청에 추가
            CourseRegistration courseRegistration = new CourseRegistration();
            courseRegistration.setId(courseRegistrationId);
            courseRegistration.setStudentId(student);
            courseRegistration.setCourseId(course);

            courseRegistrationRepository.save(courseRegistration);

            // 즉시 데이터베이스에 반영하여 SQL 오류 강제 발생
            entityManager.flush();

            System.out.println("수강신청 완료");
            return "수강신청이 성공적으로 완료되었습니다.";
        }
        catch (GenericJDBCException e) {
            System.out.println("GenericJDBCException 발생: " + e.getMessage());
            Throwable rootCause = e.getCause();
            String errorMessage = parseOracleErrorMessage(rootCause);
            System.out.println("Parsed Error Message: [" + errorMessage + "]");

            // 문자열 정규화 및 조건 확인
            String normalizedErrorMessage = errorMessage.replace("\n", "").replace("\r", "").trim();
            if (normalizedErrorMessage.contains("정원이 초과")) {
                throw new CustomEnrollmentException(normalizedErrorMessage); // CustomEnrollmentException 던지기
            }
            throw new RuntimeException("수강신청 처리 중 오류가 발생했습니다.");
        } catch (ConstraintViolationException e) {
            System.out.println("ConstraintViolationException 발생: " + e.getMessage());
            Throwable rootCause = e.getCause();
            if (rootCause instanceof SQLIntegrityConstraintViolationException) {
                String errorMessage = parseOracleErrorMessage(rootCause);
                System.out.println("Parsed Error Message: [" + errorMessage + "]");

                // 문자열 정규화 및 조건 확인
                String normalizedErrorMessage = errorMessage.replace("\n", "").replace("\r", "").trim();
                if (normalizedErrorMessage.contains("이미 대기")) {
                    throw new CustomEnrollmentException(normalizedErrorMessage); // CustomEnrollmentException 던지기
                }
                throw new RuntimeException("수강신청 처리 중 오류가 발생했습니다.");
            }
            throw new RuntimeException("ConstraintViolation 처리 중 오류가 발생했습니다.");
        }
        catch (Exception e) {
            System.out.println("Exception 발생: " + e.getClass().getName());
            if (e.getCause() != null) {
                System.out.println("Root Cause: " + e.getCause().getClass().getName());
                System.out.println("Root Cause Message: " + e.getCause().getMessage());
            }
            throw new RuntimeException("수강신청 처리 중 오류가 발생했습니다.");
        }
    }

    // 수강신청 기간 체크
    public boolean isCourseRegistrationPeriodValid() {
        // 저장 프로시저를 호출하여 수강신청 기간이 유효한지 확인
        Integer isValid = courseRegistrationRepository.checkCourseRegistrationPeriod();

        // 반환 값이 1이면 유효한 수강신청 기간, 0이면 아닌 경우
        return isValid != null && isValid == 1;
    }

    // 특정 학생의 수강신청 된 강좌 정보를 가져옴
    public List<CourseDto> getCoursesInRegistrationByStudentId(String studentId) {
        return courseRegistrationRepository.findCoursesInRegistrationByStudentId(studentId);
    }

    // 수강신청 취소
    @Transactional
    public void deleteCourseRegistration(courseRegistrationId id) {
        // CourseRegistration 삭제
        courseRegistrationRepository.deleteById(id);
    }

    // 정원이 꽉찬 강좌 수강신청시 예외처리
    private String parseOracleErrorMessage(Throwable rootCause) {
        if (rootCause != null) {
            String message = rootCause.getMessage();
            System.out.println("parseOracleErrorMessage로 넘어온 Message: " + message); // 확인용 로그

            // 다중 줄 메시지 파싱
            String[] lines = message.split("\n");
            for (String line : lines) {
                if (line.contains("ERROR_CODE:FULL_CAPACITY")) {
                    return "정원이 초과되어 대기자로 등록되었습니다.";
                } else if (line.contains("무결성 제약 조건")) {
                    return "이미 대기중인 강좌입니다.";
                }
            }
        }
        return "알 수 없는 오류가 발생했습니다.(parseOracleErrorMessage)";
    }
}