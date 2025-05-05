package com.gabriaum.essencecore.command.inventory;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.inventory.TrashInventory;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.entity.Player;

/**
 * Handles the /trash command, which opens a custom trash GUI
 * allowing the player to discard unwanted items.
 *
 * Requirements:
 * - Must be executed by a player (not console).
 * - Requires permission: essencecore.command.trash
 *
 * Integration:
 * - Uses {@link TrashInventory} to provide the trash interface.
 */
public class TrashCommand {

    /**
     * Executes the /trash command.
     * Opens a new instance of the trash inventory GUI for the player.
     *
     * @param commandArgs The context of the command execution, including sender and arguments.
     */
    @Command(
            name = "trash",
            permission = "essencecore.command.trash",
            inGameOnly = true
    )
    public void trashCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        // Initialize and open the custom trash inventory GUI
        TrashInventory inventory = new TrashInventory(player, EssenceCore.getInstance().getModifiedConfig());
        inventory.open();
    }
}