package me.eaz.strengthsmp.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.eaz.strengthsmp.StrengthSMP;
import org.bukkit.entity.Player;

public class StrengthPlaceholder extends PlaceholderExpansion {

    private final StrengthSMP plugin;

    public StrengthPlaceholder(StrengthSMP plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getIdentifier() {
        return "strengthsmp";
    }

    @Override
    public String getAuthor() {
        return "Eaz";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier) {

        if (player == null) {
            return "";
        }

        if (identifier.equalsIgnoreCase("bonus")) {

            double hearts =
                    plugin.getStrengthManager()
                    .getStrength(player) / 2.0;

            return String.valueOf(hearts);
        }

        return null;
    }
}
