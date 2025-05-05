package com.gabriaum.essencecore.command.teleport;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.controller.TpaController;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

/**
 * Handles the /tpaccept command, allowing a player to accept a pending teleport request.
 *
 * Requirements:
 * - Must be executed by a player (not the console).
 * - Requires the permission: essencecore.command.tpaccept
 *
 * Dependencies:
 * - {@link TpaController} for request tracking and teleportation logic.
 * - {@link ConfigUtil} for sending localized or configurable messages.
 */
public class TPAcceptCommand {

    // Singleton controller managing TPA requests across players.
    private final TpaController controller = EssenceCore.getInstance().getTpaController();

    /**
     * Accepts a pending teleport request from another player.
     *
     * Usage:
     * /tpaccept <player>
     *
     * Validations:
     * - Player name must be specified.
     * - Request must exist (sender -> target).
     * - Sender must be online.
     *
     * @param commandArgs Command context, including sender and arguments.
     */
    @Command(
            name = "tpaccept",
            aliases = {"tpa-accept"},
            permission = "essencecore.command.tpaccept",
            inGameOnly = true
    )
    public void tpaAcceptCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        Player player = commandArgs.getPlayer();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // Ensure the player name argument is provided
        if (args.length < 1) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.usage"))
                    .replace("{label}", commandArgs.getLabel()));
            return;
        }

        // Attempt to resolve the requesting player
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found"))
                    .replace("{player}", args[0]));
            return;
        }

        // Verify if the request exists
        if (!controller.hasTpaRequest(target.getUniqueId(), player.getUniqueId())) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.no-request"))
                    .replace("{player}", target.getName()));
            return;
        }

        // Accept the request and notify both parties
        controller.acceptRequest(target.getUniqueId(), player.getUniqueId());
        target.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-accepted")));
        player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-accepted-target"))
                .replace("{player}", player.getName()));
    }
}