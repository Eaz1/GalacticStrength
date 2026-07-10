package me.eaz.strengthsmp.utils;

import me.eaz.strengthsmp.StrengthSMP;
import org.bukkit.ChatColor;

public class Messages {

    private final StrengthSMP plugin;

    public Messages(StrengthSMP plugin) {
        this.plugin = plugin;
    }

    public String get(String path) {
        String message = plugin.getConfig().getString(path);

        if (message == null) {
            return "";
        }

        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String get(String path, String placeholder, String value) {
        return get(path).replace(placeholder, value);
    }
}
