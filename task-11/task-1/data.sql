USE hotel;
INSERT INTO attendance (name, price)
VALUES
       ('Ironing', 2.3),
       ('Wake-up', 1.5),
       ('Laundry', 4.5),
       ('Dog walking', 3.1);

INSERT INTO room (number, price, status, stars, accommodation)
VALUES
       (101, 100, 'VACANT', 'STANDARD', 'sgl' ),
       (102, 120, 'VACANT', 'JUNIOR_SUIT', 'SGL_2_CHD' ),
       (103, 150, 'VACANT', 'SUIT', 'DBL' ),
       (201, 400, 'OCCUPIED', 'DE_LUX', 'TRPL' ),
       (202, 350, 'OCCUPIED', 'FAMILY_ROOM', 'TRPL_2_CHD' ),
       (203, 300, 'REPAIR', 'STUDIO', 'SGL' ),
       (301, 300, 'VACANT', 'STUDIO', 'SGL' ),
       (302, 320, 'REPAIR', 'DUPLEX', 'DBL_EXB' ),
       (303, 440, 'OCCUPIED', 'HONEYMOON_ROOM', 'DBL' );

INSERT INTO resident (first_name, last_name, gender, vip, phone)
VALUES
       ('Mike', 'Smith', 'male', 0, '1234567'),
       ('Alex', 'Smith', 'male', 0, '1234567'),
       ('Juliet', 'Fox', 'male', 1, '1234567'),
       ('Stephani', 'Brown', 'male', 0, '1234567'),
       ('Carl', 'Patoshek', 'male', 0, '1234567');

INSERT INTO resident (first_name, last_name, email, password, phone, role)
VALUES
       ('Иван', 'Иванов', 'ivan.ivanov@mail.ru', '$2a$10$wbfi9Zi4pfkC0Y3Mp7r.fOc8po9kqc0T5.nMPTq65D/qgdKBdKAre', '+375331234567', 'CUSTOMER'),
       ('Петр', 'Петров', 'petr.petrov@yandex.ru', '$2a$10$VvYBVKbr4xafUsgWdEHsxuOTnBhTFdzY9f1nm9p1j6x5j8TQhNZX6', '+375337654321', 'CUSTOMER'),
       ('Алексей', 'Алексеев', 'alex.alexeev@gmail.com', '$2a$10$A4HOZGs7d65Nqd1Wz2T/kO8Zt4GZOI6CTn/Vo0Ug3OLjJsVGafwM.', '+375333021232', 'ADMIN');
