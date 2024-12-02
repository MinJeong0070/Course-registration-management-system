package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
        import lombok.*;
        import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class courseWaitlistId implements java.io.Serializable { // 수강대기의 복합 기본키 설정
    @Column(name = "학번", length = 9)
    private String studentId;

    @Column(name = "강좌번호")
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        courseWaitlistId waitlistId = (courseWaitlistId) o;
        return studentId.equals(waitlistId.studentId) && courseId.equals(waitlistId.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}