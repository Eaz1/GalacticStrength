package me.eaz.strengthsmp.managers;

import me.eaz.strengthsmp.StrengthSMP;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StrengthManager {

    private final StrengthSMP plugin;
    private final Map<UUID, Double> strengthMap = new HashMap<>();

    private File dataFile;
    private FileConfiguration dataConfig;

    public StrengthManager(StrengthSMP plugin) {
        this.plugin = plugin;
        setupData();
        loadData();
    }

    private void setupData() {
        dataFile = new File(plugin.getDataFolder(), "strength.yml");

        if (!dataFile.exists()) {
            try {
                plugin.getDataFolder().mkdirs();
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }
      private void loadData() {
        if (dataConfig.contains("players")) {
            for (String uuid : dataConfig.getConfigurationSection("players").getKeys(false)) {
                strengthMap.put(
                        UUID.fromString(uuid),
                        dataConfig.getDouble("players." + uuid)
                );
            }
        }
    }

    public void saveData() {
        dataConfig.set("players", null);

        for (Map.Entry<UUID, Double> entry : strengthMap.entrySet()) {
            dataConfig.set("players." + entry.getKey().toString(), entry.getValue());
        }

        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public double getStrength(Player player) {
        return strengthMap.getOrDefault(player.getUniqueId(), 0.0);
    }

    public void setStrength(Player player, double amount) {
        double max = plugin.getConfig().getDouble("max-bonus-damage");
        double min = plugin.getConfig().getDouble("min-bonus-damage");

        amount = Math.max(min, Math.min(max, amount));

        strengthMap.put(player.getUniqueId(), amount);
        saveData();
    }
      public void addStrength(Player player) {
        double increase = plugin.getConfig().getDouble("damage-per-kill");
        setStrength(player, getStrength(player) + increase);
    }

    public void removeStrength(Player player) {
        if (!plugin.getConfig().getBoolean("lose-on-death")) {
            return;
        }

        double decrease = plugin.getConfig().getDouble("damage-per-kill");
        setStrength(player, getStrength(player) - decrease);
    }

    public boolean isAtMax(Player player) {
        return getStrength(player) >= plugin.getConfig().getDouble("max-bonus-damage");
    }

    public void resetAll() {
        strengthMap.clear();
        saveData();
    }

    public Map<UUID, Double> getAllStrength() {
        return new HashMap<>(strengthMap);
    }
}
