package nova.committee.atom.sweep;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.cacheddata.CachedPermissionData;
import net.luckperms.api.util.Tristate;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.server.ServerLifecycleHooks;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
//#endif
/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 8:57
 * Version: 1.0
 */
public class Static {
    public static final String MOD_ID = "atomsweep";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static Path CONFIG_FILE;
    public static Path CONFIG_FOLDER;
    public static MinecraftServer SERVER = ServerLifecycleHooks.getCurrentServer();
    public static boolean isLuckPerms = false;

    public static void sendMessage(Player player, String message) {
        //#if MC >= 11900
        player.sendSystemMessage(Component.translatable(message));
        //#else
        //$$ player.sendMessage(new TranslatableComponent("message"), Util.NIL_UUID);
        //#endif
    }


    public static void sendMessageToAllPlayers(Component message, boolean actionBar) {
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList().getPlayers()
                .forEach(player -> player.displayClientMessage(message, actionBar)))).start();
    }


    public static void sendMessageToAllPlayers(MinecraftServer server1, String message, Object... args) {

        //#if MC >= 11900
        new Thread(() -> Optional.ofNullable(server1).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(MessageFormat.format(message, args)), true)))
                .start();
        //#else
        //$$ new Thread(() -> Optional.ofNullable(server1).ifPresent(server -> server.getPlayerList()
        //$$               .broadcastMessage(new TextComponent(MessageFormat.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
        //$$               .start();
        //#endif


    }

    public static void sendMessageToAllPlayers(String message, Object... args) {
        //#if MC >= 11900
        new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
                .broadcastSystemMessage(Component.literal(MessageFormat.format(message, args)), true)))
                .start();
        //#else
        //$$ new Thread(() -> Optional.ofNullable(SERVER).ifPresent(server -> server.getPlayerList()
        //$$              .broadcastMessage(new TextComponent(MessageFormat.format(message, args)), ChatType.SYSTEM, Util.NIL_UUID)))
        //$$              .start();
        //#endif
    }


    public static Boolean hasPermission(ServerPlayer playerEntity, String permission) throws CommandSyntaxException {
        AtomicReference<Boolean> exist = new AtomicReference<>(false);

        LuckPermsProvider.get().getUserManager().loadUser(playerEntity.getUUID())
                .thenApplyAsync(user -> {
                    CachedPermissionData permissionData = user.getCachedData()
                            .getPermissionData(user.getQueryOptions());
                    Tristate tristate = permissionData.checkPermission(permission);
                    if (tristate.equals(Tristate.UNDEFINED)) {
                        return false;
                    }

                    return tristate.asBoolean();
                }).thenAcceptAsync(aBoolean -> {
                    if (aBoolean)
                        exist.set(true);
                });

        return exist.get();
    }

    public static Boolean cmdPermission(CommandSourceStack source, String permission, boolean admin) {
        if (!Static.isLuckPerms)
            if (admin)
                return source.hasPermission(2);
            else
                return true;
        else
            try {
                return Static.hasPermission(source.getPlayerOrException(), permission);
            } catch (CommandSyntaxException e) {
                throw new RuntimeException(e);
            }
    }

}
