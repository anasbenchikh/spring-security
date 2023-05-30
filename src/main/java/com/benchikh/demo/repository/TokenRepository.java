package com.benchikh.demo.repository;

import com.benchikh.demo.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {

    @Query(value = """
            SELECT t FROM token t inner join customer c on t.customer_id = c.id WHERE c.id = :customerId AND (t.expired = false or t.revoked = false)
            """, nativeQuery = true)
    List<Token> findAllValidTokenByCustomer(Integer customerId);

    Optional<Token> findByToken(String token);
}
