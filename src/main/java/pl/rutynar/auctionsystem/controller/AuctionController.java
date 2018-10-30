package pl.rutynar.auctionsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuctionController {



    @GetMapping("/auctions")
    public String auctions(){

        // print auctions list

        return "auctions";
    }

    @GetMapping("/auction/{auctionId}")
    public String getAuction(){

        // show auction

        return "fragments/auction";
    }


//    @PostMapping("/add-auction")
//    public String createNewAuction(){
//
//
//
//        return "redirect:/auctions";
//    }

}
