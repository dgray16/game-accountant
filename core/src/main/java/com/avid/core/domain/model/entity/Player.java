package com.avid.core.domain.model.entity;

import com.avid.core.domain.model.base.AbstractVersional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * Typical user of application.
 */
@Getter
@Setter
@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString(of = StringUtils.EMPTY, callSuper = true)
@Table(
        name = "admins",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "email")
        },
        indexes = @Index(columnList = "email")
)
public class Player extends AbstractVersional {

    String email;

}
