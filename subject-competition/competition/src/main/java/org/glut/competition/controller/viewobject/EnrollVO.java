package org.glut.competition.controller.viewobject;

import lombok.Data;

@Data
public class EnrollVO {
    private String studentId;

    private String studentName;

    private String studentFaculty;

    private String studentClass;

    private String idCard;

    private String instructor;

    private Long contestId;

    private String contestName;
}
