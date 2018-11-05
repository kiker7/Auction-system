package pl.rutynar.auctionsystem.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.dto.BidDTO;
import pl.rutynar.auctionsystem.service.AuctionService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class SetBidFormValidator implements Validator {

    @Autowired
    private AuctionService auctionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return BidDTO.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        BidDTO bidDTO = (BidDTO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"offer", "NotEmpty");
        ValidationUtils.rejectIfEmpty(errors, "auctionId", "NotEmpty");

        if(errors.hasErrors()){
            errors.rejectValue("offer", "Reqex.bidDTO.requestTime");
        }

        int offer = bidDTO.getOffer();
        Auction auction = auctionService.getAuctionById(bidDTO.getAuctionId());
        int price = auction.getGame().getPrice();
        // Date format
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        String closingTime = auction.getClosingTime().toString();
        String now = dateFormat.format(new Date());

        if(offer < price){
            errors.rejectValue("offer", "Size.bidDTO.offer");
        }

        if(now.compareTo(closingTime) > 0){
            errors.rejectValue("offer", "Date.bidDTO.offer");
        }
    }
}
