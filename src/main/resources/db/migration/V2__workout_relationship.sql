CREATE TABLE tb_workouts (
    id UUID PRIMARY KEY,
    title VARCHAR(150),
    description TEXT,
    trainer_id UUID,
    student_id UUID NOT NULL,
    total_duration INT DEFAULT 0,
    notes TEXT,
    visibility VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_workout_trainer
        FOREIGN KEY (trainer_id)
        REFERENCES tb_users(id)
        ON DELETE SET NULL,

    CONSTRAINT fk_workout_student
        FOREIGN KEY (student_id)
        REFERENCES tb_users(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_workout_trainer_id ON tb_workouts(trainer_id);
CREATE INDEX idx_workout_student_id ON tb_workouts(student_id);

CREATE TABLE tb_workout_exercises (
    id UUID PRIMARY KEY,
    exercise_id VARCHAR(255),
    exercise_name VARCHAR(150),
    description TEXT,
    video_url VARCHAR(255),
    sets INT,
    repetitions INT,
    weight DOUBLE PRECISION,
    rest_time_seconds INT,
    duration_seconds INT,
    workout_id UUID NOT NULL,

    CONSTRAINT fk_workout_exercise_workout
        FOREIGN KEY (workout_id)
        REFERENCES tb_workouts(id)
        ON DELETE CASCADE
);

CREATE INDEX idx_workout_exercise_workout_id ON tb_workout_exercises(workout_id);