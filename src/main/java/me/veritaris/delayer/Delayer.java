package me.veritaris.delayer;

import me.veritaris.delayer.Events.PlayerJoin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.logging.Logger;

public final class Delayer extends JavaPlugin {
    public static Logger logger;
    private static Delayer instance;
    private static HashMap<String, String> commandsQueue = new HashMap<>();

    public static Delayer getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        logger = getLogger();
        logger.info("Enabling plugin");

        Delayer.instance = this;

        this.getCommand("delay").setExecutor(new CommandHandler());

        this.registerPlayerEvents();
        this.registerBlockEvents();
    }

    private void registerPlayerEvents() {
        PluginManager pm = this.getServer().getPluginManager();
        pm.registerEvents(new PlayerJoin(), this);
    }

    private void registerBlockEvents() {

    }

    @Override
    public void onDisable() {
        logger.info("Disabling plugin");
    }

    public static HashMap<String, String> getCommandsQueue() {
        return commandsQueue;
    }

    public static void setCommandsQueue(HashMap<String, String> commandsQueue) {
        Delayer.commandsQueue = commandsQueue;
    }

    public void addToCommandQueue(String player, String command) {
        commandsQueue.put(player, command);
    }

    public boolean removeFromCommandQueue(String player) {
        if (commandsQueue.containsKey(player)) {
            commandsQueue.remove(player);
            return true;
        }
        return false;
    }
}
