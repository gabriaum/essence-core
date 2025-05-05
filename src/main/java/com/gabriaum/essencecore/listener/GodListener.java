package com.gabriaum.essencecore.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;

/**
 * A listener that handles player invulnerability, preventing damage and food level changes
 * when the player is in a "god mode" state.
 */
public class GodListener implements Listener {

    /**
     * Event handler for entity damage events.
     * Cancels damage if the player is invulnerable (i.e., in god mode).
     *
     * @param event the EntityDamageEvent
     */
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Check if the entity is a player and if they are invulnerable
        if (event.getEntity() instanceof Player player) {
            // Cancel damage if the player is invulnerable
            event.setCancelled(player.isInvulnerable());
        }
    }

    /**
     * Event handler for food level change events.
     * Cancels food level changes if the player is invulnerable (i.e., in god mode).
     *
     * @param event the FoodLevelChangeEvent
     */
    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        // Check if the entity is a player and if they are invulnerable
        if (event.getEntity() instanceof Player player) {
            // Cancel food level change if the player is invulnerable
            event.setCancelled(player.isInvulnerable());
        }
    }
}