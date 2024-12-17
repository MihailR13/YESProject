package siit.model;

import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private LocalDate birthDate;
//    private String newField;
}
