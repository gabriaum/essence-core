package com.gabriaum.essencecore.util.tpa;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class TpaRequest {

    private final UUID senderId;
    private final UUID targetId;
    private boolean accepted = false;
    private int teleportIn = 5;
    private final long timestamp = System.currentTimeMillis() + 30000;

    public boolean isExpired() {
        return System.currentTimeMillis() > timestamp;
    }
}