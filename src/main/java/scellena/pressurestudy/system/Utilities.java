package scellena.pressurestudy.system;

import com.google.common.util.concurrent.AtomicDouble;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.hover.content.Content;
import net.md_5.bungee.api.chat.hover.content.Text;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import scellena.pressurestudy.enums.EnumLanguage;
import scellena.pressurestudy.translator.TranslatorsHandler;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Utilities {

    public static UUID commonUUID = UUID.randomUUID();
    public static UUID commonUUID2 = UUID.randomUUID();
    public static UUID commonUUID3 = UUID.randomUUID();

    public static double TRUE_DAMAGE = 50000;
    public static double INSTA_KILL = 100000;

    public static ItemStack getNamedStack(Material material, String name, List<String> description) {
        return getNamedStack(material, name, description, MainSystem.gameData.serverLanguage);
    }

    public static ItemStack getNamedStack(Material material, String name, List<String> description, Player player) {
        return getNamedStack(material, name, description, MainSystem.getPlayerData(player).language);
    }

    public static ItemStack getNamedStack(Material material, String name, List<String> description, EnumLanguage language){
        ItemStack stack = new ItemStack(material);
        ItemMeta meta = stack.getItemMeta();
        if(meta != null) {
            if(name != null) {
                meta.setDisplayName(ChatColor.RESET + TranslatorsHandler.translate(name));
            }
            if(description != null) {
                List<String> desc = new ArrayList<>();
                description.forEach(d -> {
                    desc.add(ChatColor.WHITE + TranslatorsHandler.translate(d));
                });
                meta.setLore(desc);
            }
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
            meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);
            meta.addItemFlags(ItemFlag.HIDE_DYE);
            meta.addAttributeModifier(Attribute.GENERIC_ATTACK_SPEED, new AttributeModifier(commonUUID, "clearing", 0, AttributeModifier.Operation.ADD_NUMBER));
            if(material != Material.SHIELD)meta.setUnbreakable(true);

            if(name != null) {
                PersistentDataContainer nbt = meta.getPersistentDataContainer();
                nbt.set(NamespacedKey.minecraft("translation"), PersistentDataType.STRING, removeSection(name));
            }

            stack.setItemMeta(meta);
        }
        return stack;
    }

    public static ItemStack getNamedStackSD(Material material, String name, String description, EnumLanguage lang) {
        if(description == null)return getNamedStack(material, name);
        return getNamedStack(material, name, Arrays.asList(description.split("\n")), lang);
    }

    //SDはString Descの訳
    public static ItemStack getNamedStackSD(Material material, String name, String description) {
        if(description == null)return getNamedStack(material, name);
        return getNamedStack(material, name, Arrays.asList(description.split("\n")));
    }

    public static ItemStack getNamedStack(Material material, String name) {
        return getNamedStack(material, name, (List<String>)null);
    }

    public static ItemStack getTranslatedStack(Material material, String key, Player owner) {
        return getTranslatedStack(material, key, MainSystem.getPlayerData(owner).language);
    }

    public static ItemStack getTranslatedStack(Material material, String key, EnumLanguage lang) {
        return getNamedStackSD(material, key + ".name", TranslatorsHandler.translate(key + ".desc"));
    }

    public static ItemStack getTranslatedStack(Material material, String key) {
        return getTranslatedStack(material, key, MainSystem.gameData.serverLanguage);
    }

    public static String formatString(String d){
        String s1 = ChatColor.GRAY.toString();
        String t = "0123456789+-";
        boolean isGreen = false;
        for (int i = 0; i < d.length(); i++) {
            if (t.contains(d.substring(i, i + 1))) {
                if (!isGreen) {
                    s1 += ChatColor.GREEN.toString();
                }
                isGreen = true;
            } else if(d.charAt(i) == '§'){
                s1 += d.charAt(i++);
                s1 += d.charAt(i++);
            } else if (d.charAt(i) != 's' && d.charAt(i) != '%' && d.charAt(i) != '.') {
                if (isGreen) {
                    s1 += ChatColor.GRAY.toString();
                }
                isGreen = false;
            }
            s1 += d.charAt(i);
        }
        return s1;
    }

    public static List<String> formatStringList(List<String> list){
        List<String> desc = new ArrayList<>();
        if(list != null){
            list.forEach(d -> {
                if(d.length() > 0 && d.charAt(0) == '#'){
                    desc.add(d.substring(1));
                }else {
                    desc.add(formatString(d));
                }
            });
            //desc.add("");
        }
        return desc;
    }

    public static List<Player> getPlayersInRange(Location location, double range){
        List<Player> list = new ArrayList<>();
        MainSystem.getAllPlayers().forEach((p, d) -> {
            if(p.getGameMode() != GameMode.SPECTATOR) {
                if(location.distance(p.getLocation().clone().add(0, 0.2, 0)) <= range) {
                    list.add(p);
                }else if(location.distance(p.getLocation().clone().add(0, 1.6, 0)) <= range){
                    list.add(p);
                }else{
                    double y1 = location.getY();
                    double y2 = p.getLocation().getY();
                    if(y1 >= y2 + 0.2 && y1 <= y2 + 1.6){
                        Location l1 = location.clone();
                        Location l2 = p.getLocation().clone();
                        l1.setY(0);
                        l2.setY(0);
                        if(l1.distance(l2) <= range){
                            list.add(p);
                        }
                    }
                }
            }
        });
        return list;
    }

    public static List<LivingEntity> getEntitiesInRange(Location location, double range){
        List<LivingEntity> list = new ArrayList<>();
        Bukkit.getWorlds().get(0).getEntities().forEach(p -> {
            if(p instanceof LivingEntity && !(p instanceof ArmorStand) && (!(p instanceof Player) || ((Player) p).getGameMode() != GameMode.SPECTATOR)) {
                //if((p instanceof Player && ((Player) p).getGameMode() != GameMode.SPECTATOR) || isToken(p) || isSandbag(p)) {
                    boolean hit = false;
                    for (int i = 0; i < 6; i++) {
                        if (location.distance(p.getLocation().add(0, 0.3 * i, 0)) <= range) {
                            hit = true;
                        }
                    }
                    if (hit) list.add((LivingEntity) p);
                //}
            }
        });
        return list;
    }

    public static Player getNearestPlayer(Location location, double range){
        AtomicReference<Player> ret = new AtomicReference<>();
        AtomicDouble dist_min = new AtomicDouble(range);
        MainSystem.getAllPlayers().forEach((p, d) -> {
            if(p.getGameMode() == GameMode.ADVENTURE) {
                boolean hit = false;
                for (int i = 0; i < 6; i++) {
                    double dist = location.distance(p.getLocation().add(0, 0.3 * i, 0));
                    if (dist <= dist_min.get()) {
                        hit = true;
                        dist_min.set(dist);
                    }
                }
                if (hit) {
                    ret.set(p);
                }
                ;
            }
        });
        return ret.get();
    }

    public static LivingEntity getNearestEntity(Location location, double range){
        AtomicReference<LivingEntity> ret = new AtomicReference<>();
        AtomicDouble dist_min = new AtomicDouble(range);
        Utilities.getEntitiesInRange(location, dist_min.get()).forEach(p -> {
            boolean hit = false;
            for (int i = 0; i < 6; i++) {
                double dist = location.distance(p.getLocation().add(0, 0.3 * i, 0));
                if (dist <= dist_min.get()) {
                    hit = true;
                    dist_min.set(dist);
                }
            }
            if (hit){
                ret.set(p);
            };
        });
        return ret.get();
    }

    public static void sendMessageToAll(String string, Object ... args){
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendMessage(TranslatorsHandler.translate(string, p, args));
        });
    }

    public static void sendMessageToAll(String string, List<String> desc, Object ... args){
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            sendComponentMessage(player, TranslatorsHandler.translate(string, player, args), desc);
        });
    }

    public static void sendComponentMessage(Player player, String string, List<String> desc){
        net.md_5.bungee.api.chat.TextComponent text = new net.md_5.bungee.api.chat.TextComponent();
        text.setText(string);
        List<Content> list = new ArrayList<>();
        AtomicInteger i = new AtomicInteger();
        desc.forEach(s -> {
            if(i.get() < desc.size() - 1){
                list.add(new Text(s + "\n"));
            }else{
                list.add(new Text(s));
            }
            i.getAndIncrement();
        });
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, list));
        player.spigot().sendMessage(text);
    }

    public static void sendTitleToAll(String main, String sub, int start, int stay, int stop){
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.sendTitle(main, sub, start, stay, stop);
        });
    }

    public static void sendTitleToAll(String main, String sub){
        sendTitleToAll(main, sub, 10, 60, 10);
    }

    public static void playSoundToAll(Sound sound, float pitch){
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), sound, 1, pitch);
        });
    }

    public static void playSoundToAll(String sound, float pitch){
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.playSound(p.getLocation(), sound, 1, pitch);
        });
    }

    public static ItemStack setHead(ItemStack itemStack, String nbt){
        itemStack.setType(Material.PLAYER_HEAD);
        Bukkit.getUnsafe().modifyItemStack(itemStack, nbt);
        return itemStack;
    }

    public static boolean isSandbag(Entity e){
        return e.getScoreboardTags().contains("sandbag");
    }

    public static Material getEnvironment(Entity player){
        Location loc = player.getLocation().clone();
        loc.setY(0);
        return player.getWorld().getBlockAt(loc).getType();
    }

    public static void error(Throwable e){
        Throwable e2 = e;
        if(e.getCause() != null)e2 = e.getCause();
        e2.printStackTrace();
        net.md_5.bungee.api.chat.TextComponent text = new net.md_5.bungee.api.chat.TextComponent();
        text.setText(ChatColor.RED + "[エラーデス！！]" + e2.toString() + " : " + e2.getMessage());
        List<Content> list = new ArrayList<>();
        for(StackTraceElement st : e2.getStackTrace()){
            list.add(new Text(st.toString() + "\n"));
        }
        text.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, list));
        Throwable finalE = e2;
        Bukkit.getServer().getOnlinePlayers().forEach(player -> {
            //if(MainSystem.getPlayerData(player).showExceptionMessage) {
                player.spigot().sendMessage(text);
                player.sendMessage(ChatColor.RED + "[エラーデス！！]発生箇所 : " + (finalE.getStackTrace().length == 0 ? e.getStackTrace()[0].toString() : finalE.getStackTrace()[0].toString()));
            //}
        });
    }

    public static boolean matchItem(ItemStack stack, String name){
        if(stack == null)return false;
        if(stack.getItemMeta() == null)return false;
        String str = stack.getItemMeta().getDisplayName();
        str = removeSection(str);
        name = removeSection(name);
        int i = 0;
        for(char c : str.toCharArray()){
            if(c == name.charAt(i))i++;
            if(i == name.length())break;
        }
        if(i == name.length()) return true;

        //翻訳キーチェック
        PersistentDataContainer nbt = stack.getItemMeta().getPersistentDataContainer();
        String nbtName = nbt.get(NamespacedKey.minecraft("translation"), PersistentDataType.STRING);
        if(nbtName == null)return false;
        if(nbtName.equals(name))return true;
        if(nbtName.equals(name + ".name"))return true;
        return false;
    }

    public static String removeSection(String str){
        String ret = "";
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == '§'){
                i++;
            }else{
                ret += str.charAt(i);
            }
        }
        return ret;
    }

    /**
     * 視点先のプレイヤー
     * @param player 使用者
     * @param dist 最大射程(x0.1ブロック)
     * @return 対象(いない場合null)
     */
    public static Player getStrikeThunderEnemy(Player player, int dist){
        return getStrikeThunderEnemy(player, dist, 0.8);
    }

    /**
     * 視点先のプレイヤー
     * @param player 使用者
     * @param dist 最大射程(x0.1ブロック)
     * @param hitbox 当たり判定距離
     * @return 対象(いない場合null)
     */
    public static Player getStrikeThunderEnemy(Player player, int dist, double hitbox){
        AtomicReference<Player> ans = new AtomicReference<>();
        Location pos = player.getLocation().add(0, 1.5, 0);
        for(int i=0; i<dist; i++){
            pos.add(player.getLocation().getDirection().multiply(0.1));
            if(pos.getBlock().getType() != Material.AIR)break;
            AtomicBoolean breakF = new AtomicBoolean(false);
            MainSystem.getAllPlayers().forEach((p, d) -> {
                if(Utilities.isSandbag(p)) {
                    for (int k = 0; k < 6; k++) {
                        if (p.getLocation().add(0, 0.3 * k, 0).distance(pos) <= hitbox) {
                            ans.set(p);
                            breakF.set(true);
                            break;
                        }
                    }
                }
            });
            if(breakF.get())break;
        }
        return ans.get();
    }

    public static LivingEntity getStrikeThunderEnemyEntity(Player player, int dist){
        return getStrikeThunderEnemyEntity(player, dist, 0.8);
    }

    /**
     * 視点先の任意エンティティ
     * @param player 使用者
     * @param dist 最大射程(x0.1ブロック)
     * @param hitbox 当たり判定距離
     * @return 対象(いない場合null)
     */
    public static LivingEntity getStrikeThunderEnemyEntity(Player player, int dist, double hitbox){
        AtomicReference<LivingEntity> ans = new AtomicReference<>();
        Location pos = player.getLocation().add(0, 1.5, 0);
        for(int i=0; i<dist; i++){
            pos.add(player.getLocation().getDirection().multiply(0.1));
            if(pos.getBlock().getType() != Material.AIR)break;
            AtomicBoolean breakF = new AtomicBoolean(false);
            player.getWorld().getEntities().forEach(p -> {
                if((p instanceof LivingEntity && !(p instanceof ArmorStand))) {
                    for (int k = 0; k < 6; k++) {
                        if (p.getLocation().add(0, 0.3 * k, 0).distance(pos) <= hitbox) {
                            ans.set((LivingEntity) p);
                            breakF.set(true);
                            break;
                        }
                    }
                }
            });
            if(breakF.get())break;
        }
        return ans.get();
    }

    public static boolean isAdmin(Player player){
        String name = player.getName();
        if(name.equals("ScellenaHealer"))return true;
        if(name.equals("kurisuma10"))return true;
        if(name.equals("Kureha58"))return true;
        if(name.equals("mikokonoGuilatti"))return true;
        return false;
    }

    public static ItemStack getColoredLeather(Material material, String name, List<String> desc, int red, int green, int blue){
        ItemStack stack = getNamedStack(material, name, desc);
        ItemMeta meta = stack.getItemMeta();
        if(!(meta instanceof LeatherArmorMeta))return stack;
        ((LeatherArmorMeta) meta).setColor(Color.fromRGB(red, green, blue));
        stack.setItemMeta(meta);
        return stack;
    }

    public static Vector vanillaKnockback(Entity target, float strength, double xRatio, double zRatio, double rate){
        double f = Math.sqrt(xRatio * xRatio + zRatio * zRatio);
        double multiplierToCurrent = 1 - 0.5 * rate;
        Vector vec = target.getVelocity();
        vec.setX(vec.getX() * multiplierToCurrent);
        vec.setZ(vec.getZ() * multiplierToCurrent);
        vec.setX(vec.getX() + (xRatio / (double)f * (double)strength) * rate);
        vec.setZ(vec.getZ() + (zRatio / (double)f * (double)strength) * rate);

        if (target.isOnGround())
        {
            vec.setY(vec.getY() * multiplierToCurrent + strength * rate);

            if (vec.getY() > 0.4000000059604645D) {
                vec.setY(0.4000000059604645D);
            }
        }
        target.setVelocity(vec);
        return vec;
    }

    public static List<LivingEntity> getCornEntities(Location loc, double r, double arg){
        List<LivingEntity> ans = new ArrayList<>();
        Utilities.getEntitiesInRange(loc, r).forEach(p -> {
            if(p.getLocation().toVector().add(loc.toVector().multiply(-1)).angle(loc.getDirection()) < arg){
                ans.add(p);
            }
        });
        return ans;
    }

    public static String formatTimeString(int t){
        if(t >= 200 && t % 20 == 0){
            return t / 20 + "s";
        }else{
            return String.format("%.1f", (double)t / 20.0) + "s";
        }
    }

    /**
     * 円形パーティクルを表示する.
     * @param loc 中心座標
     * @param particle パーティクル種類
     * @param range 半径
     * @param obj 特殊パラメータ(redstone_dust の色など)
     */
    public static void showParticleCircle(Location loc, Particle particle, double range, Object... obj){
        int count = (int)Math.ceil(range * 2.0);
        if(loc.getWorld() == null){
            loc.setWorld(Bukkit.getWorlds().get(0));
        }
        double frame = MainSystem.gameData.frames % 10;
        for(int i=0; i<count; i++){
            double arg = Math.PI * ((((double)i) + (frame / 10.0)) / (double)count) * 2.0;
            Location l2 = loc.clone().add(range * Math.sin(arg), 0, range * Math.cos(arg));
            if(obj.length == 0){
                l2.getWorld().spawnParticle(particle, l2, 1, 0, 0, 0, 0);
            }else{
                l2.getWorld().spawnParticle(particle, l2, 1, 0, 0, 0, obj);
            }
        }
    }

    public static String getNumberString(int id){
        if(id >= 50)return "";
        return "①②③④⑤⑥⑦⑧⑨⑩⑪⑫⑬⑭⑮⑯⑰⑱⑲⑳㉑㉒㉓㉔㉕㉖㉗㉘㉙㉚㉛㉜㉝㉞㉟㊱㊲㊳㊴㊵㊶㊷㊸㊹㊺㊻㊼㊽㊾㊿".substring(id, id + 1);
    }

    public static String getPathFromArgs(Object[] args){
        StringBuilder ans = new StringBuilder();
        for(Object a : args){
            if(ans.length() > 0)ans.append(" ");
            ans.append(a.toString());
        }
        return ans.toString();
    }

}
