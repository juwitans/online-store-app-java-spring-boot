package com.majumundurcompany.onlineclothingmarketplace.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_bank_account")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    @JsonBackReference
    private Merchant merchant;
    private Long number;
    @Column(name = "bank_name")
    private String bankName;
}
