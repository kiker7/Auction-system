package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Bid;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.dto.BidDTO;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.service.UserService;
import pl.rutynar.auctionsystem.validator.SetBidFormValidator;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private SetBidFormValidator validator;

    // get users
    @GetMapping("/users")
    public List<User> getAllUsers(){

        Page<User> resultPage = userService.getAllUsers(new PageRequest(0, 1000));
        return resultPage.getContent();
    }

    // get auction bids
    @GetMapping("/{id}/bids")
    public List<Bid> getAuctionBids(@PathVariable long id){

        Auction auction = auctionService.getAuctionById(id);
        List<Bid> bids = auctionService.getBidsForAuction(auction);
        Bid largestBid = bids.stream()
                .max(Comparator.comparing(Bid::getOffer))
                .orElse(null);
        bids.remove(largestBid);
        bids.add(0, largestBid);

        return bids;
    }

    // post auction bid
    @PostMapping("/{id}/set-bid")
    public BidDTO setAuctionBid(@PathVariable long id, @RequestBody BidDTO bidDTO, BindingResult bindingResult){

        validator.validate(bidDTO, bindingResult);

        Auction auction = auctionService.getAuctionById(bidDTO.getAuctionId());
        List<Bid> bids = auctionService.getBidsForAuction(auction);
        Bid largestBid = bids.stream()
                .max(Comparator.comparing(Bid::getOffer))
                .orElse(null);
        bids.remove(largestBid);

        auctionService.processNewBid(auction, bidDTO);

        return bidDTO;
    }
}
