package com.iprody08.customerservice.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.Pattern;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_details")
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_details_seq")
    @SequenceGenerator(name = "contact_details_seq", sequenceName = "contact_details_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    @Pattern(regexp = "^(.+)@(\\\\S+)$", message = "email must match pattern user@domain.com")
    private String email;

    @Column(name = "telegram_id", nullable = false)
    @Pattern(regexp = "^@[a-zA-Z0-9]+$")
    private String telegramId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
