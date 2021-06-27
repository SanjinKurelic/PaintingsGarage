INSERT INTO users (id, username, password, active) VALUES
(1, 'john@example.com', '$2y$10$ewC4zQd7AD46nqOU.QyT1.xdaIetI5f8SDlxviIwb5kEceSZznp/O', true),
(2, 'ann@example.com', '$2y$10$a2UvboMcyT.CpwESz6dU8.tDphmXnSvj4YlgNk4CDfe8oE5MFJn/y ', true),
(3, 'adam@example.com', '$2y$10$IY/r6fVI0fsXCbKWJ/6Tquc7Ix1A2yEcib28ZRYPghq9g9jbRtPJq', true),
(4, 'julia@example.com', '$2y$10$yzqWQYxxUYXJ0j06Z/akZ.WzNTN6B0/FMyQRrwdGxA5QO2BsCO6Oq', true);

INSERT INTO photo (id, path, thumbnail, description, size, uploaded, user_id) VALUES
(1, 'photo1', 'thumbnail1', 'Image 1 description', 120, {ts '2020-01-01 15:21:12.41'}, 1),
(2, 'photo2', 'thumbnail2', 'Image 2 description', 300, {ts '2020-05-13 12:37:24.12'}, 1),
(3, 'photo3', 'thumbnail3', 'Image 3 description', 75, {ts '2020-04-17 16:56:33.07'}, 2),
(4, 'photo4', 'thumbnail4', 'Image 4 description', 412, {ts '2020-02-10 13:17:56.56'}, 2),
(5, 'photo5', 'thumbnail5', 'Image 5 description', 1024, {ts '2020-03-05 07:22:46.13'}, 3),
(6, 'photo6', 'thumbnail6', 'Image 6 description', 300, {ts '2019-12-23 06:12:37.22'}, 3),
(7, 'photo7', 'thumbnail7', 'Image 7 description', 305, {ts '2019-12-22 21:02:22.41'}, 3),
(8, 'photo8', 'thumbnail8', 'Image 8 description', 703, {ts '2020-03-17 17:52:17.07'}, 4),
(9, 'photo9', 'thumbnail9', 'Image 9 description', 290, {ts '2020-04-08 15:41:01.03'}, 4),
(10, 'photo10', 'thumbnail10', 'Image 10 description', 291, {ts '2020-05-12 09:31:05.00'}, 4);

INSERT INTO hashtag (id, name) VALUES
(1, 'hashtag1'),
(2, 'hashtag2'),
(3, 'hashtag3'),
(4, 'hashtag4'),
(5, 'hashtag5'),
(6, 'hashtag6'),
(7, 'hashtag7'),
(8, 'hashtag8'),
(9, 'hashtag9'),
(10, 'hashtag10'),
(11, 'hashtag11');

INSERT INTO hashtag_photo (photo_id, hashtag_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 3),
(2, 4),
(2, 5),
(3, 3),
(3, 6),
(3, 7),
(4, 1),
(4, 3),
(4, 8),
(5, 3),
(5, 8),
(6, 9),
(7, 1),
(7, 5),
(7, 9),
(8, 10),
(8, 11),
(9, 11),
(10, 3);