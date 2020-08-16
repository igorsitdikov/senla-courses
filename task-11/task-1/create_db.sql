DROP DATABASE hotel;

CREATE DATABASE IF NOT EXISTS hotel DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci;

USE hotel;

CREATE TABLE IF NOT EXISTS attendance (
    id BIGINT NOT NULL AUTO_INCREMENT,
    price DECIMAL(10, 2) NOT NULL,
    name VARCHAR(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS resident (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    gender ENUM(
        'FEMALE',
        'MALE') NOT NULL,
    vip TINYINT(1) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS room (
    id BIGINT NOT NULL AUTO_INCREMENT,
    number INT NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    status ENUM(
        'VACANT',
        'OCCUPIED',
        'REPAIR') NOT NULL,
    stars ENUM(
        'JUNIOR_SUIT',
        'SUIT',
        'DE_LUX',
        'DUPLEX',
        'FAMILY_ROOM',
        'STUDIO',
        'STANDARD',
        'APARTMENT',
        'HONEYMOON_ROOM') NOT NULL,
    accommodation ENUM(
        'SGL',
        'SGL_CHD',
        'SGL_2_CHD',
        'DBL',
        'DBL_EXB',
        'DBL_CHD',
        'DBL_EXB_CHD',
        'TRPL',
        'TRPL_CHD',
        'TRPL_2_CHD') NOT NULL,
    PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS history (
    id BIGINT NOT NULL AUTO_INCREMENT,
    room_id BIGINT NOT NULL,
    resident_id BIGINT NOT NULL,
    check_in DATETIME NOT NULL,
    check_out DATETIME NOT NULL,
    CONSTRAINT room_history_fk
    FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
    CONSTRAINT resident_history_fk
    FOREIGN KEY (resident_id) REFERENCES resident(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

-- CREATE TABLE IF NOT EXISTS rooms_histories (
--     id LONG NOT NULL AUTO_INCREMENT,
--     room_id LONG NOT NULL,
--     history_id LONG NOT NULL,
--     CONSTRAINT rh_room_fk
--     FOREIGN KEY (room_id) REFERENCES room(id) ON DELETE CASCADE,
--     CONSTRAINT rh_history_fk
--     FOREIGN KEY (history_id) REFERENCES history(id) ON DELETE CASCADE,
--     PRIMARY KEY (id)
-- );

CREATE TABLE IF NOT EXISTS histories_attendances (
    id BIGINT NOT NULL AUTO_INCREMENT,
    attendance_id BIGINT NOT NULL,
    history_id BIGINT NOT NULL,
    CONSTRAINT ha_attendance_fk
    FOREIGN KEY (attendance_id) REFERENCES attendance(id) ON DELETE CASCADE,
    CONSTRAINT ha_history_fk
    FOREIGN KEY (history_id) REFERENCES history(id) ON DELETE CASCADE,
    PRIMARY KEY (id)
);
