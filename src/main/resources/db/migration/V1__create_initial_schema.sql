CREATE TABLE users (
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(100)        NOT NULL,
    email      VARCHAR(150)        NOT NULL UNIQUE,
    password   VARCHAR(255)        NOT NULL,
    created_at TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE TABLE categories (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100)        NOT NULL,
    type        VARCHAR(50)         NOT NULL,
    user_id     BIGINT              NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    created_at  TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE TABLE transactions (
    id           BIGSERIAL PRIMARY KEY,
    description  VARCHAR(255)        NOT NULL,
    amount       NUMERIC(19, 2)      NOT NULL,
    type         VARCHAR(20)         NOT NULL CHECK (type IN ('INCOME', 'EXPENSE')),
    date         DATE                NOT NULL,
    user_id      BIGINT              NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    category_id  BIGINT              REFERENCES categories(id) ON DELETE SET NULL,
    created_at   TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at   TIMESTAMP           NOT NULL DEFAULT NOW()
);

CREATE INDEX idx_transactions_user_id    ON transactions(user_id);
CREATE INDEX idx_transactions_date       ON transactions(date);
CREATE INDEX idx_transactions_category   ON transactions(category_id);
CREATE INDEX idx_categories_user_id      ON categories(user_id);