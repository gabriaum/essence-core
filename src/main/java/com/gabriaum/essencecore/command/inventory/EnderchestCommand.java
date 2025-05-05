package com.gabriaum.essencecore.command.inventory;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Handles the /enderchest (or /ec) command to open the player's own or another player's ender chest.
 *
 * Usage:
 * - /ec                — Opens the sender's own ender chest.
 * - /ec <player>       — Opens the ender chest of the specified player.
 *
 * Required permission: essencecore.command.enderchest
 * Restriction: Can only be used by players (inGameOnly = true).
 */
public class EnderchestCommand {

    /**
     * Executes the /enderchest command logic.
     *
     * @param commandArgs Wrapper for command arguments and context.
     */
    @Command(
            name = "enderchest",
            aliases = {"ec"},
            permission = "essencecore.command.enderchest",
            inGameOnly = true
    )
    public void enderChestCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        Player sender = commandArgs.getPlayer();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // If no arguments are given, open the sender's own ender chest
        if (args.length < 1) {
            sender.openInventory(sender.getEnderChest());
            return;
        }

        // Try to get the target player by name
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found")));
            return;
        }

        // Open target's ender chest and notify the sender
        sender.openInventory(target.getEnderChest());
        sender.sendMessage(Objects.requireNonNull(config.getString("commands.enderchest.target-opened"))
                .replace("{player}", target.getName()));
    }
}