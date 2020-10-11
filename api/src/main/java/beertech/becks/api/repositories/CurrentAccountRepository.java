package beertech.becks.api.repositories;

import beertech.becks.api.entities.CurrentAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CurrentAccountRepository extends JpaRepository<CurrentAccount, Long> {

    @Query(value = "SELECT * FROM CURRENT_ACCOUNT WHERE HASH_VALUE = ?1", nativeQuery = true)
    CurrentAccount findByHashValue(String hashValue);
}
