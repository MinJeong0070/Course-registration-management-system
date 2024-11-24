package org.example.deu_courseregistration.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name = "교수")
@NoArgsConstructor
public class Professor {
    @Id
    @Column(name = "교수번호", length = 7)
    private String professorId;

    @Column(name = "생년월일", nullable = false) // DATE 매핑
    private java.time.LocalDate birthDate;

    @Column(name = "이름", nullable = false, length = 12)
    private String professorName;

    @ManyToOne
    @JoinColumn(name = "학과번호", referencedColumnName = "학과번호") // 외래 키 매핑
    private Department department;

    @Column(name = "전화번호", length = 12)
    private String phoneNumber;
}