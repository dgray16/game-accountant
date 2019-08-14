package com.avid.web.system.config.web.security;

import com.avid.core.domain.service.PlayerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {

    PlayerService playerService;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return playerService.findByEmail(username).map(SystemUser::of);
    }

}
