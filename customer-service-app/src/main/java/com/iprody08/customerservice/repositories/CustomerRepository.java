package com.iprody08.customerservice.repositories;

import com.iprody08.customerservice.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    List<Customer> findByName(String email);
    Customer findBySurname(String surname);

    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.contactDetails d
            WHERE c.id = d.customer.id
            AND d.email = :email
            """)
    Optional<Customer> findByContactDetailsEmail(String email);

    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.contactDetails d
            WHERE c.id = d.customer.id
            AND d.telegramId = :telegramId
            """)
    Optional<Customer> findByContactsDetailsTelegramId(String telegramId);

    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.contactDetails
            JOIN FETCH c.country
            WHERE c.name = :name
            """)
    List<Customer> findCustomersByName(String name);

    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.contactDetails
            JOIN FETCH c.country
            WHERE c.surname = :surname
            """)
    List<Customer> findCustomersBySurname(String surname);

    @Override
    @Query("""
            SELECT c FROM Customer c
            JOIN FETCH c.contactDetails
            JOIN FETCH c.country
            """)
    List<Customer> findAll();

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM Customer c
        JOIN c.contactDetails cd
        WHERE cd.telegramId = :telegramId
        """)
    boolean existsByTelegramId(String telegramId);

    @Query("""
        SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END
        FROM Customer c
        JOIN c.contactDetails cd
        WHERE cd.email = :email
        """)
    boolean existByEmail(String email);
}
