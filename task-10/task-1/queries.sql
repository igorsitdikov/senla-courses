-- task 1
SELECT model, speed, hd
  FROM pc
 WHERE price < 500;
-- task 2
SELECT DISTINCT maker
  FROM product
 WHERE type = 'Printer';
-- task 3
SELECT model, ram, screen
  FROM laptop
 WHERE price > 1000;
-- task 4
SELECT code, model, color, type, price
  FROM printer
 WHERE color = 'y';
-- task 5
SELECT model, speed, ram
  FROM pc
 WHERE cd IN ('24x', '48x')
   AND price > 600;
-- task 6
SELECT DISTINCT maker, speed
  FROM laptop
       JOIN product AS p
       ON p.model = laptop.model
 WHERE hd >= 100;
-- task 7
SELECT pc.model, price
  FROM pc
       JOIN product p
       ON p.model = pc.model
 WHERE maker = 'B'

 UNION

SELECT l.model, price
  FROM laptop AS l
       JOIN product AS p
       ON p.model = l.model
 WHERE maker = 'B'

 UNION

SELECT pr.model, price
  FROM printer pr
       JOIN product p
       ON p.model = pr.model
 WHERE maker = 'B';
-- task 8
SELECT DISTINCT maker
  FROM product
 WHERE type = 'PC'
   AND maker NOT IN
       (SELECT maker
          FROM product
         WHERE type = 'Laptop');
-- task 9
SELECT DISTINCT maker
  FROM product AS p
       JOIN pc
       ON pc.model = p.model
 WHERE type = 'PC'
   AND speed >= 450;
-- task 10
SELECT model, price
  FROM printer
 WHERE price = (
       SELECT MAX(price)
         FROM printer);
-- task 11
SELECT AVG(speed) AS avg_speed
  FROM pc;
-- task 12
SELECT AVG(speed) AS avg_speed
  FROM laptop
 WHERE price > 1000;
-- task 13
SELECT AVG(speed) AS avg_speed
  FROM pc
       JOIN product p
       ON pc.model = p.model
 WHERE maker = 'A';
-- task 14
SELECT speed, AVG(price)
  FROM pc
 GROUP BY speed;
-- task 15
SELECT hd
  FROM pc
 GROUP BY hd
HAVING COUNT(model) >= 2;
-- task 16
SELECT DISTINCT A.model AS model_1, B.model AS model_2, A.speed, A.ram
  FROM pc AS A, pc B
 WHERE A.speed = B.speed
   AND A.ram = B.ram
   AND A.model > B.model;
-- task 17
SELECT DISTINCT type, laptop.model, laptop.speed
  FROM product, laptop
 WHERE type = 'Laptop'
   AND laptop.speed < ALL (
       SELECT pc.speed
       FROM pc);
-- task 18
SELECT DISTINCT maker, price
  FROM product
       JOIN printer
       ON printer.model = product.model
 WHERE printer.price = (
       SELECT MIN(printer.price)
       FROM printer
       WHERE printer.color = 'y')
   AND printer.color = 'y';
-- task 19
SELECT maker, AVG(screen)
  FROM product
       JOIN laptop
       ON laptop.model = product.model
 GROUP BY maker;
-- task 20
SELECT maker, COUNT(model)
  FROM product
 WHERE type = 'PC'
 GROUP BY product.maker
HAVING COUNT(DISTINCT model) >= 3;
-- task 21
SELECT DISTINCT maker, MAX(pc.price)
  FROM product
       JOIN pc
       ON pc.model = product.model
 GROUP BY maker;
-- task 22
SELECT speed, AVG(price)
  FROM pc
 WHERE speed > 600
 GROUP BY speed;
--task 23
SELECT p.maker
  FROM product AS p
       JOIN laptop AS l
       ON l.model = p.model
       AND l.speed >= 750
 WHERE p.maker IN (
       SELECT maker
         FROM product AS p
              JOIN pc
              ON pc.model = p.model
              AND pc.speed >= 750
        GROUP BY maker)
 GROUP BY p.maker;
--task 24
SELECT p1.model
  FROM product AS p1
       JOIN (
            SELECT model, price
              FROM laptop

            UNION

            SELECT model, price
              FROM pc

             UNION

            SELECT model, price
              FROM printer
            ) AS p2
       ON p1.model = p2.model
 WHERE p2.price = (
       SELECT max(p.price)
         FROM (
              SELECT max(p2.price) AS price
                FROM product AS p1
                     JOIN pc p2
                     ON p2.model = p1.model

              UNION

              SELECT max(p2.price) AS price
                FROM product AS p1
                     JOIN laptop p2
                     ON p2.model = p1.model

              UNION

              SELECT max(p2.price) AS price
                FROM product AS p1
                     JOIN printer p2
                     ON p2.model = p1.model
              ) AS p)
 GROUP BY p1.model;
--task 25
SELECT maker
  FROM product AS p2
 WHERE p2.type
  LIKE 'Printer'
   AND p2.maker IN
       (SELECT p.maker
          FROM product AS p
         WHERE p.model IN
               (SELECT pc.model
                  FROM pc
                 WHERE pc.speed = (
                       SELECT max(speed)
                         FROM pc
                        WHERE ram = (
                              SELECT min(ram)
                              FROM pc))
                   AND pc.ram = (
                       SELECT min(ram)
                         FROM pc)
                 GROUP BY pc.model)
         GROUP BY maker)
 GROUP BY maker
