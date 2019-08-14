package com.avid.web.player.v1.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlayerDto {

    String email;

    /*public static PlayerDTO of(Player player) {
        PlayerDTO dto = new PlayerDTO();

        dto.setEmail(player.getEmail());

        return dto;
    }*/
}
