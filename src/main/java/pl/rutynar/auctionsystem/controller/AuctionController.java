package pl.rutynar.auctionsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.wrapper.PageWrapper;

import java.util.Optional;

@Controller
public class AuctionController {

    private static final int BUTTONS_TO_SHOW = 3;
    private static final int INITIAL_PAGE = 0;
    private static final int INITIAL_PAGE_SIZE = 6;
    private static final int[] PAGE_SIZES = {15, 25};

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/auctions")
    public ModelAndView auctions(@RequestParam("pageSize")Optional<Integer> pageSize, @RequestParam("page") Optional<Integer> page) {


        ModelAndView modelAndView = new ModelAndView("auction/auctions");


        int evalPageSize = pageSize.orElse(INITIAL_PAGE_SIZE);
        int evalPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Page<Auction> auctionPage = auctionService.getAllAuctions(new PageRequest(evalPage, evalPageSize));
        PageWrapper pageWrapper = new PageWrapper(auctionPage.getTotalPages(), auctionPage.getNumber(), BUTTONS_TO_SHOW);

        modelAndView.addObject("auctions", auctionPage);
        modelAndView.addObject("selectedPageSize", evalPageSize);
        modelAndView.addObject("pageSizes", PAGE_SIZES);
        modelAndView.addObject("pager", pageWrapper);

        return modelAndView;
    }

    @GetMapping("/auction/{auctionId}")
    public ModelAndView getAuction(@PathVariable long auctionId) {

        ModelAndView modelAndView = new ModelAndView("auction/auction-details");
        modelAndView.addObject("auction", auctionService.getAuctionById(auctionId));
        return modelAndView;
    }
}
