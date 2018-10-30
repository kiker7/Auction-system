package pl.rutynar.auctionsystem;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import pl.rutynar.auctionsystem.data.domain.User;
import pl.rutynar.auctionsystem.repository.UserRepository;
import pl.rutynar.auctionsystem.service.impl.UserServiceImpl;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

    @Mock
    UserRepository userRepositoryMock;

    @InjectMocks
    UserServiceImpl service;

    @Test
    public void findUserByLogin(){

        when(userRepositoryMock.findOneByLogin("uala")).thenReturn(Optional.of(new User()));

        assertNotNull(service.getUserByLogin("uala"));
    }
}
