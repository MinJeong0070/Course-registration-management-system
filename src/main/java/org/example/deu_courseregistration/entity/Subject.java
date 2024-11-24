package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "교과목")
@Getter
@Setter
@NoArgsConstructor
public class Subject {
    @Id
    @Column(name = "교과목번호", length = 7)
    private String subjectId;

    @Column(name = "교과목명", nullable = false, length = 50)
    private String subjectName;

    @Column(name = "학점수", precision = 3, scale = 2)
    private BigDecimal credits;

    // 교과목과 학과의 N:1 관계
    @ManyToOne
    @JoinColumn(name = "학과번호", referencedColumnName = "학과번호")
    private Department department; // 학과와의 관계
}