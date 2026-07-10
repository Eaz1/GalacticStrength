package me.eaz.strengthsmp.listeners;

import me.eaz.strengthsmp.StrengthSMP;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageListener implements Listener {

    private final StrengthSMP plugin;

    public DamageListener(StrengthSMP plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();

        double bonus = plugin.getStrengthManager().getStrength(attacker);

        event.setDamage(event.getDamage() + bonus);
    }
}
