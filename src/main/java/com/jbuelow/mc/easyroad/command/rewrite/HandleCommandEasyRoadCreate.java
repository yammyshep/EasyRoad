package com.jbuelow.mc.easyroad.command.rewrite;

import com.github.overmighty.croissant.command.CommandExecutor;
import com.github.overmighty.croissant.command.CroissantCommand;
import com.github.overmighty.croissant.command.argument.Optional;
import com.github.overmighty.croissant.command.argument.Rest;
import com.jbuelow.mc.easyroad.EasyRoad;
import com.jbuelow.mc.easyroad.Session;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class HandleCommandEasyRoadCreate extends CroissantCommand {

    private EasyRoad easyRoad;

    public HandleCommandEasyRoadCreate(EasyRoad easyRoad) {
        //configure
        super("create");
        super.setDescription("Creates a new road.");
        super.setUsage("/easyroad create [road name]");
        super.setAliases("c", "cr");
        super.setPlayerOnly(true);

        this.easyRoad = easyRoad;
    }

    @CommandExecutor
    public void run(Player sender, @Optional @Rest String roadname) {
        HashMap<Player, Session> sessions = easyRoad.getActiveSessions();
        if (sessions.containsKey(sender)) {
            sender.sendMessage(ChatColor.RED + "Error: you are already in an EasyRoad session!\nType " + ChatColor.YELLOW + "/easyroad stop" + ChatColor.BLUE + " to exit");
            return;
        }

        Session s;
        if (roadname == null) {
            s = new Session(easyRoad, sender);
        } else {
            s = new Session(easyRoad, sender, roadname);
        }

        s.enable();
        sessions.put(sender, s);

        String uid = s.getRoad().getUUID().toString();

        sender.sendMessage(ChatColor.BLUE + "You are now in an EasyRoad session.\nType " + ChatColor.YELLOW + "/easyroad stop" + ChatColor.BLUE + " to exit");
        sender.sendMessage(ChatColor.BLUE + "Editing road \"" + roadname + "\"\n" + ChatColor.DARK_BLUE + "(id: "+ChatColor.GRAY+uid+ChatColor.DARK_BLUE+")");
    }
}