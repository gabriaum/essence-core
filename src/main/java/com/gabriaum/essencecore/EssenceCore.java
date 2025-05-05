package com.gabriaum.essencecore;

import com.gabriaum.essencecore.command.core.GamemodeCommand;
import com.gabriaum.essencecore.command.core.GodCommand;
import com.gabriaum.essencecore.command.inventory.EnderchestCommand;
import com.gabriaum.essencecore.command.inventory.FixCommand;
import com.gabriaum.essencecore.command.inventory.OpenInventoryCommand;
import com.gabriaum.essencecore.command.inventory.TrashCommand;
import com.gabriaum.essencecore.command.teleport.TPACommand;
import com.gabriaum.essencecore.command.teleport.TPADenyCommand;
import com.gabriaum.essencecore.command.teleport.TPAcceptCommand;
import com.gabriaum.essencecore.controller.TpaController;
import com.gabriaum.essencecore.listener.GodListener;
import com.gabriaum.essencecore.listener.InventoryListener;
import com.gabriaum.essencecore.listener.TPAListener;
import com.gabriaum.essencecore.scheduler.TeleportScheduler;
import com.gabriaum.essencecore.util.ConfigUtil;
import com.gabriaum.essencecore.util.command.CommandFramework;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * EssenceCore is the main plugin class for the EssenceCore plugin.
 * It handles the initialization of various components such as commands, listeners, controllers, and tasks.
 * This class extends JavaPlugin and is loaded and enabled by the Spigot server.
 */
@Getter
public class EssenceCore extends JavaPlugin {

    @Getter
    protected static EssenceCore instance;

    private ConfigUtil modifiedConfig;
    private CommandFramework commandFramework;
    private TpaController tpaController;

    /**
     * Called when the plugin is loaded by the server.
     * Initializes the static instance of EssenceCore and loads the config file.
     */
    @Override
    public void onLoad() {
        instance = this;
        modifiedConfig = new ConfigUtil(this, "config.yml");
    }

    /**
     * Called when the plugin is enabled by the server.
     * Registers commands, listeners, and starts scheduled tasks.
     */
    @Override
    public void onEnable() {
        tpaController = new TpaController();
        commandFramework = new CommandFramework(this);

        // Register commands for the plugin
        commandFramework.registerCommands(
                new EnderchestCommand(), new GamemodeCommand(), new GodCommand(),
                new OpenInventoryCommand(), new FixCommand(), new TPACommand(),
                new TPAcceptCommand(), new TPADenyCommand(), new TrashCommand()
        );

        // Register listeners for events
        getServer().getPluginManager().registerEvents(new GodListener(), this);
        getServer().getPluginManager().registerEvents(new TPAListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        // Schedule teleportation checks to run periodically
        new TeleportScheduler().runTaskTimer(this, 0L, 20L);
    }
}
