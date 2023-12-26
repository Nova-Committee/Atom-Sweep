package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoReloadToml;
import cn.evole.config.toml.TomlUtil;
import cn.evole.config.toml.annotation.Reload;
import cn.evole.config.toml.annotation.TableField;
import org.tomlj.TomlTable;
import lombok.Getter;
import lombok.Setter;
import nova.committee.atom.sweep.Static;

/**
 * Name: atomsweep / SweepConfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:13
 * Description:
 */
@Getter
@Setter
public class ModConfig extends AutoReloadToml {
    @Reload(autoReload = true)
    public static ModConfig INSTANCE = TomlUtil.readConfig(Static.CONFIG_FILE, ModConfig.class, true);
    @TableField(value = "common", topComment = "通用")
    private CommonConfig common = new CommonConfig();
    @TableField(value = "item", topComment = "物品")
    private ItemsConfig item = new ItemsConfig();
    @TableField(value = "mob", topComment = "生物")
    private MobsConfig mob = new MobsConfig();
    @TableField(value = "other", topComment = "其他")
    private OthersConfig other = new OthersConfig();


    public ModConfig() {
        super(null, Static.CONFIG_FILE);
    }

    public ModConfig(TomlTable source) {
        super(source, Static.CONFIG_FILE);
        this.load(ModConfig.class);
    }

    public void save() {
        TomlUtil.writeConfig(Static.CONFIG_FILE, INSTANCE);
    }
}
