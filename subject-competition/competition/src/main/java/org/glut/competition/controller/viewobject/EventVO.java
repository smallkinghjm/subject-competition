package org.glut.competition.controller.viewobject;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventVO {
    /**
     * 赛事编号
     */
    private Long eventId;

    /**
     * 标题
     */
    private String title;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;

    /**
     * 附件编号
     */
    private String enclosureName;

    private String enclosureContent;

    private String userId;
}
