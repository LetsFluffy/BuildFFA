package de.letsfluffy.plorax.buildffa.utils;

import de.letsfluffy.plorax.buildffa.BuildFFA;
import net.minecraft.server.v1_8_R3.*;
import net.plorax.api.PloraxAPI;
import net.plorax.api.StatsAPI;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * (c) by Frederic Kayser(2015-2019)
 * Project: BuildFFA
 * Package: de.letsfluffy.plorax.buildffa.utils
 * Class created: 2019-02-03, 16:51
 */
public class PacketScoreboard {

    public static void updateScoreboard(final Player player) {
        final Scoreboard scoreboard = new Scoreboard();
        final ScoreboardObjective scoreboardObjective = scoreboard.registerObjective("BuildFFA", IScoreboardCriteria.b);

        scoreboardObjective.setDisplayName("§8➜ §2§lBuild§a§lFFA");

        final PacketPlayOutScoreboardObjective packetPlayOutScoreboardObjective = new PacketPlayOutScoreboardObjective(scoreboardObjective, 1);
        final PacketPlayOutScoreboardObjective packetPlayOutScoreboardObjective1 = new PacketPlayOutScoreboardObjective(scoreboardObjective, 0);
        final PacketPlayOutScoreboardDisplayObjective packetPlayOutScoreboardDisplayObjective = new PacketPlayOutScoreboardDisplayObjective(1, scoreboardObjective);

        final ScoreboardScore scoreboardScore = new ScoreboardScore(scoreboard, scoreboardObjective, "§1");
        final ScoreboardScore scoreboardScore1 = new ScoreboardScore(scoreboard, scoreboardObjective, "§8» §7Kills");
        final ScoreboardScore scoreboardScore2 = new ScoreboardScore(scoreboard, scoreboardObjective, " §8➥ §0§e" + PloraxAPI.getStatsAPI().getStats(player.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA)[0]);
        final ScoreboardScore scoreboardScore3 = new ScoreboardScore(scoreboard, scoreboardObjective, "§2");
        final ScoreboardScore scoreboardScore4 = new ScoreboardScore(scoreboard, scoreboardObjective, "§8» §7Tode");
        final ScoreboardScore scoreboardScore5 = new ScoreboardScore(scoreboard, scoreboardObjective, " §8➥ §9§e" + PloraxAPI.getStatsAPI().getStats(player.getUniqueId(), StatsAPI.StatsGameMode.BUILDFFA)[1]);
        final ScoreboardScore scoreboardScore6 = new ScoreboardScore(scoreboard, scoreboardObjective, "§3");
        final ScoreboardScore scoreboardScore7 = new ScoreboardScore(scoreboard, scoreboardObjective, "§8» §7Coins");
        final ScoreboardScore scoreboardScore8 = new ScoreboardScore(scoreboard, scoreboardObjective, " §8➥ §4§e" + PloraxAPI.getCoinAPI().getCoins(player.getUniqueId()));
        final ScoreboardScore scoreboardScore9 = new ScoreboardScore(scoreboard, scoreboardObjective, "§4");
        final ScoreboardScore scoreboardScore10 = new ScoreboardScore(scoreboard, scoreboardObjective, "§8» §7Map");
        final ScoreboardScore scoreboardScore11 = new ScoreboardScore(scoreboard, scoreboardObjective, " §8➥ §e" + BuildFFA.getBuildFFA().getMapImporter().getMap().getDisplayName());
        final ScoreboardScore scoreboardScore12 = new ScoreboardScore(scoreboard, scoreboardObjective, "§5");

        scoreboardScore.setScore(12);
        scoreboardScore1.setScore(11);
        scoreboardScore2.setScore(10);
        scoreboardScore3.setScore(9);
        scoreboardScore4.setScore(8);
        scoreboardScore5.setScore(7);
        scoreboardScore6.setScore(6);
        scoreboardScore7.setScore(5);
        scoreboardScore8.setScore(4);
        scoreboardScore9.setScore(3);
        scoreboardScore10.setScore(2);
        scoreboardScore11.setScore(1);
        scoreboardScore12.setScore(0);

        final PlayerConnection playerConnection = ((CraftPlayer)player).getHandle().playerConnection;

        playerConnection.sendPacket(packetPlayOutScoreboardObjective);
        playerConnection.sendPacket(packetPlayOutScoreboardObjective1);
        playerConnection.sendPacket(packetPlayOutScoreboardDisplayObjective);
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore1));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore2));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore3));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore4));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore5));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore6));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore7));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore8));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore9));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore10));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore11));
        playerConnection.sendPacket(new PacketPlayOutScoreboardScore(scoreboardScore12));
    }

}
