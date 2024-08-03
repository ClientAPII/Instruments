package de.clientapi.instruments.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InstrumentCommand implements CommandExecutor, TabCompleter {
    private final JavaPlugin plugin;
    private static final String[] INSTRUMENTS = {"banjo", "bell", "flute", "didgeridoo", "ukulele"};

    public InstrumentCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be used by players.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Usage: /instrument <name>");
            return true;
        }

        Player player = (Player) sender;
        String instrumentName = args[0].toLowerCase();

        ItemStack instrument = createInstrument(instrumentName);
        if (instrument == null) {
            player.sendMessage(ChatColor.RED + "Invalid instrument name.");
            return true;
        }

        player.getInventory().addItem(instrument);
        player.sendMessage(ChatColor.GREEN + "You have been given a " + instrumentName + ".");
        return true;
    }

    private ItemStack createInstrument(String name) {
        Material material = Material.STICK; // Default material, can be changed based on instrument
        String displayName;
        switch (name) {
            case "banjo":
                displayName = ChatColor.GOLD + "Banjo";
                break;
            case "bell":
                displayName = ChatColor.GOLD + "Bell";
                break;
            case "flute":
                displayName = ChatColor.GOLD + "Flute";
                break;
            case "didgeridoo":
                displayName = ChatColor.GOLD + "Didgeridoo";
                break;
            case "ukulele":
                displayName = ChatColor.GOLD + "Ukulele";
                break;
            default:
                return null;
        }

        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "Instrument"));
            item.setItemMeta(meta);
        }
        return item;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Arrays.stream(INSTRUMENTS)
                    .filter(instrument -> instrument.startsWith(args[0].toLowerCase()))
                    .collect(Collectors.toList());
        }
        return null;
    }
}