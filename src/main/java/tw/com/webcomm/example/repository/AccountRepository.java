package tw.com.webcomm.example.repository;

import java.util.Set;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import tw.com.webcomm.example.entity.Account;
import tw.com.webcomm.example.entity.Role;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByEmail(String email);
	
	Account findByEmailAndActive(String email, Boolean active);

    @Query("select a from Account a where a.email = :email")
    Stream<Account> findByEmailReturnStream(@Param("email") String email);
   
    @Modifying
    @Transactional
    @Query(value = "update Account a set a.balance = :balance where a.email = :email", nativeQuery = true)
    void updateBalanceByEmail(@Param("balance") Long balance, @Param("email") String email);
    
    void deleteByEmail(String email);
    
    @Modifying
    @Transactional
    @Query(value = "delete from ACCOUNT_ROLE ar where ar.ACCOUNT_ID in (select a.ID from ACCOUNT a where a.EMAIL=?)", nativeQuery = true)
    void deleteAccountRole(String email);
    
//    Page<Account> findByRoles(Set<Role> roles, Pageable pageable);
    
//    Page<Account> findByRolesAndActive(Set<Role> roles, Pageable pageable, Boolean active);
    
    @Modifying
    @Transactional
    @Query(value = "update Account a set a.active = :active where a.email = :email", nativeQuery = true)
    void updateActiveByEmail(@Param("active") Boolean active, @Param("email") String email);
    
}
