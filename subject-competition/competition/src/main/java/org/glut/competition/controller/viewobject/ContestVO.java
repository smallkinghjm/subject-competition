package org.glut.competition.controller.viewobject;

import lombok.Data;
import java.time.LocalDate;

@Data
public class ContestVO {

    private Long contestId;

    private String state;

    private String contestName;

    private LocalDate contestStart;

    private LocalDate contestEnd;

    private String contestContent;

    private String leader;

    private String enclosureName;

}
