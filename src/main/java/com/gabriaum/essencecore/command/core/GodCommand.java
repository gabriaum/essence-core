package com.gabriaum.essencecore.command.core;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Handles the /god command, which toggles player invulnerability.
 *
 * Usage:
 * - /god              — Toggles god mode for yourself (if sender is a player).
 * - /god <player>     — Toggles god mode for another player (any sender).
 *
 * Required permission: essencecore.command.god
 */
public class GodCommand {

    /**
     * Executes the /god command logic, toggling invulnerability (god mode)
     * for the sender or a target player.
     *
     * @param commandArgs The encapsulated command sender, label, and arguments.
     */
    @Command(name = "god", permission = "essencecore.command.god")
    public void godCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        CommandSender sender = commandArgs.getSender();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // Defensive check: ensures config is properly loaded
        if (config == null) {
            throw new NullPointerException("Config is null");
        }

        // Console must specify a target player
        if (!commandArgs.isPlayer() && args.length < 1) {
            sender.sendMessage(Objects.requireNonNull(config.getString("commands.god.usage"))
                    .replace("{label}", commandArgs.getLabel()));
            return;
        }

        switch (args.length) {
            case 0 -> {
                // Self god mode toggle
                Player player = commandArgs.getPlayer();
                if (player == null) {
                    throw new NullPointerException("Player is null");
                }

                boolean newState = !player.isInvulnerable();
                player.setInvulnerable(newState);

                player.sendMessage(Objects.requireNonNull(config.getString("commands.god." + (newState ? "enabled" : "disabled"))));
            }

            case 1 -> {
                // Targeted god mode toggle
                Player target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found"))
                            .replace("{player}", args[0]));
                    return;
                }

                boolean newState = !target.isInvulnerable();
                target.setInvulnerable(newState);

                sender.sendMessage(Objects.requireNonNull(config.getString("commands.god.target-" + (newState ? "enabled" : "disabled")))
                        .replace("{player}", target.getName()));
            }
        }
    }
}