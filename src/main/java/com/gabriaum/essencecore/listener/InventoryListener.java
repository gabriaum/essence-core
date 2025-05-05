package com.gabriaum.essencecore.listener;

import com.gabriaum.essencecore.inventory.TrashInventory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class InventoryListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        Inventory inventory = event.getInventory();

        // Check if the inventory is a TrashInventory
        if (inventory.getHolder() instanceof TrashInventory) {
            for (int i = 0; i < inventory.getSize(); i++) {
                if (inventory.getItem(i) != null) {
                    // Clear the item
                    inventory.setItem(i, null);
                }
            }
        }
    }
}