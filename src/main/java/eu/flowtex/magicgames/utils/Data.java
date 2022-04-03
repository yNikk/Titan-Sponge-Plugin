package eu.flowtex.magicgames.utils;

import eu.flowtex.magicgames.manager.FileManager;
import eu.flowtex.magicgames.manager.TeamManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.entity.projectile.Arrow;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.profile.GameProfile;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.text.format.TextColor;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;
import org.spongepowered.api.world.WorldBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public class Data {
    public static User getPlayerFromUUid(final UUID id) {
        UserStorageService service = null;
        if (!Sponge.getGame().getServiceManager().provide((Class) UserStorageService.class).isPresent()) {
            System.err.println("Kein Service verf\u00fcgbar!");
            return null;
        }
        service = (UserStorageService) Sponge.getGame().getServiceManager().provide((Class) UserStorageService.class).get();
        try {
            final GameProfile profile = (GameProfile) Sponge.getGame().getServer().getGameProfileManager().get(id, false).get();
            if (profile != null && profile.getUniqueId() != null) {
                return service.getOrCreate(profile);
            }
            System.err.println("Der Server konnte die UUID " + id + " nicht fetchen.");
            return null;
        } catch (InterruptedException | ExecutionException ex2) {
            final Exception ex = null;
            final Exception e = ex;
            System.err.println("FEHLER!");
            System.err.println("Der Spieler ist nicht existent!");
            return null;
        }
    }

    public static User getPlayerFromString(final String name) {
        UserStorageService service = null;
        if (!Sponge.getGame().getServiceManager().provide((Class) UserStorageService.class).isPresent()) {
            System.err.println("Kein Service verf\u00fcgbar!");
            return null;
        }
        service = (UserStorageService) Sponge.getGame().getServiceManager().provide((Class) UserStorageService.class).get();
        try {
            final GameProfile profile = (GameProfile) Sponge.getGame().getServer().getGameProfileManager().get(name, false).get();
            return service.getOrCreate(profile);
        } catch (InterruptedException | ExecutionException ex2) {
            final Exception ex = null;
            final Exception e = ex;
            System.err.println("FEHLER!!");
            System.err.println("Der Spieler ist nicht existent!!");
            return null;
        }
    }

    public static TextColor getColorByString(final String color) {
        if (color.equalsIgnoreCase("red")) {
            return TextColors.RED;
        }
        if (color.equalsIgnoreCase("darkred")) {
            return TextColors.DARK_RED;
        }
        if (color.equalsIgnoreCase("aqua")) {
            return TextColors.AQUA;
        }
        if (color.equalsIgnoreCase("black")) {
            return TextColors.BLACK;
        }
        if (color.equalsIgnoreCase("blue")) {
            return TextColors.BLUE;
        }
        if (color.equalsIgnoreCase("darkaqua")) {
            return TextColors.DARK_AQUA;
        }
        if (color.equalsIgnoreCase("darkblue")) {
            return TextColors.DARK_BLUE;
        }
        if (color.equalsIgnoreCase("darkgray")) {
            return TextColors.DARK_GRAY;
        }
        if (color.equalsIgnoreCase("darkgreen")) {
            return TextColors.DARK_GREEN;
        }
        if (color.equalsIgnoreCase("darkpurple")) {
            return TextColors.DARK_PURPLE;
        }
        if (color.equalsIgnoreCase("gold")) {
            return TextColors.GOLD;
        }
        if (color.equalsIgnoreCase("gray")) {
            return TextColors.GRAY;
        }
        if (color.equalsIgnoreCase("green")) {
            return TextColors.GREEN;
        }
        if (color.equalsIgnoreCase("lightpurple")) {
            return TextColors.LIGHT_PURPLE;
        }
        if (color.equalsIgnoreCase("white")) {
            return TextColors.WHITE;
        }
        if (color.equalsIgnoreCase("yellow")) {
            return TextColors.YELLOW;
        }
        return null;
    }

    public static String getStringbyColor(final TextColor color) {
        if (color == TextColors.RED) {
            return "red";
        }
        if (color == TextColors.DARK_RED) {
            return "darkred";
        }
        if (color == TextColors.AQUA) {
            return "aqua";
        }
        if (color == TextColors.BLACK) {
            return "black";
        }
        if (color == TextColors.BLUE) {
            return "blue";
        }
        if (color == TextColors.DARK_AQUA) {
            return "darkaqua";
        }
        if (color == TextColors.DARK_BLUE) {
            return "darkblue";
        }
        if (color == TextColors.DARK_GRAY) {
            return "darkgray";
        }
        if (color == TextColors.DARK_GREEN) {
            return "darkgreen";
        }
        if (color == TextColors.DARK_PURPLE) {
            return "darkpurple";
        }
        if (color == TextColors.GOLD) {
            return "gold";
        }
        if (color == TextColors.GRAY) {
            return "gray";
        }
        if (color == TextColors.GREEN) {
            return "green";
        }
        if (color == TextColors.LIGHT_PURPLE) {
            return "lightpurple";
        }
        if (color == TextColors.WHITE) {
            return "white";
        }
        if (color == TextColors.YELLOW) {
            return "yellow";
        }
        return null;
    }

    public static void activateBorder() {
        final FileManager fm = FileManager.getInstance();
        final int borderradius = (int) fm.get(205, "border.borderradius");
        final int x_coord = (int) fm.get(205, "border.x_coord");
        final int z_coord = (int) fm.get(205, "border.z_coord");
        final String worldname = (String) fm.get(205, "border.world");
        if (borderradius > 0) {
            if (Sponge.getGame().getServer().getWorld(worldname).isPresent()) {
                final World w = Sponge.getGame().getServer().getWorld(worldname).get();
                final WorldBorder wb = w.getWorldBorder();
                wb.setCenter((double) x_coord, (double) z_coord);
                wb.setDiameter((double) (2 * borderradius));
                wb.setDamageAmount(1.0);
            } else {
                System.err.println("Die angegebene Welt existiert nicht");
            }
        }
    }

    public static List<Entity> getNearbyEntities(final Player p, final double distance) {
        final List<Entity> entities = new ArrayList<Entity>();
        for (final Entity e : p.getWorld().getEntities()) {
            if (getDistance((Location<?>) e.getLocation(), (Location<?>) p.getLocation()) <= distance) {
                entities.add(e);
            }
        }
        return entities;
    }

    public static double getDistance(final Location<?> loc1, final Location<?> loc2) {
        return Math.sqrt((loc1.getX() - loc2.getX()) * (loc1.getX() - loc2.getX()) + (loc1.getY() - loc2.getY()) * (loc1.getY() - loc2.getY()) + (loc1.getZ() - loc2.getZ()) * (loc1.getZ() - loc2.getZ()));
    }

    public static boolean inSameTeam(final Player p1, final Player p2) {
        final TeamManager tm = TeamManager.getInstance();
        return tm.getPlayersTeam((User) p1) != null && tm.getPlayersTeam((User) p2) != null && tm.getPlayersTeam((User) p1).getName() == tm.getPlayersTeam((User) p2).getName();
    }

    public static Player getAttackerIfPlayer(final EntityDamageSource source) {
        if (source.getSource() instanceof Player) {
            return (Player) source.getSource();
        }
        if (source.getSource() instanceof Arrow) {
            final Arrow arrow = (Arrow) source.getSource();
            if (arrow.getShooter() instanceof Player) {
                return (Player) arrow.getShooter();
            }
        }
        return null;
    }
}
