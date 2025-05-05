package com.gabriaum.essencecore.scheduler;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.tpa.TpaRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Iterator;
import java.util.Objects;

/**
 * A scheduler that handles teleportation of players after a specific time delay.
 * This class is responsible for counting down the teleportation time and executing the teleportation once the time reaches zero.
 */
public class TeleportScheduler extends BukkitRunnable {

    /**
     * Runs the teleportation countdown for all accepted teleport requests.
     * If the teleportation time reaches zero, the teleportation is executed.
     * If the sender or target player is no longer valid, the request is removed.
     */
    @Override
    public void run() {
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();
        Iterator<TpaRequest> iterator = EssenceCore.getInstance().getTpaController().getAcceptedRequests().iterator();

        while (iterator.hasNext()) {
            TpaRequest request = iterator.next();
            if (request == null) return;

            Player sender = Bukkit.getPlayer(request.getSenderId());
            if (sender == null) {
                iterator.remove();
                continue;
            }

            // If the teleportation time is zero, execute teleportation
            if (request.getTeleportIn() <= 0) {
                iterator.remove();
                handleTeleportation(request);
                return;
            }

            // Inform the sender about the remaining teleportation time
            sender.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.teleporting"))
                    .replace("{time}", String.valueOf(request.getTeleportIn())));

            // Decrease the teleportation time
            request.setTeleportIn(request.getTeleportIn() - 1);
        }
    }

    /**
     * Handles the teleportation of the sender to the target player once the teleportation time reaches zero.
     * Removes the teleport request from the controller after teleportation is completed.
     *
     * @param request The teleport request that is being handled.
     */
    private void handleTeleportation(TpaRequest request) {
        Player sender = Bukkit.getPlayer(request.getSenderId());
        Player target = Bukkit.getPlayer(request.getTargetId());
        if (sender == null || target == null) return;

        // Teleport the sender to the target player
        sender.teleport(target);
        sender.sendMessage(Objects.requireNonNull(EssenceCore.getInstance().getModifiedConfig()
                        .getString("commands.tpa.teleported"))
                .replace("{player}", target.getName()));

        // Remove the request from the controller after teleportation
        EssenceCore.getInstance().getTpaController().remove(request);
    }
}