package pl.rutynar.auctionsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Bid;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.dto.BidDTO;
import pl.rutynar.auctionsystem.exception.AuctionNotFoundException;
import pl.rutynar.auctionsystem.repository.AuctionRepository;
import pl.rutynar.auctionsystem.repository.BidRepository;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.service.UserService;

import java.time.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Override
    public Auction getAuctionById(long id) {

        return auctionRepository.findOneById(id).orElseThrow(() -> new AuctionNotFoundException(id));
    }

    @Override
    public void createAuctionFromGame(Game game) {

        User owner = userService.getCurrentUser();

        Auction auction = new Auction();
        auction.setUser(owner);
        auction.setGame(game);
        auction.setFinished(false);
        auction.setClosingTime(new Date());
        // Owner also follows auction
        Set<User> followers = new HashSet<>();
        auction.setFollowers(followers);
        auction.registerObserver(owner);

        auctionRepository.save(auction);
    }

    @Override
    public Page<Auction> getAllAuctions(Pageable pageable) {
        return auctionRepository.findAllByAndFinishedIsFalse(pageable);
    }

    @Override
    public String calculateTimeLeftToCloseAuction(Auction auction) {

        Instant instant = Instant.ofEpochMilli(auction.getClosingTime().getTime());
        LocalDateTime closingTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        Period period = Period.between(LocalDate.now(), closingTime.toLocalDate());
//        Duration duration = Duration.between(LocalTime.now(), closingTime.toLocalTime());

        return period.getDays() + " dni";
    }

    @Override
    public String checkUserPermissionToFollow(User user, Auction auction) {

        if (user.equals(auction.getUser())) {
            return "owner";
        } else {
            User follower = auction.getFollowers().stream()
                    .filter(user::equals)
                    .findAny()
                    .orElse(null);

            return follower == null ? "visitor" : "follower";
        }
    }

    @Override
    public void addFollower(User user, Auction auction) {
        auction.registerObserver(user);
        auctionRepository.save(auction);
    }

    @Override
    public void removeFollower(User user, Auction auction) {
        auction.removeObserver(user);
        auctionRepository.save(auction);
    }

    @Override
    public void closeAuction(Auction auction) {
        auction.setFinished(true);

        auction.notifyObservers();

        auctionRepository.save(auction);
    }

    @Override
    public void processNewBid(Auction auction, BidDTO bidDTO) {
        Bid newBid = new Bid();
        newBid.setOffer(bidDTO.getOffer());
        newBid.setAuction(auction);
        newBid.setUser(userService.getCurrentUser());
        newBid.setRequestTime(new Date());
        bidRepository.save(newBid);
    }
}
