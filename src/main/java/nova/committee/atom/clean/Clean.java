package nova.committee.atom.clean;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import nova.committee.atom.clean.util.FileUtils;

import static nova.committee.atom.clean.Static.CONFIG_FOLDER;

@Mod(Static.MOD_ID)
public class Clean {


    public Clean() {
        CONFIG_FOLDER = FMLPaths.GAMEDIR.get().resolve("atom");
        FileUtils.checkFolder(CONFIG_FOLDER);

    }

}
