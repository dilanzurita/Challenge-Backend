package com.challengeBP.accountMovements.domain.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime date;

    private String type;

    private Double value;

    private Double balance;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
