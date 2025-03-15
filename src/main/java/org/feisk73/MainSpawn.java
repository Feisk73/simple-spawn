package org.feisk73;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.level.Location;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;

import java.io.File;

public class MainSpawn extends PluginBase {

    private Config config;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        config = new Config(new File(getDataFolder(), "spawn.yml"), Config.YAML);
        this.getLogger().info("SimpleSpawn enabled");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("SimpleSpawn disabled");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            this.getLogger().error("Only player can use this command");
            return false;
        }
        switch (command.getName().toLowerCase()) {
            case "spawn":
                spawn((Player) sender);
                return true;
            case "setspawn":
                setspawn((Player) sender);
                return true;
        }
        return false;
    }
    private void setspawn(Player player) {
        config.set("world", player.getLevel().getName());
        config.set("x", player.getX());
        config.set("y", player.getY());
        config.set("z", player.getZ());
        config.set("yaw", player.getYaw());
        config.set("pitch", player.getPitch());
        config.save();
    }
    private void spawn(Player player) {
        Location location = new Location((Double) config.get("x"), (Double) config.get("y"), (Double) config.get("z"), (Double) config.get("yaw"), (Double) config.get("pitch"), this.getServer().getLevelByName(config.getString("world")));
        player.teleport(location);
    }
}