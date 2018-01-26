package pl.rutynar.auctionsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateUserFormDTO {

    @NotBlank
    private String login = "";
    @Email
    private String email = "";
    @NotBlank
    private String firstName = "";
    @NotBlank
    private String lastName = "";
    @NotBlank
    private String password = "";
    @NotBlank
    private String passwordRepeated = "";
}
