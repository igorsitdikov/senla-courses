CREATE TABLE IF NOT EXISTS user
(
    id             BIGINT AUTO_INCREMENT,
    first_name     VARCHAR(255)               NOT NULL,
    last_name      VARCHAR(255)               NOT NULL,
    email          VARCHAR(255)               NOT NULL,
    password       VARCHAR(255)               NOT NULL,
    phone          VARCHAR(15)                NOT NULL,
    role           ENUM ('USER', 'ADMIN')     NOT NULL DEFAULT 'USER',
    balance        DECIMAL(10, 2)             NOT NULL DEFAULT 0,
    premium        ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
    auto_subscribe ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
    UNIQUE (email),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS tariff
(
    id          BIGINT AUTO_INCREMENT,
    price       DECIMAL(10, 2) NOT NULL,
    term        TINYINT(1)     NOT NULL,
    description VARCHAR(255)   NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS payment
(
    id       BIGINT AUTO_INCREMENT,
    user_id  BIGINT         NOT NULL,
    payment  DECIMAL(10, 2) NOT NULL,
    payed_at DATETIME       NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_payment_user_user_id
        FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS subscription
(
    id            BIGINT AUTO_INCREMENT,
    user_id       BIGINT   NOT NULL,
    tariff_id     BIGINT   NOT NULL,
    subscribed_at DATETIME NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_subscription_user_user_id
        FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_subscription_tariff_tariff_id
        FOREIGN KEY (tariff_id) REFERENCES tariff (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS bulletin
(
    id         BIGINT AUTO_INCREMENT,
    seller_id  BIGINT                 NOT NULL,
    created_at DATETIME               NOT NULL DEFAULT NOW(),
    title      VARCHAR(255)           NOT NULL,
    price      DECIMAL(10, 2)         NOT NULL,
    text       TEXT                   NOT NULL,
    status     ENUM ('OPEN', 'CLOSE') NOT NULL DEFAULT 'OPEN',
    CONSTRAINT fk_bulletin_user_seller_id
        FOREIGN KEY (seller_id) REFERENCES user (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS comment
(
    id          BIGINT AUTO_INCREMENT,
    author_id   BIGINT   NOT NULL,
    bulletin_id BIGINT   NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT NOW(),
    text        TEXT     NOT NULL,
    CONSTRAINT fk_comment_user_author_id
        FOREIGN KEY (author_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_comment_bulletin_bulletin_id
        FOREIGN KEY (bulletin_id) REFERENCES bulletin (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS dialog
(
    id          BIGINT AUTO_INCREMENT,
    bulletin_id BIGINT   NOT NULL,
    customer_id BIGINT   NOT NULL,
    created_at  DATETIME NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_dialog_user_customer_id
        FOREIGN KEY (customer_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_dialog_bulletin_bulletin_id
        FOREIGN KEY (bulletin_id) REFERENCES bulletin (id) ON DELETE CASCADE,
    UNIQUE unique_index (customer_id, bulletin_id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS message
(
    id           BIGINT AUTO_INCREMENT,
    sender_id    BIGINT       NOT NULL,
    recipient_id BIGINT       NOT NULL,
    dialog_id    BIGINT       NOT NULL,
    created_at   DATETIME     NOT NULL DEFAULT NOW(),
    text         VARCHAR(255) NOT NULL,
    CONSTRAINT fk_message_user_sender_id
        FOREIGN KEY (sender_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_message_user_recipient_id
        FOREIGN KEY (recipient_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_message_dialog_dialog_id
        FOREIGN KEY (dialog_id) REFERENCES dialog (id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS seller_vote
(
    id          BIGINT AUTO_INCREMENT,
    voter_id    BIGINT     NOT NULL,
    bulletin_id BIGINT     NOT NULL,
    vote        TINYINT(1) NOT NULL,
    CONSTRAINT fk_seller_vote_bulletin_bulletin_id
        FOREIGN KEY (bulletin_id) REFERENCES bulletin (id) ON DELETE CASCADE,
    CONSTRAINT fk_seller_vote_user_voter_id
        FOREIGN KEY (voter_id) REFERENCES user (id) ON DELETE CASCADE,
    UNIQUE unique_index (voter_id, bulletin_id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS token_blacklist
(
    id         BIGINT AUTO_INCREMENT,
    token      VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME     NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO user (first_name, last_name, password, email, phone, role)
VALUES ('Admin', 'Admin', '$2a$10$fCL8gaKWollUJsiE2JrNT.Yi48w.xhsamC05ppDJv/ABDGWgOJ7Ai', 'admin',
        '+375333021232', 'ADMIN');