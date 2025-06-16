package com.challengeBP.ClientAccount.infraestructure.output.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String type;
    private BigDecimal initialBalance;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private ClientEntity client;
}
