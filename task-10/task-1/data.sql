INSERT INTO product (maker, model, type) VALUES
('A', 'MacBook Pro 16', 'Laptop'),
('A', 'MacBook Air 13', 'Laptop'),
('A', 'iMac 27', 'PC'),
('A', 'iMac Pro', 'PC'),
('A', 'iMac 21', 'PC'),
('B', 'ROG', 'Laptop'),
('B', 'ProArt', 'Laptop'),
('B', 'Vivo AiO', 'PC'),
('B', 'Zen AiO', 'PC'),
('C', 'ProBook 4320', 'Laptop'),
('C', 'Pavilion', 'Laptop'),
('C', 'ENVY', 'PC'),
('C', 'Laser', 'Printer'),
('C', 'Neverstop', 'Printer'),
('C', 'Laserjet Color', 'Printer'),
('D', 'Mi Book Air', 'Laptop'),
('D', 'Mi Book Pro', 'Laptop'),
('E', 'IdeaCentre 520', 'PC'),
('F', 'Gaming 24', 'PC'),
('G', 'i-SENSYS', 'Printer'),
('H', 'Phaser', 'Printer'),
('I', 'PIXMA', 'Printer');

INSERT INTO laptop (code, model, speed, ram, hd, screen, price) VALUES
(00001, 'MacBook Pro 16', 1800, 4096, 500, 11, 800),
(00002, 'MacBook Air 13', 1800, 8192, 128, 13, 950),
(00003, 'ProBook 4320', 1600, 3072, 1000, 17, 400),
(00004, 'Pavilion', 2500, 8192, 1000, 15, 750),
(00005, 'ROG', 2500, 4096, 500, 15, 650),
(00006, 'ProArt', 2500, 8192, 256, 15, 1240),
(00007, 'Mi Book Pro', 1600, 8192, 256, 15, 1150),
(00008, 'Mi Book Air', 2300, 8192, 256, 13, 275);

INSERT INTO pc (code, model, speed, ram, hd, cd, price) VALUES
(10001, 'iMac 27', 3800, 8192, 2000, '48x', 2700),
(10002, 'iMac Pro', 3200, 8192, 1000, '48x', 6300),
(10003, 'iMac 21', 2600, 8192, 1000, '24x', 1000),
(10004, 'IdeaCentre 520', 3400, 4096, 1000, '24x', 750),
(10005, 'Vivo AiO', 2500, 4096, 1000, '8x', 650),
(10006, 'Zen AiO', 2400, 8192, 2000, '12x', 1200),
(10007, 'ENVY', 3900, 4096, 128, '12x', 490),
(10008, 'Gaming 24', 2400, 8192, 256, '4x', 375);

INSERT INTO printer (code, model, color, type, price) VALUES
(20001, 'i-SENSYS', 'n', 'Laser', 95),
(20002, 'Phaser', 'y', 'Jet', 187),
(20003, 'PIXMA', 'n', 'Matrix', 20),
(20004, 'Laser', 'n', 'Laser', 120),
(20005, 'Neverstop', 'n', 'Jet', 90),
(20006, 'Laserjet Color', 'y', 'Laser', 350);
