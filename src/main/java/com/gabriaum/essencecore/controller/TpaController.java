package com.gabriaum.essencecore.controller;

import com.gabriaum.essencecore.util.tpa.TpaRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * Controller that manages all TPA (Teleportation Request) operations.
 *
 * Extends ArrayList to hold TpaRequest objects and provides methods to send, accept, deny,
 * and manage teleportation requests between players.
 */
public class TpaController extends ArrayList<TpaRequest> {

    /**
     * Sends a teleportation request from one player to another.
     *
     * @param senderId The UUID of the player sending the request.
     * @param targetId The UUID of the player receiving the request.
     */
    public void sendRequest(UUID senderId, UUID targetId) {
        TpaRequest tpaRequest = new TpaRequest(senderId, targetId);
        this.add(tpaRequest);
    }

    /**
     * Retrieves the TPA request between two players.
     *
     * @param senderId The UUID of the sender.
     * @param targetId The UUID of the target.
     * @return The TpaRequest if found, null otherwise.
     */
    public TpaRequest getTpaRequest(UUID senderId, UUID targetId) {
        for (TpaRequest tpaRequest : this) {
            // Skip expired requests
            if (tpaRequest.isExpired()) continue;

            if (tpaRequest.getSenderId().equals(senderId) && tpaRequest.getTargetId().equals(targetId)) {
                return tpaRequest;
            }
        }
        return null;
    }

    /**
     * Accepts a teleportation request between two players.
     *
     * @param senderId The UUID of the player who sent the request.
     * @param targetId The UUID of the player who will accept the request.
     */
    public void acceptRequest(UUID senderId, UUID targetId) {
        TpaRequest tpaRequest = getTpaRequest(senderId, targetId);
        if (tpaRequest != null) {
            tpaRequest.setAccepted(true);
        }
    }

    /**
     * Denies a teleportation request, removing it from the list of active requests.
     *
     * @param senderId The UUID of the player who sent the request.
     * @param targetId The UUID of the player who will deny the request.
     */
    public void denyRequest(UUID senderId, UUID targetId) {
        TpaRequest tpaRequest = getTpaRequest(senderId, targetId);
        if (tpaRequest != null) {
            this.remove(tpaRequest);
        }
    }

    /**
     * Checks if a TPA request exists between two players.
     *
     * @param senderId The UUID of the sender.
     * @param targetId The UUID of the target.
     * @return true if a request exists, false otherwise.
     */
    public boolean hasTpaRequest(UUID senderId, UUID targetId) {
        return getTpaRequest(senderId, targetId) != null;
    }

    /**
     * Checks if a player is currently teleporting.
     *
     * @param uniqueId The UUID of the player to check.
     * @return true if the player is in the process of teleporting, false otherwise.
     */
    public boolean isTeleporting(UUID uniqueId) {
        for (TpaRequest tpaRequest : this) {
            // Skip expired or unaccepted requests
            if (tpaRequest.isExpired() || !tpaRequest.isAccepted()) continue;

            if (tpaRequest.getSenderId().equals(uniqueId)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Removes the teleportation request sent by a specific player.
     *
     * @param senderId The UUID of the sender whose request will be removed.
     */
    public void removeRequest(UUID senderId) {
        for (TpaRequest tpaRequest : this) {
            // Skip expired requests
            if (tpaRequest.isExpired()) continue;

            if (tpaRequest.getSenderId().equals(senderId)) {
                this.remove(tpaRequest);
                break;
            }
        }
    }

    /**
     * Retrieves all accepted TPA requests.
     *
     * @return A collection of accepted TPA requests.
     */
    public Collection<TpaRequest> getAcceptedRequests() {
        Collection<TpaRequest> acceptedRequests = new ArrayList<>();
        for (TpaRequest tpaRequest : this) {
            // Skip expired requests
            if (tpaRequest.isExpired()) continue;

            if (tpaRequest.isAccepted()) {
                acceptedRequests.add(tpaRequest);
            }
        }
        return acceptedRequests;
    }
}