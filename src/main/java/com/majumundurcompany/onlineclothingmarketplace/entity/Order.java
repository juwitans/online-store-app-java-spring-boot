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
@Table(name = "t_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;
    @Column(name = "trans_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;
    private String address;
    @Column(name = "payment_status")
    private Boolean paymentStatus;
    @OneToOne
    private OrderStatus status;
    @OneToMany(mappedBy = "order")
    @JsonManagedReference
    private List<OrderDetail> orderDetails;
}
