package com.sachet.authserveramazonjava.model.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
public class AddressRead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "line_one")
    private String lineOne;
    @Column(name = "line_two")
    private String lineTwo;
    @Column(name = "postal_code")
    private int postalCode;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserRead user;
    @Column(name = "primary_address")
    private String primaryAddress;

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", lineOne='" + lineOne + '\'' +
                ", lineTwo='" + lineTwo + '\'' +
                ", postalCode=" + postalCode +
                '}';
    }
}
