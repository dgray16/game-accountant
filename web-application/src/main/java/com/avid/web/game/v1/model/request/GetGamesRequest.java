package com.avid.web.game.v1.model.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GetGamesRequest {

    String query;

    public boolean isQueryPresent() {
        return StringUtils.isNotBlank(query);
    }

}
