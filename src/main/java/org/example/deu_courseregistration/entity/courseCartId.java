package org.example.deu_courseregistration.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class courseCartId implements java.io.Serializable { // 장바구니의 복합 기본키 설정
    @Column(name = "학번", length = 9)
    private String studentId;

    @Column(name = "강좌번호")
    private Long courseId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        courseCartId cartId = (courseCartId) o;
        return studentId.equals(cartId.studentId) && courseId.equals(cartId.courseId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseId);
    }
}
