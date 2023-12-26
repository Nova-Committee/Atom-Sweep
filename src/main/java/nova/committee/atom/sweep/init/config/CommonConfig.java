package nova.committee.atom.sweep.init.config;

import cn.evole.config.toml.AutoLoadTomlConfig;
import cn.evole.config.toml.annotation.TableField;
import org.tomlj.TomlTable;

/**
 * Name: atomsweep / CommonConfig
 * Author: cnlimiter
 * CreateTime: 2023/12/19 3:15
 * Description:
 */

public class CommonConfig extends AutoLoadTomlConfig {
    @TableField(rightComment = "扫地周期（分钟）")
    private int sweepPeriod = 4;
    @TableField(rightComment = "提前通知时间（秒）")
    private int sweepNotify = 20;
    @TableField(rightComment = "倒计时时间（秒）")
    private int sweepDiscount = 5;
    @TableField(rightComment = "通知提示")
    private String sweepNotice = "<演变> 注意：还有 {0} 秒就要去你家吃饭了~";
    @TableField(rightComment = "清扫完通知提示")
    private String sweepNoticeComplete = "<演变> 这次一共吃掉了 {0} 个掉落物， {1} 个生物 {2} 个经验球和 {3} 个其他实体~";

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
