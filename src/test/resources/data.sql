SET REFERENTIAL_INTEGRITY FALSE;
TRUNCATE TABLE hashtag_photo;
TRUNCATE TABLE hashtag;
TRUNCATE TABLE photo_owner;
TRUNCATE TABLE photo;
TRUNCATE TABLE users;
SET REFERENTIAL_INTEGRITY TRUE;

INSERT INTO users (id, name, email, role, password, active, plan) VALUES
(1, '_test', '_test', 'ROLE_USER', '{bcrypt}$2y$10$X/YzXTfaPrizK3Z01PgDdeQJVO0ogOn6atCHaV1bfCI8mJefiEZKS', true, 'ARTIST'),
(2, '_test2', '_test2', 'ROLE_USER', '{bcrypt}$2y$10$X/YzXTfaPrizK3Z01PgDdeQJVO0ogOn6atCHaV1bfCI8mJefiEZKS', true, 'ARTIST'),
(3, '_t3', '_t3', 'ROLE_USER', '{bcrypt}$2y$10$X/YzXTfaPrizK3Z01PgDdeQJVO0ogOn6atCHaV1bfCI8mJefiEZKS', true, 'ARTIST'),
(4, '_admin', '_admin', 'ROLE_ADMIN', '{bcrypt}$2y$10$X/YzXTfaPrizK3Z01PgDdeQJVO0ogOn6atCHaV1bfCI8mJefiEZKS', true, 'ARTIST');

INSERT INTO photo (id, path, thumbnail, title, description, size, digital_price, painting_price, uploaded, user_id) VALUES
(1, '_test.jpg', '_th_test.jpg', '_test', 'Some description', 2724, 120.55, 35740, '2020-01-01 12:00:00.00', 1);

INSERT INTO photo_owner (user_id, photo_id) VALUES
(1, 1);

INSERT INTO hashtag (id, name) VALUES
(1, '_test'),
(2, '_test2'),
(3, '_t3');

INSERT INTO hashtag_photo (photo_id, hashtag_id) VALUES
(1, 1);