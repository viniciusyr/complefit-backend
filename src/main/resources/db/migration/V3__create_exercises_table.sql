CREATE TABLE tb_exercises (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(150) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    muscle_group VARCHAR(100),
    equipment VARCHAR(100),
    difficulty VARCHAR(50),
    video_url VARCHAR(255),
    image_url VARCHAR(255)
);

CREATE INDEX idx_exercise_name ON tb_exercises(name);
CREATE INDEX idx_exercise_category ON tb_exercises(category);
CREATE INDEX idx_exercise_muscle_group ON tb_exercises(muscle_group);
CREATE INDEX idx_exercise_equipment ON tb_exercises(equipment);
CREATE INDEX idx_exercise_difficulty ON tb_exercises(difficulty);
