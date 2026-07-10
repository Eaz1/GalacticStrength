package me.eaz.strengthsmp;

import me.eaz.strengthsmp.placeholder.StrengthPlaceholder;
import me.eaz.strengthsmp.commands.StrengthCommand;
import me.eaz.strengthsmp.listeners.DamageListener;
import me.eaz.strengthsmp.listeners.DeathListener;
import me.eaz.strengthsmp.managers.StrengthManager;
import me.eaz.strengthsmp.utils.Messages;
import org.bukkit.plugin.java.JavaPlugin;

public class StrengthSMP extends JavaPlugin {

    private static StrengthSMP instance;
    private StrengthManager strengthManager;
    private Messages messages;

    @Override
    public void onEnable() {

        instance = this;

        saveDefaultConfig();

        messages = new Messages(this);
        strengthManager = new StrengthManager(this);

        getServer().getPluginManager().registerEvents(
                new DeathListener(this), this);

        getServer().getPluginManager().registerEvents(
                new DamageListener(this), this);

        getCommand("strength").setExecutor(
                new StrengthCommand(this));
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
    new StrengthPlaceholder(this).register();
    getLogger().info("PlaceholderAPI hooked!");
        }

        getLogger().info("StrengthSMP enabled!");
    }

    @Override
    public void onDisable() {

        if (strengthManager != null) {
            strengthManager.saveData();
        }

        getLogger().info("StrengthSMP disabled!");
    }

    public static StrengthSMP getInstance() {
        return instance;
    }

    public StrengthManager getStrengthManager() {
        return strengthManager;
    }

    public Messages getMessages() {
        return messages;
    }
}
