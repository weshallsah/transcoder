package com.weshallsah.transcoder.services;

import com.weshallsah.transcoder.Repo.userRepo;
import com.weshallsah.transcoder.model.Users;
import com.weshallsah.transcoder.model.UsersDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class userServices implements UserDetailsService {
    @Autowired
    private userRepo repo;

    @Autowired
    private JWTservices jwTservices;

    @Autowired
    @Lazy
    AuthenticationManager manager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repo.findByUsername(username);
        if (user == null) {
            System.out.println("user not found");
            throw new UsernameNotFoundException("user not found");
        }
        return new UsersDetails(user);
    }

    public Users register(Users users) {
        users.setPassword(bCryptPasswordEncoder.encode(users.getPassword()));

        return repo.save(users);

    }

    public String verify(Users users) {
        Authentication authentication = manager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                users.getUsername(),
                                users.getPassword()
                        )
                );
        if (authentication.isAuthenticated()){
            return jwTservices.generateToken(users.getUsername());
        }
        return "fail to login";
    }
}
