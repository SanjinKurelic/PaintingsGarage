INSERT INTO users (id, username, password, active) VALUES
(1, 'picasso@example.com', '$2y$10$QZPXV6rpdkUHLa.vjJ3fSunQswqtCMfkphh2z8iAHv4uEUo1CWaMO', true),
(2, 'remby@example.com', '$2y$10$0EYWbHH.rlaiKTYYZBuqOuk1Q2hgcr8.8DUqz24jCa3msIAbJ6L8i ', true),
(3, 'munch@example.com', '$2y$10$Tt2D6tiZBgkzv3QRvNAmD.hTAJEpHSstEv7aMyzgmtDiyEBtJrfo.', true),
(4, 'da.vinci@example.com', '$2y$10$kRIx9CRqOka1jrfrIiFm5.bmbYl5bIJE4MAs7KerMQ8K8fkWoMSuu', true),
(5, 'van.gogh@example.com', '$2y$10$04/xQtHYKGRRQG3Ts/K.nO9XHavsIez1VMF1iJ9Y6MrJhQ6fqI2kC', true),
(6, 'bosch@example.com', '$2y$10$q9ZjLuPYOgbFhKEDb/I/r.WKBefbarC7EdCh1W/6X3qSCtE.6rv1C', true),
(7, 'seurat@example.com', '$2y$10$BeWfvcnN5vp2Yg8R3ogcb.gzgFHwCx7KUlzdu7pVhHV9jZ/GpTJnK', true),
(8, 'botticeli@example.it', '$2y$10$1uEY6BH1oejmLH42jnQh5e4.dIKrg1275BU6/hMcRwndBZ2rUZec6', true),
(9, 'verm33r@example.com', '$2y$10$7NoT.wbac57g4HoY3DwdKun10zSivJ4roJxH40EJ.IeoIIlIzYRmK', true);

INSERT INTO photo (id, path, thumbnail, description, size, uploaded, user_id) VALUES
(1, 'demo_afa34a6e-2c9f-44c0-823b-44b9d0b27751.jpg', 'demo_6a4dd621-99aa-42c2-bcb0-578ec6fc183e.jpg', 'Plenty of people enjoying near river.', 2724, {ts '2020-01-01 15:21:12.41'}, 7),
(2, 'demo_8547faea-b736-4fef-81fe-24024b00b085.jpg', 'demo_acfc41a2-b21b-40cb-9c76-f0b7b12f21df.jpg', 'Image description', 699, {ts '2020-05-13 12:37:24.12'}, 8),
(3, 'demo_d37d306d-13cc-4fba-ad0f-31567b586529.jpg', 'demo_ca9c382a-847a-4049-9596-5c41e033e8a1.jpg', 'Image 3 description', 297, {ts '2020-04-17 16:56:33.07'}, 1),
(4, 'demo_29014dff-836e-4213-9388-faa8cad1aeab.jpg', 'demo_86e34eb4-27a3-46ae-a59c-f227a909c9fe.jpg', 'Image 4 description', 1843, {ts '2020-02-10 13:17:56.56'}, 5),
(5, 'demo_833680bf-ff24-485d-8c73-a17aa43b86af.jpg', 'demo_7aea73e6-59a7-46bc-9682-a110b536ec81.jpg', 'Image 5 description', 724, {ts '2020-03-05 07:22:46.13'}, 3),
(6, 'demo_940a28a9-df41-4aca-808c-007d20e646af.jpg', 'demo_a01d3cfe-254d-4b41-a655-abb690caa6a5.jpg', 'Image 6 description', 336, {ts '2019-12-23 06:12:37.22'}, 1),
(7, 'demo_27e4ce7b-a193-40c6-8b41-cbbca55b7d4b.jpg', 'demo_9c18cca1-6ed0-4f88-89c1-1de921f7dd59.jpg', 'Image 7 description', 1341, {ts '2020-02-22 21:02:22.41'}, 3),
(8, 'demo_56d923c2-fe21-446b-a9f7-d5d1c3994413.jpg', 'demo_b56ab478-4ae8-44f9-bdba-8b96ef89ccef.jpg', 'Image 8 description', 801, {ts '2020-03-17 17:52:17.07'}, 4),
(9, 'demo_67ef8361-0f72-49cf-92ae-891fc8ee52ab.jpg', 'demo_876981fd-ae78-4abd-9d08-0384620eb140.jpg', 'Image 9 description', 712, {ts '2019-11-08 15:41:01.03'}, 1),
(10, 'demo_fb95c3a5-5bd4-4901-a12b-300859901004.jpg', 'demo_cc32c390-4f76-4bdb-b42e-5c86eaf231e9.jpg', 'Image 10 description', 99, {ts '2019-10-12 09:31:05.00'}, 9),
(11, 'demo_21cc0692-a1c0-4e34-b780-4e73d551a31b.jpg', 'demo_19f0b75a-5c34-4f33-8b24-791c8db55cbd.jpg', 'Image 10 description', 206, {ts '2020-05-12 09:31:05.00'}, 2),
(12, 'demo_133b0b62-053b-4712-a9b6-8a774860e0da.jpg', 'demo_c115fa00-806b-4abc-b0c4-b1b50d78c34c.jpg', 'Image 10 description', 216, {ts '2019-11-12 19:27:07.00'}, 2),
(13, 'demo_a529d11c-5aeb-4eec-9faa-e4bd31a3dc0b.jpg', 'demo_b8e33874-287b-4379-bf3c-4fe3784e1d67.jpg', 'Image 10 description', 1608, {ts '2020-02-12 09:31:05.00'}, 6);

INSERT INTO hashtag (id, name) VALUES
(1, 'people'),
(2, 'park'),
(3, 'nature'),
(4, 'female'),
(5, 'war'),
(6, 'night'),
(7, 'city'),
(8, 'sky'),
(9, 'horse'),
(10, 'river'),
(11, 'tree'),
(12, 'boat'),
(13, 'man'),
(14, 'village'),
(15, 'crop'),
(16, 'field'),
(17, 'colorful'),
(18, 'scream'),
(19, 'water'),
(20, 'coastline'),
(21, 'portrait'),
(22, 'mountains'),
(23, 'hug'),
(24, 'ship'),
(25, 'sea'),
(26, 'waves'),
(27, 'table'),
(28, 'dinner'),
(29, 'king'),
(30, 'sword'),
(31, 'drink');

INSERT INTO hashtag_photo (photo_id, hashtag_id) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 10),
(1, 11),
(1, 12),
(2, 3),
(2, 4),
(3, 1),
(3, 11),
(4, 6),
(4, 7),
(4, 8),
(4, 11),
(5, 9),
(5, 13),
(5, 14),
(5, 15),
(5, 16),
(6, 9),
(6, 17),
(7, 13),
(7, 18),
(7, 19),
(7, 20),
(8, 21),
(8, 22),
(8, 4),
(9, 4),
(9, 13),
(9, 23),
(10, 21),
(10, 4),
(11, 24),
(11, 19),
(11, 12),
(11, 25),
(11, 26),
(12, 27),
(12, 28),
(12, 29),
(12, 30),
(12, 31),
(13, 1),
(13, 3),
(13, 11),
(13, 19);