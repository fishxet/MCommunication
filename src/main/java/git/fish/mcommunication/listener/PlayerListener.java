package git.fish.mcommunication.listener;

import git.fish.mcommunication.settings.Settings;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import git.fish.mcommunication.objects.RadioType;
import org.joml.Random;

import java.util.HashMap;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PlayerListener implements Listener {
    HashMap<String, RadioType> materials = new HashMap<>();
    HashMap<String, Long> cooldown = new HashMap<>();
    Random random = new Random();
    public PlayerListener() {
        materials.put(Settings.IMP.WMATERIAL.toLowerCase(), RadioType.WEAKRADIO);
        materials.put(Settings.IMP.MMATERIAL.toLowerCase(), RadioType.MEDIUMRADIO);
        materials.put(Settings.IMP.SMATERIAL.toLowerCase(), RadioType.STRONGRADIO);
    }


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        val player = event.getPlayer();
        var msg = event.getMessage().trim();
        int dist = 0, lucky = 0;
        if ((System.currentTimeMillis() - cooldown.getOrDefault(player.getName(), 0L)) >= (Settings.IMP.COOLDOWN * 1000L)) {

            cooldown.put(player.getName(), System.currentTimeMillis());

            if (msg.startsWith("!")) {
                event.setFormat(Settings.IMP.GCHAT);

                val itemInMainHand = player.getInventory().getItemInMainHand().getType();
                msg = msg.substring(1);

                val info = materials.get(itemInMainHand.toString().toLowerCase());
                if (info != null) {
                    dist = info.getDistance();
                    lucky = info.getLucky();
                }

                if (dist == 0) {
                    event.setCancelled(true);
                    player.sendMessage("Для отправки сообщения в глобальный возпользуйтесь радио");
                    return;
                }

                if (random.nextInt(100) < lucky) {
                    event.getRecipients().clear();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                        if (onlinePlayer.getLocation().distance(player.getLocation()) <= dist)
                            event.getRecipients().add(onlinePlayer);
                }
                else {
                    event.setCancelled(true);
                    player.sendMessage("К сожалению при попытке связи сигнал был утерян.");
                }

            } else {
                event.setFormat(Settings.IMP.LCHAT);
                event.getRecipients().clear();
                event.setMessage(this.color(player.getName() + " >>> " + msg));
                for (Player onlinePlayer : Bukkit.getOnlinePlayers())
                    if (onlinePlayer.getLocation().distance(player.getLocation()) <= Settings.IMP.LOCALDIST)
                        event.getRecipients().add(onlinePlayer);
            }
        } else {
            event.setCancelled(true);
            event.getRecipients().clear();
            player.sendMessage(this.color(Settings.IMP.SERVER_PREFIX + " Отправлять сообщение можно раз в " + Settings.IMP.COOLDOWN + " секунд"));

        }
    }

    private String color(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
