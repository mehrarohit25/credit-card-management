package com.publicis.sapient.creditcard.management.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "T_CREDIT_CARD")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@EqualsAndHashCode
@Setter
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "card_name", nullable = false)
    private String name;

    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;

    @Column(name = "card_limit", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private BigDecimal limit;

    @Column(name = "balance", nullable = false, columnDefinition = "DECIMAL(10,2)")
    @Builder.Default
    private BigDecimal balance = BigDecimal.valueOf(0.0);

    @Column(name = "currency", nullable = false)
    @Builder.Default
    private String currency= "GBP";
}
