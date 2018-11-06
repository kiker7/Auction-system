package pl.rutynar.auctionsystem.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Bid;
import pl.rutynar.auctionsystem.data.domain.Event;
import pl.rutynar.auctionsystem.repository.AuctionRepository;
import pl.rutynar.auctionsystem.service.AuctionService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class CloseAuctionsTask {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Transactional
    @Scheduled(cron = "0 1 0 * * ?")
    public void closeAuctions() throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        Date now = dateFormat.parse(dateFormat.format(new Date()));

        try (Stream<Auction> auctionStream = auctionRepository.findAllByAndFinishedIsFalse()) {
            // Find auctions to close
            List<Auction> auctions = auctionStream
                    .filter(auction -> now.compareTo(auction.getClosingTime()) < 0)
                    .peek(e -> {
                        e.setFinished(true);
                        auctionService.notifyObservers(e, Event.FINISHED, null);
                    })
                    .collect(Collectors.toList());
            // Change game owners
            auctions.forEach(auction -> {
                Optional<Bid> winnerBid = auction.getBids().stream()
                        .max(Comparator.comparing(Bid::getOffer));
                winnerBid.ifPresent(bid -> {
                    auction.setUser(bid.getUser());
                    auctionService.notifyObservers(auction, Event.AUCTION_WIN, null);
                });
            });
        }
    }
}
