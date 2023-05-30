package com.benchikh.demo.services;


import com.benchikh.demo.config.JwtUtil;
import com.benchikh.demo.dto.AuthenticationResponse;
import com.benchikh.demo.model.Customer;
import com.benchikh.demo.repository.CustomerRepository;
import com.benchikh.demo.repository.TokenRepository;
import com.benchikh.demo.token.Token;
import com.benchikh.demo.token.TokenType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    public AuthenticationResponse saveCustomer(Customer request) {
        // customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        var customer = Customer.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        var savedCustomer = customerRepository.save(customer);
        var jwtToken = jwtUtil.generateToken(customer);
        revokeAllToken(customer);
        saveCustomerToken(savedCustomer, jwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void revokeAllToken(Customer customer) {
        var customerValidTokens = tokenRepository.findAllValidTokenByCustomer(customer.getId());
        if (customerValidTokens.isEmpty())
            return;
        customerValidTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(customerValidTokens);
    }
    private void saveCustomerToken(Customer savedCustomer, String jwtToken) {
        var token = Token.builder()
                .customer(savedCustomer)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false).build();
        tokenRepository.save(token);
    }

}
