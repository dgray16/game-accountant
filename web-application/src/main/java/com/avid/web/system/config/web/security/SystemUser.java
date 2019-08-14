package com.avid.web.system.config.web.security;

import com.avid.core.domain.model.entity.Player;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class SystemUser extends User {

    public static SystemUser of(Player player) {
        return new SystemUser(player.getEmail(), StringUtils.EMPTY, "NOT_IMPLEMENTED");
    }

    private SystemUser(String username, String password, String role) {
        super(username, password, AuthorityUtils.createAuthorityList(role));
    }

}
