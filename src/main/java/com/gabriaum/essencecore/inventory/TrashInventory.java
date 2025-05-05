package com.gabriaum.essencecore.inventory;

import com.gabriaum.essencecore.util.ConfigUtil;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * Represents a Trash inventory for players to discard their items.
 *
 * Allows you to open a custom trash inventory with configurable size and title.
 */
@RequiredArgsConstructor
public class TrashInventory {
    private final Player player;
    private final ConfigUtil config;

    /**
     * Opens the Trash inventory for the player.
     * The size and title are configured via the configuration file.
     */
    public void open() {
        // Retrieve the inventory size from the config, defaulting to 6 rows if not set
        int size = config.getInt("inventories.trash.size", 6);

        // Retrieve the inventory title from the config, defaulting to "Trash" if not set
        String title = config.getString("inventories.trash.title", "Trash");

        // Create the inventory and open it for the player
        Inventory inventory = Bukkit.createInventory(player, 9 * size, title);
        player.openInventory(inventory);
    }
}