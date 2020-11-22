DROP DATABASE bulletin_board;
CREATE DATABASE IF NOT EXISTS bulletin_board DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;
USE bulletin_board;
CREATE TABLE IF NOT EXISTS user
(
    id         BIGINT AUTO_INCREMENT,
    first_name VARCHAR(255)           NOT NULL,
    last_name  VARCHAR(255)           NOT NULL,
    email      VARCHAR(255)           NOT NULL,
    password   VARCHAR(255)           NOT NULL,
    phone      VARCHAR(15)            NOT NULL,
    role       ENUM ('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    balance    DECIMAL(10, 2) NOT NULL DEFAULT 0,
    premium    ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
    auto_subscribe ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
    UNIQUE (email),
    PRIMARY KEY (id)
);

USE bulletin_board;
#
# CREATE TABLE IF NOT EXISTS profile
# (
#     id         BIGINT AUTO_INCREMENT,
#     user_id    BIGINT NOT NULL  UNIQUE,
#     role       ENUM ('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
#     balance    DECIMAL(10, 2) NOT NULL DEFAULT 0,
#     premium    ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
#     auto_subscribe ENUM ('ACTIVE', 'DISABLE') NOT NULL DEFAULT 'DISABLE',
#     CONSTRAINT fk_profile_user_user_id
#         FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
#     PRIMARY KEY (id)
# );

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
    id        BIGINT AUTO_INCREMENT,
    user_id   BIGINT                     NOT NULL,
    payment DECIMAL(10, 2) NOT NULL,
    payed_at  DATETIME                   NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_payment_user_user_id
        FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    UNIQUE (user_id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS subscription
(
    id        BIGINT AUTO_INCREMENT,
    user_id   BIGINT                     NOT NULL,
    tariff_id   BIGINT                     NOT NULL,
    subscribed_at  DATETIME                   NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_subscription_user_user_id
        FOREIGN KEY (user_id) REFERENCES user (id) ON DELETE CASCADE,
    CONSTRAINT fk_subscription_tariff_tariff_id
        FOREIGN KEY (tariff_id) REFERENCES tariff (id) ON DELETE CASCADE,
    UNIQUE (user_id),
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
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS token_blacklist
(
    id         BIGINT AUTO_INCREMENT,
    token      VARCHAR(255) NOT NULL UNIQUE,
    created_at DATETIME     NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

INSERT INTO tariff (price, term, description)
VALUES (5, 1, '5$ за 1 день'),
       (12, 3, '12$ за 3 дня');

INSERT INTO user (first_name, last_name, password, email, phone, role)
VALUES ('Иван', 'Иванов', '$2a$10$wbfi9Zi4pfkC0Y3Mp7r.fOc8po9kqc0T5.nMPTq65D/qgdKBdKAre', 'ivan.ivanov@mail.ru',
        '+375331234567', 'USER'),
       ('Петр', 'Петров', '$2a$10$VvYBVKbr4xafUsgWdEHsxuOTnBhTFdzY9f1nm9p1j6x5j8TQhNZX6', 'petr.petrov@yandex.ru',
        '+375337654321', 'USER'),
       ('Алексей', 'Алексеев', '$2a$10$A4HOZGs7d65Nqd1Wz2T/kO8Zt4GZOI6CTn/Vo0Ug3OLjJsVGafwM.', 'alex.alexeev@gmail.com',
        '+375333021232', 'ADMIN');

INSERT INTO bulletin (seller_id, created_at, title, price, text)
VALUES (1, NOW(), 'Продам свадебный сервиз', 34.12, 'г. Неман'),
       (2, NOW(), 'Продам соковыжималку "Журавинка" СВСП 102П', 25, 'б/у'),
       (3, NOW(), 'Продам хомяка', 12, '2 по цене 1'),
       (1, NOW(), 'Продам отборный картофель, сорт «Вектор»', 0.45, 'БЕСПЛАТНАЯ доставка по г.Пружаны и району.');

INSERT INTO comment (author_id, bulletin_id, created_at, text)
VALUES (2, 1, NOW(), 'Отличный картофель');

insert into dialog (bulletin_id, customer_id, created_at)
values (2, 1, NOW());

insert into message (sender_id, recipient_id, dialog_id, created_at, text)
values (1, 2, 1, NOW(), 'Здравствуйте. Год выпуска?');