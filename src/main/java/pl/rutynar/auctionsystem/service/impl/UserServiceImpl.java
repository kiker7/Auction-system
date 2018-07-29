package pl.rutynar.auctionsystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.rutynar.auctionsystem.domain.Library;
import pl.rutynar.auctionsystem.domain.Role;
import pl.rutynar.auctionsystem.domain.User;
import pl.rutynar.auctionsystem.dto.CreateUserFormDTO;
import pl.rutynar.auctionsystem.exception.UserNotFoundException;
import pl.rutynar.auctionsystem.repository.UserRepository;
import pl.rutynar.auctionsystem.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public Optional<User> getUserByLogin(String login) {
        return userRepository.findOneByLogin(login);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public User getCurrentUser() {
        String loggedInUserName = findLoggedInUsername();
        return userRepository.findOneByLogin(loggedInUserName).orElseThrow(() -> new UserNotFoundException(loggedInUserName));
    }

    private String findLoggedInUsername(){
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(userDetails instanceof UserDetails){
            return ((UserDetails) userDetails).getUsername();
        }
        return null;
    }

    @Override
    public User createUserFromForm(CreateUserFormDTO form) {

        // create User
        User user = new User();
        user.setFirstName(form.getFirstName());
        user.setLastName(form.getLastName());
        user.setLogin(form.getLogin());
        user.setEmail(form.getEmail());
        user.setPassword(passwordEncoder.encode(form.getPassword()));
        user.setRole(Role.USER);
        // by default create user library
        Library library = new Library();
        library.setOwner(user);
        user.setLibrary(library);

        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findOneByLogin(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika"));
        Collection<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority("USER"));

        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);

    }
}
