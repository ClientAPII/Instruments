package de.clientapi.instruments;

import de.clientapi.instruments.commands.InstrumentCommand;
import de.clientapi.instruments.inventories.BanjoGUI;
import de.clientapi.instruments.listeners.InstrumentListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Instruments extends JavaPlugin {

    //Todo:
    // - Lazy Parser shit
    // - Vielleicht ne Website womit man nen Code bekommt welcher dann hier ein fertiges Buch mit Musik erstellt


    @Override
    public void onEnable() {
        InstrumentCommand instrumentCommand = new InstrumentCommand(this);
        this.getCommand("instrument").setExecutor(instrumentCommand);
        this.getCommand("instrument").setTabCompleter(instrumentCommand);
        new InstrumentListener(this);
        new BanjoGUI(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}