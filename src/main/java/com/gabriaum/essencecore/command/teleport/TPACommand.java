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
 * Handles the /tpa command, allowing a player to request teleportation to another player.
 *
 * Requirements:
 * - Must be executed by a player (not from console).
 * - Requires the permission: essencecore.command.tpa
 *
 * This command supports request cooldowns, self-checking, and duplication prevention
 * via {@link TpaController}.
 */
public class TPACommand {

    // Controller managing all TPA requests.
    private final TpaController controller = EssenceCore.getInstance().getTpaController();

    /**
     * Sends a teleport request to another player.
     *
     * Usage:
     * /tpa <player>
     *
     * Validations:
     * - Must specify a player name.
     * - Target must be online.
     * - Cannot request teleportation to self.
     * - Cannot send duplicate requests.
     *
     * @param commandArgs Context for the command execution.
     */
    @Command(
            name = "tpa",
            permission = "essencecore.command.tpa",
            inGameOnly = true
    )
    public void tpaCommand(CommandArgs commandArgs) {
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
            player.sendMessage(Objects.requireNonNull(config.getString("messages.player-not-found")));
            return;
        }

        // Prevent self-request
        if (target == player) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.self")));
            return;
        }

        // Prevent duplicate requests
        if (controller.hasTpaRequest(player.getUniqueId(), target.getUniqueId())) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.already-sent")));
            return;
        }

        // Register the request and notify both players
        controller.sendRequest(player.getUniqueId(), target.getUniqueId());

        player.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-sent"))
                .replace("{player}", target.getName()));

        target.sendMessage(Objects.requireNonNull(config.getString("commands.tpa.request-received"))
                .replace("{player}", player.getName()));
    }
}