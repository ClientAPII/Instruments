package de.clientapi.instruments.inventories;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class BanjoGUI extends InstrumentGUI implements Listener {
    private static final int OCTAVE_SIZE = 12;
    private static final int TOTAL_NOTES = 24;
    private static final float[] PITCHES = {
            0.5f, 0.53f, 0.56f, 0.6f, 0.63f, 0.67f, 0.7f, 0.75f, 0.8f, 0.85f, 0.9f, 1.0f,
            1.05f, 1.1f, 1.15f, 1.2f, 1.25f, 1.3f, 1.35f, 1.4f, 1.45f, 1.5f, 1.55f, 1.6f
    };
    private static final Sound[] SOUNDS = new Sound[TOTAL_NOTES];

    private static final Material[] GLASS_PANES = {
            Material.WHITE_STAINED_GLASS_PANE, Material.ORANGE_STAINED_GLASS_PANE, Material.MAGENTA_STAINED_GLASS_PANE,
            Material.LIGHT_BLUE_STAINED_GLASS_PANE, Material.YELLOW_STAINED_GLASS_PANE, Material.LIME_STAINED_GLASS_PANE,
            Material.PINK_STAINED_GLASS_PANE, Material.GRAY_STAINED_GLASS_PANE, Material.LIGHT_GRAY_STAINED_GLASS_PANE,
            Material.CYAN_STAINED_GLASS_PANE, Material.PURPLE_STAINED_GLASS_PANE, Material.BLUE_STAINED_GLASS_PANE
    };

    private static final Material[] GLASS_BLOCKS = {
            Material.WHITE_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS,
            Material.LIGHT_BLUE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS,
            Material.PINK_STAINED_GLASS, Material.GRAY_STAINED_GLASS, Material.LIGHT_GRAY_STAINED_GLASS,
            Material.CYAN_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.BLUE_STAINED_GLASS
    };

    public BanjoGUI(JavaPlugin plugin) {
        super(plugin,ChatColor.YELLOW + "Banjo", SOUNDS);
        Arrays.fill(SOUNDS, Sound.BLOCK_NOTE_BLOCK_BANJO);
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @Override
    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, title);
        for (int i = 0; i < TOTAL_NOTES; i++) {
            ItemStack note;
            if (i < OCTAVE_SIZE) {
                note = new ItemStack(GLASS_PANES[i]);
            } else {
                note = new ItemStack(GLASS_BLOCKS[i - OCTAVE_SIZE]);
            }
            ItemMeta meta = note.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ChatColor.YELLOW + "Note " + (i + 1));
                note.setItemMeta(meta);
            }
            gui.setItem(i, note);
        }
        player.openInventory(gui);
    }

    @Override
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getView().getTitle().equals(title)) {
            event.setCancelled(true);
            ItemStack currentItem = event.getCurrentItem();
            if (currentItem != null && currentItem.getType() != Material.AIR) {
                Material type = currentItem.getType();

                Player player = null;
                if (Arrays.asList(GLASS_PANES).contains(type) || Arrays.asList(GLASS_BLOCKS).contains(type)) {
                    player = (Player) event.getWhoClicked();
                    int slot = event.getRawSlot();
                    if (slot >= 0 && slot < TOTAL_NOTES) {
                        float pitch = PITCHES[slot];
                        player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BANJO, 1.0f, pitch);
                       // player.sendMessage(ChatColor.GREEN + "Playing sound with pitch: " + pitch);
                    }
                } else {
                    // player.sendMessage(ChatColor.RED + "Clicked an invalid item type.");
                }
            }
        }
    }
}
