package subside.plugins.koth.utils;

import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import subside.plugins.koth.adapter.Koth;
import subside.plugins.koth.adapter.KothHandler;
import subside.plugins.koth.adapter.TimeObject;

public class MessageBuilder {
    StrObj message;
    String excluder;
    boolean shouldExclude;

    private class StrObj {
        private String[] message;

        private StrObj(String[] message) {
            this.message = message;
            if (message == null) this.message = new String[] {};
        }

        protected StrObj replaceAll(String search, String replace) {
            for (int x = 0; x < message.length; x++) {
                message[x] = message[x].replaceAll(search, replace);
            }
            return this;
        }

        protected String[] build() {
            for (int x = 0; x < message.length; x++) {
                message[x] = ChatColor.translateAlternateColorCodes('&', message[x]);
            }
            return message;
        }

    }

    public MessageBuilder(String[] msg) {
        this.message = new StrObj(msg);
    }
    
    public MessageBuilder(String msg){
        this.message = new StrObj(new String[]{msg});
    }

    public MessageBuilder koth(String koth) {
        message.replaceAll("%koth%", koth.replaceAll("\\\\\\\\", "%5C").replaceAll("([^\\\\])_", "$1 ").replaceAll("\\\\_", "_").replaceAll("%5C", "\\\\\\\\"));
        Koth ar = KothHandler.getKoth(koth);
        if (ar != null) {
            Location loc = ar.getMidd();
            if (loc != null) {
                message.replaceAll("%x%", "" + loc.getBlockX()).replaceAll("%y%", "" + loc.getBlockY()).replaceAll("%z%", "" + loc.getBlockZ());
                try {
                    message.replaceAll("%world%", loc.getWorld().getName());
                }
                catch (Exception e) {}
            }
        }
        return this;
    }
    
    public MessageBuilder area(String area){
        message.replaceAll("%area%", area);
        return this;
    }
    
    public MessageBuilder loot(String loot){
        message.replaceAll("%loot%", loot);
        return this;
    }
    
    public MessageBuilder title(String title){
        message.replaceAll("%title%", title);
        return this;
    }

    public MessageBuilder player(String player) {
        excluder = player;
        if (player == null) player = "None";
        message.replaceAll("%player%", player);
        return this;
    }

    public MessageBuilder day(String day) {
        message.replaceAll("%day%", day);
        return this;
    }

    public MessageBuilder lootAmount(int lootamount) {
        message.replaceAll("%lootamount%", lootamount + "");
        return this;
    }

    public MessageBuilder time(String time) {
        message.replaceAll("%time%", time);
        return this;
    }

    public MessageBuilder length(int length) {
        message.replaceAll("%length%", "" + length);
        return this;
    }

    public MessageBuilder id(int id) {
        message.replaceAll("%id%", "" + id);
        return this;
    }

    public MessageBuilder date(String date) {
        message.replaceAll("%date%", date);
        return this;
    }

    public MessageBuilder time(TimeObject tO) {
        message.replaceAll("%minutes%", String.format("%02d", tO.getMinutesCapped()));
        message.replaceAll("%seconds%", String.format("%02d", tO.getSecondsCapped()));
        message.replaceAll("%minutes_left%", String.format("%02d", tO.getMinutesLeft()));
        message.replaceAll("%seconds_left%", String.format("%02d", tO.getSecondsCapped()));

        return this;
    }

    public MessageBuilder maxTime(int maxTime) {
        message.replaceAll("%maxtime%", "" + ((int) maxTime / 60));
        return this;
    }

    public MessageBuilder command(String command) {
        message.replaceAll("%command%", command);
        return this;
    }

    public MessageBuilder commandInfo(String commandInfo) {
        message.replaceAll("%command_info%", commandInfo);
        return this;
    }

    public MessageBuilder shouldExcludePlayer() {
        shouldExclude = true;
        return this;
    }

    public void buildAndBroadcast() {
        String[] msg = build();
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (shouldExclude) {
                if (excluder.equalsIgnoreCase(player.getName())) {
                    continue;
                }
            }

            for (int x = 0; x < msg.length; x++) {
                player.sendMessage(msg[x]);
            }
        }

    }

    public void buildAndSend(CommandSender player) {
        String[] msg = build();
        for (int x = 0; x < msg.length; x++) {
            player.sendMessage(msg[x]);
        }
    }

    public String[] build() {
        return message.build();
    }
    
    public List<String> buildArray(){
        return Arrays.asList(message.build());
    }
}