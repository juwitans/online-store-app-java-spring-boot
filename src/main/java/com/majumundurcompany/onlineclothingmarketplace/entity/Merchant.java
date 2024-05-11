package com.majumundurcompany.onlineclothingmarketplace.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_merchant")
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "store_name")
    private String storeName;
    private String address;
    private String description;
    private Boolean status;
    private Double income;
    @OneToMany(mappedBy = "merchant")
    @JsonManagedReference
    private List<BankAccount> accounts;
}
