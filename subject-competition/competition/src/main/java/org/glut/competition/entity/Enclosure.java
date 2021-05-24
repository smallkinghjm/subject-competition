package org.glut.competition.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author 黄家满
 * @since 2021-04-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Enclosure extends Model<Enclosure> {

    private static final long serialVersionUID = 1L;

    /**
     * 附件编号
     */
    @TableId(value = "enclosure_id", type = IdType.AUTO)
    private Long enclosureId;

    /**
     * 附件名称
     */
    private String enclosureName;

    /**
     * 附件内容
     */
    private String enclosureContent;


    @Override
    protected Serializable pkVal() {
        return this.enclosureId;
    }

}
