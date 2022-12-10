package nova.committee.atom.sweep.init.handler;


import com.google.gson.Gson;
import nova.committee.atom.sweep.Static;
import nova.committee.atom.sweep.init.config.SweepConfig;
import nova.committee.atom.sweep.util.JSONFormat;
import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;


public class ConfigHandler {
    private static final Gson GSON = new Gson();

    public static SweepConfig load() {
        SweepConfig config = new SweepConfig();

        if (!Static.CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(Static.CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Path configPath = Static.CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        if (configPath.toFile().isFile()) {
            try {
                config = GSON.fromJson(FileUtils.readFileToString(configPath.toFile(), StandardCharsets.UTF_8),
                        SweepConfig.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return config;
    }

    public static void save(SweepConfig config) {
        if (!Static.CONFIG_FOLDER.toFile().isDirectory()) {
            try {
                Files.createDirectories(Static.CONFIG_FOLDER);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Path configPath = Static.CONFIG_FOLDER.resolve(config.getConfigName() + ".json");
        try {
            FileUtils.write(configPath.toFile(), JSONFormat.formatJson(GSON.toJson(config)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void onChange(){
        ConfigHandler.save(Static.config);
        Static.config = ConfigHandler.load();
    }
}
