package com.nbu.schoolbook.enums;

import lombok.Getter;

@Getter
public enum GradeValue {
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6);

    GradeValue(int value) {
    }
}
