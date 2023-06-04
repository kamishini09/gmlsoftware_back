package com.example.alianza.Domain.Model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private String sharedKey;
    private String name;
    private Long phone;
    private String email;
    private LocalDate startDate;
    private LocalDate endDate;
}
