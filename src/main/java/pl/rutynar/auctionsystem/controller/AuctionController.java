package pl.rutynar.auctionsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuctionController {

    @GetMapping("/auctions")
    public String auctions(){
        return "auctions";
    }
}
