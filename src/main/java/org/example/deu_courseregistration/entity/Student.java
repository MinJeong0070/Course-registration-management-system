package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "학생") // Oracle 테이블 이름 매핑
@NoArgsConstructor
public class Student {
    @Id
    @Column(name = "학번", length = 9)
    private String studentId;

    @Column(name = "생년월일", nullable = false)
    private LocalDate birthDate;

    @Column(name = "이름", nullable = false, length = 10)
    private String name;

    @ManyToOne
    @JoinColumn(name = "학과번호", referencedColumnName = "학과번호")
    private Department department;

    @Column(name = "전화번호", length = 12)
    private String phoneNumber;

    @Column(name = "학년")
    private Integer grade;

}