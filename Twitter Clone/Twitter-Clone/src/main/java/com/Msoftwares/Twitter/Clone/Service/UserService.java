package com.Msoftwares.Twitter.Clone.Service;

import com.Msoftwares.Twitter.Clone.Model.User;
import com.Msoftwares.Twitter.Clone.Repo.UserRepo;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import java.util.Arrays;

@Service
@Transactional(readOnly = true)
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private final Scheduler dbScheduler;

    public UserService(UserRepo userRepo, Scheduler dbScheduler){
        this.dbScheduler = dbScheduler;
        this.userRepo = userRepo;
    }

    @Transactional(rollbackFor = Exception.class)
    public Mono<User> save(User user){
        return Mono.fromCallable(() ->
                userRepo.save(user)).publishOn(dbScheduler);
    }

    public Mono<User> getUserByScreenName(String screenName){
        return Mono.fromCallable(() ->
                userRepo.findByScreenName(screenName)).publishOn(dbScheduler);
    }

    public Mono<User> findUserById(String id){
        return Mono.fromCallable(() ->
                userRepo.findById(id).get()).publishOn(dbScheduler);
    }

    @Override
    public UserDetails loadUserByUsername(String screenName) throws UsernameNotFoundException {
        User user = userRepo.findByScreenName(screenName);
        if(user == null){
            throw new UsernameNotFoundException(screenName);
        }

        return new org.springframework.security.core.
                userdetails.User(user.getScreenName(), user.getPassword(),
                Arrays.asList(new SimpleGrantedAuthority
                        (user.getRole().toString())));
    }

}
