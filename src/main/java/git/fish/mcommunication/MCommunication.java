package git.fish.mcommunication;

import git.fish.mcommunication.listener.PlayerListener;
import git.fish.mcommunication.settings.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public final class MCommunication extends JavaPlugin {

    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
        Settings.IMP.reload(new File(this.getDataFolder(),"config.yml"));
        this.getCommand("mcommunication").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length != 1 || !args[0].equalsIgnoreCase("reload")) {
            sender.sendMessage("Use: /mcommunication reload - reload plugin");
            return true;
        }

        sender.sendMessage("Вы успешно перезагрузили плагин!");
        Settings.IMP.reload(new File(this.getDataFolder(),"config.yml"));
        return true;
    }
}
