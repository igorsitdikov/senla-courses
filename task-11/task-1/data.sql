
insert into attendance (name, price)
values
       ('Ironing', 2.3),
       ('Wake-up', 1.5),
       ('Laundry', 4.5),
       ('Dog walking', 3.1);

insert into room (number, price, status, stars, accommodation)
values
       (101, 100, 'VACANT', 'STANDARD', 'sgl' ),
       (102, 120, 'VACANT', 'JUNIOR_SUIT', 'SGL_2_CHD' ),
       (103, 150, 'VACANT', 'SUIT', 'DBL' ),
       (201, 400, 'OCCUPIED', 'DE_LUX', 'TRPL' ),
       (202, 100, 'OCCUPIED', 'FAMILY_ROOM', 'TRPL_2_CHD' ),
       (203, 100, 'REPAIR', 'STUDIO', 'SGL' ),
       (301, 100, 'VACANT', 'STUDIO', 'SGL' ),
       (302, 100, 'REPAIR', 'DUPLEX', 'DBL_EXB' ),
       (303, 100, 'OCCUPIED', 'HONEYMOON_ROOM', 'DBL' );

insert into resident (first_name, last_name, gender, vip, phone)
values
       ('Mike', 'Smith', 'male', 0, '1234567'),
       ('Alex', 'Smith', 'male', 0, '1234567'),
       ('Juliet', 'Fox', 'male', 1, '1234567'),
       ('Stephani', 'Brown', 'male', 0, '1234567'),
       ('Carl', 'Patoshek', 'male', 0, '1234567');
