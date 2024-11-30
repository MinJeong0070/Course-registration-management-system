package org.example.deu_courseregistration.controller;

import org.example.deu_courseregistration.dto.CourseDto;
import org.example.deu_courseregistration.service.CourseCartService;
import org.example.deu_courseregistration.service.CourseRegistrationService;
import org.example.deu_courseregistration.service.CourseSearchService;
import org.example.deu_courseregistration.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class CourseController {
    private final CourseService courseService;
    private final CourseCartService courseCartService;
    private final CourseSearchService courseSearchService;
    private final CourseRegistrationService courseRegistrationService;

    @Autowired
    public CourseController(CourseService courseService, CourseCartService courseCartService, CourseSearchService courseSearchService, CourseRegistrationService courseRegistrationService) {
        this.courseService = courseService;
        this.courseCartService = courseCartService;
        this.courseSearchService = courseSearchService;
        this.courseRegistrationService = courseRegistrationService;
    }

    // 전체 강좌 데이터 처리 메서드(수강신청, 장바구니 페이지에서 사용)
    private void addCommonAttributes(Model model, String page) {
        List<CourseDto> courses = courseService.getAllCustomCourseDetails();
        courses.forEach(course -> {
            if (course.getDepartmentName() == null) {
                course.setDepartmentName("N/A");
            }
        });

        model.addAttribute("page", page); // 현재 페이지 정보 추가
        model.addAttribute("courses", courses); // 강좌 데이터 추가
    }

    // 공통 검색 조건 설정
    private void addSearchAttributes(String subjectId, String subjectName, String professorName, String departmentName, Integer grade, String search, Model model) {
        // 입력값이 숫자면 교과목번호로, 한글이면 교수이름으로 검색 조건 추가
        if (search != null && !search.isEmpty()) {
            if (search.matches("\\d+")) {
                subjectId = search;
            } else {
                professorName = search;
            }
        }

        // 검색 조건에 맞는 강좌 목록 조회
        List<CourseDto> courses = courseSearchService.searchCourses(subjectId, subjectName, professorName, departmentName, grade);

        // 조회 결과를 모델에 추가하여 뷰에 전달
        model.addAttribute("courses", courses);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("subjectName", subjectName);
        model.addAttribute("professorName", professorName);
        model.addAttribute("departmentName", departmentName);
        model.addAttribute("grade", grade);
        model.addAttribute("search", search);
    }

    // 공통 데이터 추가 메서드
    private String handleCourseAction(
            String studentId,
            Long courseId,
            String returnPage,
            String subjectId,
            String subjectName,
            String professorName,
            String departmentName,
            Integer grade,
            String search,
            RedirectAttributes redirectAttributes,
            Model model,
            String actionType
    ) {
        String resultMessage;
        if ("cart".equals(actionType)) {
            resultMessage = courseCartService.addToCart(studentId, courseId);
        } else if ("registration".equals(actionType)) {
            resultMessage = courseRegistrationService.addToCourseRegistration(studentId, courseId);
        } else {
            throw new IllegalArgumentException("Invalid action type: " + actionType);
        }

        // 검색 조건에 맞는 강좌 목록 조회
        addSearchAttributes(subjectId, subjectName, professorName, departmentName, grade, search, model);

        // 결과 메시지를 리다이렉트하여 전달
        redirectAttributes.addFlashAttribute("message", resultMessage);
        redirectAttributes.addFlashAttribute("subjectId", subjectId);
        redirectAttributes.addFlashAttribute("subjectName", subjectName);
        redirectAttributes.addFlashAttribute("professorName", professorName);
        redirectAttributes.addFlashAttribute("departmentName", departmentName);
        redirectAttributes.addFlashAttribute("grade", grade);
        redirectAttributes.addFlashAttribute("search", search);

        // 페이지로 리다이렉트
        return "redirect:/" + returnPage;
    }

    @GetMapping("/")
    public String index(Model model) {
        addCommonAttributes(model, "enrollment");
        return "index";
    }

    @GetMapping("/CourseCart")
    public String courseCart(Model model) {
        addCommonAttributes(model, "CourseCart");
        return "CourseCart";
    }

    @GetMapping("/CourseCartStatus")
    public String courseCartStatus(@RequestParam String studentId, Model model) {
        // 특정 학생의 장바구니 데이터를 가져옴
        List<CourseDto> coursesInCart = courseCartService.getCoursesInCartByStudentId(studentId);

        coursesInCart.forEach(course -> {
            if (course.getDepartmentName() == null) {
                course.setDepartmentName("N/A");
            }
        });

        model.addAttribute("page", "CourseCartStatus");
        model.addAttribute("courses", coursesInCart);

        return "CourseCartStatus";
    }

    // 강좌 검색 페이지 요청 처리
    @GetMapping("/searchCourses")
    public String getCourses(
            @RequestParam(required = false) String subjectId,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String professorName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "index") String returnPage,
            Model model
    ) {
        addSearchAttributes(subjectId, subjectName, professorName, departmentName, grade, search, model);
        return returnPage;
    }

    // 장바구니 담기
    @PostMapping("/addToCart")
    public String addToCart(
            @RequestParam("studentId") String studentId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("returnPage") String returnPage,
            @RequestParam(required = false) String subjectId,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String professorName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) String search,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        return handleCourseAction(studentId, courseId, returnPage, subjectId, subjectName, professorName, departmentName, grade, search, redirectAttributes, model, "cart");
    }

    // 수강신청
    @PostMapping("/addToCourseRegistration")
    public String addToCourseRegistration(
            @RequestParam("studentId") String studentId,
            @RequestParam("courseId") Long courseId,
            @RequestParam("returnPage") String returnPage,
            @RequestParam(required = false) String subjectId,
            @RequestParam(required = false) String subjectName,
            @RequestParam(required = false) String professorName,
            @RequestParam(required = false) String departmentName,
            @RequestParam(required = false) Integer grade,
            @RequestParam(required = false) String search,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // 수강신청 가능 여부 확인
        boolean isRegistrationPeriodValid = courseRegistrationService.isCourseRegistrationPeriodValid();

        // 수강신청이 불가능한 경우
        if (!isRegistrationPeriodValid) {
            redirectAttributes.addFlashAttribute("message", "현재 수강신청 기간이 아닙니다.");
            return "redirect:/";  // 또는 수강신청 페이지로 리디렉션
        }

        return handleCourseAction(studentId, courseId, returnPage, subjectId, subjectName, professorName, departmentName, grade, search, redirectAttributes, model, "registration");
    }

}
