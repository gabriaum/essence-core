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
 * Handles the /tpdeny command, allowing a player to deny a teleportation request.
 *
 * Requirements:
 * - Must be executed by a player (not from console).
 * - Requires the permission: essencecore.command.tpadeny
 *
 * This command ensures that a player can decline a TPA request from another player,
 * and sends appropriate messages to both the requester and target player.
 */
public class TPADenyCommand {

    // Controller managing all TPA requests.
    private final TpaController controller = EssenceCore.getInstance().getTpaController();

    /**
     * Denies a teleportation request sent to the player.
     *
     * Usage:
     * /tpdeny <player>
     *
     * Validations:
     * - Must specify a player name.
     * - Target player must have sent a TPA request to the player executing the command.
     *
     * @param commandArgs Context for the command execution.
     */
    @Command(
            name = "tpdeny",
            aliases = {"tpadeny", "tpa-deny"},
            permission = "essencecore.command.tpadeny",
            inGameOnly = true
    )
    public void tpaDenyCommand(CommandArgs commandArgs) {
        String[] args = commandArgs.getArgs();
        Player player = commandArgs.getPlayer();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        // Validate input
        if (args.length < 1) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.usage"))
                    .replace("{label}", commandArgs.getLabel()));
            return;
        }

        // Resolve target player
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            player.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found"))
                    .replace("{player}", args[0]));
            return;
        }

        // Check if a TPA request exists
        if (!controller.hasTpaRequest(target.getUniqueId(), player.getUniqueId())) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.no-request"))
                    .replace("{player}", target.getName()));
            return;
        }

        // Deny the TPA request and notify both players
        controller.denyRequest(target.getUniqueId(), player.getUniqueId());

        target.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-declined")));
        player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-declined")));
    }
}