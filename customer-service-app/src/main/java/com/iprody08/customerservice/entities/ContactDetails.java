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
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "contact_details")
public class ContactDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "contact_details_seq")
    @SequenceGenerator(name = "contact_details_seq", sequenceName = "contact_details_seq", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "contactDetails")
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column(nullable = false)
    private String email;

    @Column(name = "telegram_id", nullable = false)
    private String telegramId;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

}
