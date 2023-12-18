package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import cn.evole.libs.tomlj.TomlTable;
import com.google.gson.annotations.Expose;
import lombok.Getter;
import lombok.Setter;

/**
 * Name: atomsweep / CommonConfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:15
 * Description:
 */

public class CommonConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "开启q群功能")
    private int sweepPeriod = 4;
    @TableField(rightComment = "开启q群功能")
    private int sweepNotify = 20;
    @TableField(rightComment = "开启q群功能")
    private int sweepDiscount = 5;
    @TableField(rightComment = "开启q群功能")
    private String sweepNotice = "<演变> 注意：还有 %d 秒就要去你家吃饭了~";
    @TableField(rightComment = "开启q群功能")
    private String sweepNoticeComplete = "<演变> 这次一共吃掉了 %d 个掉落物， %d 个生物 %d 个经验球和 %d 个其他实体~";

    public CommonConfig() {
        super(null);
    }

    public CommonConfig(TomlTable source) {
        super(source);
        this.load(CommonConfig.class);
    }

    public String getSweepNoticeComplete() {
        return sweepNoticeComplete;
    }

    public String getSweepNotice() {
        return sweepNotice;
    }

    public int getSweepNotify() {
        return sweepNotify;
    }

    public int getSweepPeriod() {
        return sweepPeriod;
    }

    public int getSweepDiscount() {
        return sweepDiscount;
    }
}
