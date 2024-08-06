package io.github.tiagodesouza.goldenblood.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Address {
    private String postalCode;
    private String street;
    private Integer number;
    private String neighborhood;
    private String city;
    private String state;
}
