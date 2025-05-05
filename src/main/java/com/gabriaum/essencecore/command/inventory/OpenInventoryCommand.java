package com.gabriaum.essencecore.command.inventory;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Objects;

/**
 * Handles the /openinventory (or /openinv) command, allowing players
 * to open and inspect the inventory of another online player.
 *
 * Usage:
 * - /openinventory <player> — Opens the specified player's inventory.
 *
 * Requirements:
 * - Must be executed by a player (not console).
 * - Target player must be online.
 *
 * Required permission: essencecore.command.openinventory
 */
public class OpenInventoryCommand {

    /**
     * Command executor for /openinventory or /openinv.
     *
     * @param commandArgs Command execution context (args, sender, etc.).
     */
    @Command(
            name = "openinventory",
            aliases = {"openinv"},
            permission = "essencecore.command.openinventory",
            inGameOnly = true
    )
    public void openInventoryCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        CommandSender sender = commandArgs.getSender();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // Validate arguments
        if (args.length < 1) {
            sender.sendMessage(Objects.requireNonNull(config.getString("commands.open-inventory.usage"))
                    .replace("{label}", commandArgs.getLabel()));
            return;
        }

        // Try to fetch the target player
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found"))
                    .replace("{player}", args[0]));
            return;
        }

        // Ensure sender is a player (redundant due to inGameOnly = true but safe for cast)
        if (!(sender instanceof Player senderPlayer)) return;

        // Open target's inventory for the sender
        Inventory targetInventory = target.getInventory();
        senderPlayer.openInventory(targetInventory);

        // Notify the sender
        senderPlayer.sendMessage(Objects.requireNonNull(config.getString("commands.open-inventory.target-opened"))
                .replace("{player}", target.getName()));
    }
}