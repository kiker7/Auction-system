package pl.rutynar.auctionsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class CreateGameFormDTO {

    @NotBlank
    private String name = "";
    @NotBlank
    private int price;
}
