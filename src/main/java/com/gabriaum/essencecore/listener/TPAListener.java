package com.gabriaum.essencecore.listener;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.controller.TpaController;
import com.gabriaum.essencecore.util.ConfigUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Objects;

/**
 * Listener that handles teleportation behavior during player movement.
 * If the player moves while a teleport request is active, the request will be canceled.
 */
public class TPAListener implements Listener {

    /**
     * Controller that manages all teleport requests (TPA).
     */
    private final TpaController controller = EssenceCore.getInstance().getTpaController();

    /**
     * Plugin configuration utility, used to access specific messages and settings.
     */
    private final ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

    /**
     * Handles the player movement event. If the player moves while a teleport request
     * is active, the teleport request will be canceled and the player will be notified
     * about the cancellation.
     *
     * @param event The player movement event.
     */
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        // Check if the player has moved
        if (!Objects.equals(event.getTo(), event.getFrom()) && controller.isTeleporting(player.getUniqueId())) {
            // Remove the teleport request if the player has moved
            controller.removeRequest(player.getUniqueId());
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.teleport-cancelled")));
        }
    }
}