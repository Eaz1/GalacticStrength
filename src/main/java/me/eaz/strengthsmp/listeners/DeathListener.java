package me.eaz.strengthsmp.listeners;

import me.eaz.strengthsmp.StrengthSMP;
import me.eaz.strengthsmp.managers.StrengthManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {

    private final StrengthSMP plugin;
    private final StrengthManager manager;

    public DeathListener(StrengthSMP plugin) {
        this.plugin = plugin;
        this.manager = plugin.getStrengthManager();
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {

        Player victim = event.getEntity();
        Player killer = victim.getKiller();

        if (killer == null) {
            return;
        }

        if (!manager.isAtMax(killer)) {
            manager.addStrength(killer);
            killer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.gained")
                            .replace("%bonus%", String.valueOf(manager.getStrength(killer) / 2.0))));
        } else {
            killer.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    plugin.getConfig().getString("messages.max")));
        }

        manager.removeStrength(victim);

        victim.sendMessage(ChatColor.translateAlternateColorCodes('&',
                plugin.getConfig().getString("messages.lost")
                        .replace("%bonus%", String.valueOf(manager.getStrength(victim) / 2.0))));
    }
}
