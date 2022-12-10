package nova.committee.atom.sweep.init.handler;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import nova.committee.atom.sweep.common.cmd.SweepCommand;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/4/8 16:15
 * Version: 1.0
 */
public class CmdHandler {
    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, commandBuildContext, commandSelection) -> {
            SweepCommand.register(dispatcher);
        });
    }
}
