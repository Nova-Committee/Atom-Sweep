package nova.committee.atom.sweep;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import nova.committee.atom.sweep.util.FileUtils;

import static nova.committee.atom.sweep.Static.CONFIG_FOLDER;

@Mod(Static.MOD_ID)
public class Sweep {


    public Sweep() {
        CONFIG_FOLDER = FMLPaths.GAMEDIR.get().resolve("atom");
        FileUtils.checkFolder(CONFIG_FOLDER);

    }

}
