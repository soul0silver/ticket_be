package com.event_management.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleName {
    GUEST("GUEST"),
    SPONSOR("SPONSOR"),
    EVENT_OPERATOR("EVENT_OPERATOR"),
    CHECKING_STAFF("CHECKING_STAFF");
    private final String name;
}
