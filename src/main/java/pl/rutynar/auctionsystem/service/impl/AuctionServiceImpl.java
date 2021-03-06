package pl.rutynar.auctionsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import pl.rutynar.auctionsystem.data.domain.*;
import pl.rutynar.auctionsystem.dto.BidDTO;
import pl.rutynar.auctionsystem.exception.AuctionNotFoundException;
import pl.rutynar.auctionsystem.repository.AuctionRepository;
import pl.rutynar.auctionsystem.repository.BidRepository;
import pl.rutynar.auctionsystem.repository.NotificationRepository;
import pl.rutynar.auctionsystem.repository.UserRepository;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.service.UserService;

import java.time.*;
import java.util.*;
import java.util.stream.Stream;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private NotificationRepository notificationRepository;

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
        this.notifyObservers(auction, Event.FINISHED, null);
        auctionRepository.save(auction);
    }

    @Override
    public void processNewBid(Auction auction, BidDTO bidDTO) {
        // Create bid
        Bid newBid = new Bid();
        User user = userService.getCurrentUser();
        newBid.setOffer(bidDTO.getOffer());
        newBid.setAuction(auction);
        newBid.setUser(user);
        newBid.setRequestTime(new Date());
        bidRepository.save(newBid);
        // Notifications
        auction.submitNewBid(newBid, Event.BID);
        this.notifyObservers(auction, Event.BID, newBid);
    }

    @Override
    public void notifyObservers(Auction auction, Event eventType, Bid bid) {
        List<Notification> notifications = new ArrayList<>();
        auction.getFollowers().forEach(follower -> {
            Notification notification = new Notification();
            notification.setEventType(eventType);
            notification.setRecipient(follower);
            switch (eventType) {
                case FINISHED:
                    notification.setMessage("Aukcja gry: " + auction.getGame().getName() + " została zakończona.");
                    break;
                case BID:
                    notification.setMessage("Użytkownik: " + bid.getUser().getLogin().toUpperCase() + " złożył ofertę: " + bid.getOffer() + " zł za grę: " + auction.getGame().getName());
                    break;
                case AUCTION_WIN:
                    notification.setMessage("Użytkownik: " + auction.getGame().getOwnerName().toUpperCase() + " wygrał aukcję gry: " + auction.getGame().getName() + "!");
                    break;
            }
            notifications.add(notification);
        });
        notificationRepository.save(notifications);
    }

    @Override
    public List<Bid> getBidsForAuction(Auction auction) {
        return bidRepository.findAllByAuctionOrderByRequestTimeDesc(auction);
    }
}
