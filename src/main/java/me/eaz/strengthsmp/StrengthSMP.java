package me.eaz.strengthsmp;

import me.eaz.strengthsmp.commands.StrengthCommand;
import me.eaz.strengthsmp.listeners.DamageListener;
import me.eaz.strengthsmp.listeners.DeathListener;
import me.eaz.strengthsmp.managers.StrengthManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StrengthSMP extends JavaPlugin {

    private static StrengthSMP instance;
    private StrengthManager strengthManager;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();

        strengthManager = new StrengthManager(this);

        getServer().getPluginManager().registerEvents(new DeathListener(this), this);
        getServer().getPluginManager().registerEvents(new DamageListener(this), this);

        getCommand("strength").setExecutor(new StrengthCommand(this));

        getLogger().info("StrengthSMP has been enabled!");
    }

    @Override
    public void onDisable() {
        strengthManager.saveData();
        getLogger().info("StrengthSMP has been disabled!");
    }

    public static StrengthSMP getInstance() {
        return instance;
    }

    public StrengthManager getStrengthManager() {
        return strengthManager;
    }
}
