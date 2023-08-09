INSERT INTO posts(image_file_id, description, price, created, sold, car_id, user_id) VALUES
(NULL, 'Description 1', 25000, now() - INTERVAL '10 DAY', FALSE, 1, 1),
(NULL, 'Description 2', 18000, now(), FALSE, 2, 2);