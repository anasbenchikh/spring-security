package com.benchikh.demo.token;


import com.benchikh.demo.model.Customer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Token {

    @Id
    @GeneratedValue
    private Integer id;

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private String token;

    private boolean revoked;

    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
}
