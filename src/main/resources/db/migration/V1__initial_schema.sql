CREATE TABLE tb_users (
    id UUID PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255),
    phone_number VARCHAR(50),
    birth_date DATE,
    gender VARCHAR(20),
    height NUMERIC(5,2),
    weight NUMERIC(5,2),
    role VARCHAR(50),
    status VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE tb_trainers (
    id UUID PRIMARY KEY,
    tb_user_id UUID NOT NULL UNIQUE,
    cref VARCHAR(20) UNIQUE NOT NULL,
    specialty VARCHAR(255),
    years_of_experience INT,
    bio VARCHAR(500),
    CONSTRAINT fk_trainer_user FOREIGN KEY (tb_user_id)
        REFERENCES tb_users (id)
        ON DELETE CASCADE
);

CREATE TABLE tb_students (
    id UUID PRIMARY KEY,
    tb_user_id UUID NOT NULL UNIQUE,
    goal VARCHAR(255),
    level VARCHAR(100),
    medical_restrictions VARCHAR(500),
    CONSTRAINT fk_student_user FOREIGN KEY (tb_user_id)
        REFERENCES tb_users (id)
        ON DELETE CASCADE
);

CREATE TABLE tb_auth_users (
    id UUID PRIMARY KEY,
    refresh_token VARCHAR(255) UNIQUE NOT NULL,
    expiry_date TIMESTAMP,
    user_id UUID NOT NULL,
    CONSTRAINT fk_auth_user FOREIGN KEY (user_id)
        REFERENCES tb_users (id)
        ON DELETE CASCADE
);