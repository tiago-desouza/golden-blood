package io.github.tiagodesouza.goldenblood.models;

import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class Contact {
    private String email;
    private String landline;
    private String mobile;
}
