package pl.rutynar.auctionsystem.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

@Data
@NoArgsConstructor
public class BidDTO {

    @NotBlank
    private int offer;

    @NotBlank
    private long auctionId;
}
