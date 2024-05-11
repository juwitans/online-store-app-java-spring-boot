package com.majumundurcompany.onlineclothingmarketplace.entity;

import com.majumundurcompany.onlineclothingmarketplace.constant.EOrderStatus;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "m_status")
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Enumerated(EnumType.STRING)
    private EOrderStatus status;
}
