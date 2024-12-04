package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class courseRegistrationId implements java.io.Serializable { // 수강신청 복합 기본키 설정
    @Column(name = "학번", length = 9)
    private String studentId;

    @Column(name = "강좌번호")
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        courseRegistrationId registrationId = (courseRegistrationId) o;
        return studentId.equals(registrationId.studentId) && courseId.equals(registrationId.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}