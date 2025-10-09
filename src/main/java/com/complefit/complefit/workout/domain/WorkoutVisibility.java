package com.complefit.complefit.workout.domain;

public enum WorkoutVisibility {
    PRIVATE("PRIVATE"),
    SHARED("SHARED"),
    PUBLIC("PUBLIC");

    private String visibility;

    WorkoutVisibility(String visibility){
        this.visibility = visibility;
    }
}
