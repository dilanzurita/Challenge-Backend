package com.challengeBP.ClientAccount.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String type;
    private Double initialBalance;
    private Boolean status;
    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;
}
