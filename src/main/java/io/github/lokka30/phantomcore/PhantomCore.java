package io.github.lokka30.phantomcore;

import de.leonhard.storage.LightningBuilder;
import de.leonhard.storage.internal.FlatFile;
import io.github.lokka30.phantomcore.commands.*;
import io.github.lokka30.phantomcore.events.JoinTeleportationListener;
import io.github.lokka30.phantomcore.events.JoinVanishUpdateListener;
import io.github.lokka30.phantomcore.utils.LogLevel;
import io.github.lokka30.phantomcore.utils.Utils;
import org.bstats.bukkit.Metrics;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Objects;

public class PhantomCore extends JavaPlugin {

    public Manager manager;
    public Utils utils;
    public FlatFile settings;
    public FlatFile messages;
    public FlatFile data;
    private PluginManager pluginManager;

    @Override
    public void onLoad() {
        manager = new Manager(this);
        utils = new Utils();
        pluginManager = getServer().getPluginManager();
    }

    @Override
    public void onEnable() {
        log(LogLevel.INFO, "[Enabling Began]");

        log(LogLevel.INFO, "Checking compatibility...");
        if (checkCompatibility()) {

            log(LogLevel.INFO, "Loading files...");
            loadFiles();

            log(LogLevel.INFO, "Registering events...");
            registerEvents();

            log(LogLevel.INFO, "Registering commands...");
            registerCommands();

            log(LogLevel.INFO, "Starting bStats metrics...");
            new Metrics(this, 7258);

            log(LogLevel.INFO, "[Enabling Complete]");
        } else {
            log(LogLevel.INFO, "[Enabling Cancelled]");
            pluginManager.disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        //Method is present for possible use in the future, it is commented out as it is pointless to send status messages if nothing is happening in onDisable.
    }

    private boolean checkCompatibility() {
        //Compare server version
        //Note: does not return false, as the plugin might actually work fine on older or newer versions.
        String currentVersion = getServer().getVersion();
        String recommendedVersion = utils.getRecommendedServerVersion();

        if (!currentVersion.contains(recommendedVersion)) {
            log(LogLevel.WARNING, "&b[Using Unsupported Version] &7You are not running a recommended server version. The author's support will only be given to users running &b" + recommendedVersion + "&7.");
        }

        //More checks will be added if required. Currently it always returns true.
        return true;
    }

    private void loadFiles() {
        //Tell LightningStorage to start its business.
        final String path = "plugins/PhantomCore/";
        settings = LightningBuilder
                .fromFile(new File(path + "settings"))
                .addInputStreamFromResource("settings.yml")
                .createYaml();
        messages = LightningBuilder
                .fromFile(new File(path + "messages"))
                .addInputStreamFromResource("messages.yml")
                .createYaml();
        data = LightningBuilder
                .fromFile(new File(path + "data"))
                .addInputStreamFromResource("data.json")
                .createJson();

        //Check if they exist
        final File settingsFile = new File(path + "settings.yml");
        final File messagesFile = new File(path + "messages.yml");
        final File dataFile = new File(path + "data.json");

        if (!(settingsFile.exists() && !settingsFile.isDirectory())) {
            log(LogLevel.INFO, "File &bsettings.yml&7 doesn't exist. Creating it now.");
            saveResource("settings.yml", false);
        }

        if (!(messagesFile.exists() && !messagesFile.isDirectory())) {
            log(LogLevel.INFO, "File &bmessages.yml&7 doesn't exist. Creating it now.");
            saveResource("messages.yml", false);
        }

        if (!(dataFile.exists() && !dataFile.isDirectory())) {
            log(LogLevel.INFO, "File &bdata.json&7 doesn't exist. Creating it now.");
            saveResource("data.json", false);
        }

        //Check their versions
        if (settings.get("file-version", 0) != utils.getLatestSettingsVersion()) {
            log(LogLevel.SEVERE, "File &bsettings.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }

        if (messages.get("file-version", 0) != utils.getLatestMessagesVersion()) {
            log(LogLevel.SEVERE, "File &bmessages.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }

        if (data.get("file-version", 0) != utils.getLatestDataVersion()) {
            log(LogLevel.SEVERE, "File &bdata.yml&7 is out of date! Errors are likely to occur! Reset it or merge the old values to the new file.");
        }
    }

    private void registerEvents() {
        pluginManager.registerEvents(new JoinTeleportationListener(this), this);
        pluginManager.registerEvents(new JoinVanishUpdateListener(this), this);
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("broadcast")).setExecutor(new BroadcastCommand(this));
        Objects.requireNonNull(getCommand("clearinventory")).setExecutor(new ClearInventoryCommand(this));
        Objects.requireNonNull(getCommand("delwarp")).setExecutor(new DelWarpCommand(this));
        Objects.requireNonNull(getCommand("enchant")).setExecutor(new EnchantCommand(this));
        Objects.requireNonNull(getCommand("feed")).setExecutor(new FeedCommand(this));
        Objects.requireNonNull(getCommand("fly")).setExecutor(new FlyCommand(this));
        Objects.requireNonNull(getCommand("flyspeed")).setExecutor(new FlyspeedCommand(this));
        Objects.requireNonNull(getCommand("gamemode")).setExecutor(new GameModeCommand(this));
        Objects.requireNonNull(getCommand("god")).setExecutor(new GodCommand(this));
        Objects.requireNonNull(getCommand("heal")).setExecutor(new HealCommand(this));
        Objects.requireNonNull(getCommand("invsee")).setExecutor(new InvseeCommand(this));
        Objects.requireNonNull(getCommand("kill")).setExecutor(new KillCommand(this));
        Objects.requireNonNull(getCommand("near")).setExecutor(new NearCommand(this));
        Objects.requireNonNull(getCommand("phantomcore")).setExecutor(new PhantomCoreCommand(this));
        Objects.requireNonNull(getCommand("saycoords")).setExecutor(new SayCoordsCommand(this));
        Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
        Objects.requireNonNull(getCommand("setwarp")).setExecutor(new SetWarpCommand(this));
        Objects.requireNonNull(getCommand("smite")).setExecutor(new SmiteCommand(this));
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
        Objects.requireNonNull(getCommand("suicide")).setExecutor(new SuicideCommand(this));
        Objects.requireNonNull(getCommand("top")).setExecutor(new TopCommand(this));
        Objects.requireNonNull(getCommand("vanish")).setExecutor(new VanishCommand(this));
        Objects.requireNonNull(getCommand("walkspeed")).setExecutor(new WalkspeedCommand(this));
        Objects.requireNonNull(getCommand("warp")).setExecutor(new WarpCommand(this));
        Objects.requireNonNull(getCommand("workbench")).setExecutor(new WorkbenchCommand(this));
    }

    public String colorize(final String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    public void log(final LogLevel level, String msg) {
        msg = colorize("&b&lPhantomCore: &7" + msg);

        switch (level) {
            case INFO:
                getLogger().info(msg);
                return;
            case WARNING:
                getLogger().warning(msg);
                return;
            case SEVERE:
                getLogger().severe(msg);
                return;
            default:
                throw new IllegalStateException("Unexpected LogLevel: " + level.toString() + ". Message: " + msg);
        }
    }
}
