package com.zx.bs.model;

import lombok.Data;


@Data
public class Question {

    private int question_id;
    private String question_conent;
    private User user;

}
