package de.xcrafttm.borderxp.listener;

import de.xcrafttm.borderxp.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerConnectionEvent implements Listener {

    protected final Main plugin;

    public PlayerConnectionEvent(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (!player.hasPlayedBefore()) {
            if (Objects.equals(plugin.getBorderConfig().getVillageToggle(), "true")) {
                World world = Bukkit.getWorld("world");
                Location village = player.getWorld().locateNearestStructure(player.getLocation(), StructureType.VILLAGE, 5000, true);
                if (village == null) {
                    player.sendMessage("§8[§6BorderXP§8] §cNo Village was found, to spawn you in. Please Reset the World and Restart!");
                    return;
                }
                Location loc = player.getWorld().getHighestBlockAt(village).getLocation().add(1, 1, 1);
                village.getChunk().load(true);

                world.setSpawnLocation(loc);
                player.teleport(loc);
            }
            World world = Bukkit.getWorld("world");
            player.teleport(world.getSpawnLocation());
            world.setGameRule(GameRule.SPAWN_RADIUS, 0);
            WorldBorder border = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
            WorldBorder netherborder = Objects.requireNonNull(Bukkit.getWorld("world_nether")).getWorldBorder();
            WorldBorder endborder = Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getWorldBorder();
            border.setCenter(player.getLocation().getBlockX() + 1, player.getLocation().getBlockZ() + 1);
            border.setSize(2);

            netherborder.setCenter(Objects.requireNonNull(Bukkit.getWorld("world_nether")).getSpawnLocation());
            endborder.setCenter(Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getSpawnLocation());
        }

        WorldBorder border = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        WorldBorder netherborder = Objects.requireNonNull(Bukkit.getWorld("world_nether")).getWorldBorder();
        WorldBorder endborder = Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getWorldBorder();
            border.setSize(2);
            netherborder.setSize(2);
            endborder.setSize(2);

    }
}
