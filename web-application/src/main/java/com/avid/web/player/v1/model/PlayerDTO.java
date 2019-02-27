package com.avid.web.player.v1.model;

import com.avid.core.domain.model.entity.Player;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerDTO {

    String email;

    public static PlayerDTO of(Player player) {
        PlayerDTO dto = new PlayerDTO();

        dto.setEmail(player.getEmail());

        return dto;
    }
}
