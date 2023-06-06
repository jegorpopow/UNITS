package com.hse.units.repos;

import com.hse.units.token.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Long> {
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.uid = u.uid\s
      where u.uid = :id and (t.expired = 0 or t.revoked = 0)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    Optional<Token> findByToken(String token);
}
