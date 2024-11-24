package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "학과") // 테이블 이름 매핑
@NoArgsConstructor
public class Department {
    @Id
    @Column(name = "학과번호") // PRIMARY KEY
    private Integer departmentId; // NUMBER(5)에 대응되는 필드

    @Column(name = "학과명", nullable = false, length = 30)
    private String departmentName;

    @Column(name = "전화번호", length = 12)
    private String phoneNumber;

    // 학과와 교수와의 1:N 관계
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Professor> professors;

    // 학과와 교과목의 1:N 관계
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL)
    private List<Subject> subjects;
}