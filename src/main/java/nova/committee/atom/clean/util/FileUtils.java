package nova.committee.atom.clean.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Project: clean
 * Author: cnlimiter
 * Date: 2022/12/6 22:53
 * Description:
 */
public class FileUtils {
    public static void checkFolder(Path folder) {
        if (!folder.toFile().isDirectory()) {
            try {
                Files.createDirectories(folder);
            } catch (IOException var2) {
                var2.printStackTrace();
            }
        }

    }
}
