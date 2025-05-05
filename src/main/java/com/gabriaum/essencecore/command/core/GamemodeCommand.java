package com.gabriaum.essencecore.command.core;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Handles the /gamemode (or /gm) command to change a player's game mode.
 *
 * Usage:
 * - /gm <mode>              — Changes the sender's gamemode (if a player)
 * - /gm <mode> <player>     — Changes another player's gamemode (console or privileged player)
 *
 * Required permission: essencecore.command.gamemode
 *
 * Supported aliases: "gm"
 */
public class GamemodeCommand {

    /**
     * Executes the /gamemode command logic.
     *
     * @param commandArgs Encapsulates the command sender, label, and arguments.
     */
    @Command(name = "gamemode", aliases = {"gm"}, permission = "essencecore.command.gamemode")
    public void gamemodeCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        CommandSender sender = commandArgs.getSender();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // Defensive check to avoid null config
        if (config == null) {
            throw new NullPointerException("Config is null");
        }

        // Validate argument count depending on whether the sender is a player or console
        boolean playerSender = commandArgs.isPlayer();
        if ((!playerSender && args.length < 2) || (playerSender && (args.length < 1 || args.length > 2))) {
            sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.usage"))
                    .replace("{label}", commandArgs.getLabel()));
            return;
        }

        // Parse the desired game mode
        GameMode mode = getGameMode(args[0].toLowerCase());
        if (mode == null) {
            sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.invalid-mode"))
                    .replace("{mode}", args[0]));
            return;
        }

        switch (args.length) {
            case 1 -> {
                // Self mode change (only valid if sender is a player)
                Player player = commandArgs.getPlayer();

                if (player.getGameMode().equals(mode)) {
                    sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.already-in-mode")));
                    return;
                }

                player.setGameMode(mode);
                sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.changed"))
                        .replace("{mode}", mode.name().toLowerCase()));
            }

            case 2 -> {
                // Changing another player's gamemode
                Player target = Bukkit.getPlayer(args[1]);
                if (target == null) {
                    sender.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found")));
                    return;
                }

                if (target.getGameMode().equals(mode)) {
                    sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.target-already-in-mode")));
                    return;
                }

                target.setGameMode(mode);
                sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.target-changed"))
                        .replace("{player}", target.getName())
                        .replace("{mode}", mode.name().toLowerCase()));
            }

            default -> {
                // Fallback in case of unexpected argument count
                sender.sendMessage(Objects.requireNonNull(config.getString("commands.gamemode.usage"))
                        .replace("{label}", commandArgs.getLabel()));
            }
        }
    }

    /**
     * Parses a user-provided gamemode string into a valid GameMode.
     *
     * @param mode The gamemode string (e.g., "0", "survival", "c", etc.).
     * @return Corresponding GameMode enum or null if invalid.
     */
    private GameMode getGameMode(String mode) {
        return switch (mode.toLowerCase()) {
            case "0", "survival", "s" -> GameMode.SURVIVAL;
            case "1", "creative", "c" -> GameMode.CREATIVE;
            case "2", "adventure", "a" -> GameMode.ADVENTURE;
            case "3", "spectator", "sp" -> GameMode.SPECTATOR;
            default -> null;
        };
    }
}