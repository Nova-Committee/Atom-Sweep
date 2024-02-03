package committee.nova.atom.sweep.lib;

import dev.vankka.dependencydownload.classpath.ClasspathAppender;
import net.fabricmc.loader.impl.launch.FabricLauncherBase;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * @Project: atomsweep
 * @Author: cnlimiter
 * @CreateTime: 2024/2/4 2:24
 * @Description:
 */

@SuppressWarnings("unused")
public class FabricClasspathAppender implements ClasspathAppender {

    @Override
    public void appendFileToClasspath(@NotNull Path path) {
        FabricLauncherBase.getLauncher().addToClassPath(path);
    }
}
