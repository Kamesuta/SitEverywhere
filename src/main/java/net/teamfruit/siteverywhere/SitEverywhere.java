package net.teamfruit.siteverywhere;

import com.cnaude.chairs.api.ChairsAPI;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class SitEverywhere extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;
        if (args.length > 0) {
            if (!sender.hasPermission("chairs.other")) {
                sender.sendMessage("You can't sit other players");
                return true;
            }
            Player p = getServer().getPlayer(args[0]);
            if (p == null) {
                sender.sendMessage("Invalid player name or player is offline");
                return true;
            }
            player = p;
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("You are not player");
                return true;
            }
            player = (Player) sender;
        }

        if (ChairsAPI.isSitting(player)) {
            ChairsAPI.unsit(player);
            return true;
        }

        Location plocation = player.getLocation();
        plocation.add(0, -.55, 0);
        ChairsAPI.sit(player, plocation.getBlock(), plocation);

        return true;
    }

}
