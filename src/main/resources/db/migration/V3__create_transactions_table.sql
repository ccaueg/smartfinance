CREATE TABLE transactions (
    id BIGSERIAL PRIMARY KEY,
    description VARCHAR(255),
    amount NUMERIC(10,2) NOT NULL,
    category VARCHAR(50),
    type VARCHAR(50),
    date DATE,
    user_id BIGINT,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
);