package com.gabriaum.essencecore.command.inventory;

import com.gabriaum.essencecore.EssenceCore;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.Command;
import com.gabriaum.essencecore.util.command.CommandArgs;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

/**
 * Handles the /fix command, allowing players to repair the item in their main hand.
 *
 * Usage:
 * - /fix              — Repairs the item currently held by the player.
 *
 * Restrictions:
 * - Only usable in-game.
 * - Item must be repairable (has durability).
 * - Item must not already be fully repaired.
 *
 * Required permission: essencecore.command.fix
 */
public class FixCommand {

    /**
     * Executes the /fix command logic.
     *
     * @param commandArgs Contextual command arguments (sender, label, args).
     */
    @Command(
            name = "fix",
            permission = "essencecore.command.fix",
            inGameOnly = true
    )
    public void fixCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        ConfigUtil config = EssenceCore.getInstance().getModifiedConfig();

        ItemStack item = player.getInventory().getItemInMainHand();

        // Check if the player is holding an item
        if (item.getType().equals(Material.AIR)) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.fix.no-item-in-hand")));
            return;
        }

        // Check if the item is repairable (has durability)
        if (item.getType().getMaxDurability() == 0) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.fix.not-repairable")));
            return;
        }

        // Check if the item is already fully repaired
        if (item.getDurability() == 0) {
            player.sendMessage(Objects.requireNonNull(config.getString("commands.fix.already-repaired")));
            return;
        }

        // Repair the item by setting durability to 0
        item.setDurability((short) 0);
        player.getInventory().setItemInMainHand(item);

        // Notify the player of successful repair
        player.sendMessage(Objects.requireNonNull(config.getString("commands.fix.repaired")));
    }
}