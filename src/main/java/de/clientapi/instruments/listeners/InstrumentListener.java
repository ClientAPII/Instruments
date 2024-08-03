package de.clientapi.instruments.listeners;

import de.clientapi.instruments.inventories.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.inventory.meta.ItemMeta;

public class InstrumentListener implements Listener {
    private JavaPlugin plugin;

    public InstrumentListener(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        if (item != null && item.getType() == Material.STICK && item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            if (meta.hasDisplayName()) {
                String displayName = ChatColor.stripColor(meta.getDisplayName()).toLowerCase();
                switch (displayName) {
                    case "banjo":
                        new BanjoGUI(plugin).open(player);
                        break;
                    case "bell":
                        new BellGUI(plugin).open(player);
                        break;
                    case "flute":
                        new FluteGUI(plugin).open(player);
                    case "didgeridoo":
                        new DidgeridooGUI(plugin).open(player);
                    case "ukulele":
                        new UkuleleGUI(plugin).open(player);
                        break;
                    default:
                        break;
                }
            }
        }
    }
}
