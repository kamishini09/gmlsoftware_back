package com.example.alianza.infrastructure.H2.Entity;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDate;


@Data
@Entity
@Table(name = "client")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sharedKey;
    private String name;
    private Long phone;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
     

}