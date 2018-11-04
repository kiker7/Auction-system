package pl.rutynar.auctionsystem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.rutynar.auctionsystem.data.domain.Auction;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.repository.AuctionRepository;
import pl.rutynar.auctionsystem.service.impl.AuctionServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class AuctionTest {

    @Mock
    AuctionRepository auctionRepositoryMock;

    @InjectMocks
    AuctionServiceImpl auctionService;

    private User owner;
    private User follower;
    private User visitor;

    private Auction auction;

    @Before
    public void setUp(){
        owner = new User();
        owner.setLogin("owner");
        follower = new User();
        follower.setLogin("follower");
        visitor = new User();
        visitor.setLogin("visitor");
        auction = new Auction();

        Set<User> followers = new HashSet<>();
        List<Auction> auctions = new ArrayList<>();

        auction.setFollowers(followers);
        auctions.add(auction);

        auction.registerObserver(follower);
        auction.registerObserver(owner);
        auction.setUser(owner);
        owner.setAuctions(auctions);
    }

    @Test
    public void checkOwnerPermissionToFollow(){
        assertEquals("owner", auctionService.checkUserPermissionToFollow(owner, auction));
    }

    @Test
    public void checkFollowerPermissionToFollow(){
        assertEquals("follower", auctionService.checkUserPermissionToFollow(follower, auction));
    }

    @Test
    public void ckeckVisitorPermissionToFollow(){
        assertEquals("visitor", auctionService.checkUserPermissionToFollow(visitor, auction));
    }
}
