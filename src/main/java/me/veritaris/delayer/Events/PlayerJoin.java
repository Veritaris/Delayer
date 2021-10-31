package me.veritaris.delayer.Events;

import me.veritaris.delayer.Delayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player eventPlayer = event.getPlayer();
        String player = String.format("%s", eventPlayer.getName());

        if (Delayer.getCommandsQueue().containsKey(player)) {
            Delayer.logger.info(String.format(
                    "Player %s joining", eventPlayer.getName()
            ));
            String command = Delayer.getCommandsQueue().get(player);
            Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), command);
            Delayer.logger.info(String.format(
                    "Issued command %s", command
            ));
            Delayer.getInstance().removeFromCommandQueue(player);
        }
    }
}
