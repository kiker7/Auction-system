package pl.rutynar.auctionsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.Game;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.exception.AuctionNotFoundException;
import pl.rutynar.auctionsystem.repository.AuctionRepository;
import pl.rutynar.auctionsystem.service.AuctionService;
import pl.rutynar.auctionsystem.service.UserService;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private UserService userService;

    @Autowired
    private AuctionRepository auctionRepository;

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
        followers.add(owner);
        auction.setFollowers(followers);

        auctionRepository.save(auction);
    }

    @Override
    public Page<Auction> getAllAuctions(Pageable pageable) {
        return auctionRepository.findAllByOrderByClosingTimeDesc(pageable);
    }
}
