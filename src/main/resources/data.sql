INSERT INTO users (id, name, email, role, password, active, plan) VALUES
(1, 'picasso', 'picasso@example.com', 'ROLE_USER', '{bcrypt}$2y$10$OSN6RcjBoh1/AkQbtOkh8elkaSs.GueaU9l5tzPHl9eecv2jheRri', true, 'ARTIST'),
(2, 'remby', 'remby@example.com', 'ROLE_USER', '{bcrypt}$2y$10$DXZDfdbiTgfozb8pKEsyQu/22BoPYXX0hGD55B3/ldljBybqs3kYS', true, 'ARTIST'),
(3, 'munch', 'munch@example.com', 'ROLE_USER', '{bcrypt}$2y$10$3va09Cg7BXDqIa9uTxoPreqCE4nR6ZKXn8BUaSBpnR8GvDS9Y9iEO', true, 'ARTIST'),
(4, 'da.vinci', 'da.vinci@example.com', 'ROLE_USER', '{bcrypt}$2y$10$pDf6M4aQbgwLNRYAGppuKOA8eHPIiZWOZOD9eL0SPHF.3QFdJzn1S', true, 'ARTIST'),
(5, 'van.gogh', 'van.gogh@example.com', 'ROLE_USER', '{bcrypt}$2y$10$d7n7Im5OXW1XfAXwMM6.Ru9P7bL1aRx.g1vjpgHEp2EpAvmJCkhNq', true, 'ARTIST'),
(6, 'bosch', 'bosch@example.com', 'ROLE_USER', '{bcrypt}$2y$10$.I4U7lkafnZJDoQGKaEb6OO2MoQ0aiuh3fTv60faEw3zAlVBqW3Fm', true, 'ARTIST'),
(7, 'seurat', 'seurat@example.com', 'ROLE_USER', '{bcrypt}$2y$10$naMlMbjSMsqtmCE3YS4Ale5g5ypXom8MY3iW1V4UIpVCOkBJ32sWS', true, 'ARTIST'),
(8, 'botticeli', 'botticeli@example.it', 'ROLE_USER', '{bcrypt}$2y$10$hbBxZnk.MDA51xdpqCzRWO68QIP1BnYCPktiXPiSaHY8INUqDGxm.', true, 'ARTIST'),
(9, 'verm33r', 'verm33r@example.com', 'ROLE_USER', '{bcrypt}$2y$10$eVKa2Gq8P3B.F/yRdeuf0efsk4EJtLFharvQJKYwT03OKsb2vg4Nm', true, 'ARTIST'),
(10, 'admin', 'admin@example.com', 'ROLE_ADMIN', '{bcrypt}$2y$10$1h7VDDIWWTk7jnnF19.UC.iHcfv2h0FfTbwKq/aJlpVRIgJ6nZ5Ou', true, 'BUYER');

INSERT INTO photo (id, path, thumbnail, title, description, size, digital_price, painting_price, uploaded, user_id) VALUES
(1, 'demo_afa34a6e-2c9f-44c0-823b-44b9d0b27751.jpg', 'demo_6a4dd621-99aa-42c2-bcb0-578ec6fc183e.jpg', 'A Sunday Afternoon on Le Grande Jatte', 'I''ve sat in the park, creating numerous sketches of the various figures in order to perfect their form. I''ve concentrated on issues of colour, light, and form. The painting is approximately 2 by 3 metres in size.I''ve contrasted miniature dots or small brushstrokes of colors that when unified optically in the human eye are perceived as a single shade or hue. I believed that this form of painting would make the colors more brilliant and powerful than standard brushstrokes.', 2724, 120.55, 35740, '2020-01-01 15:21:12.41', 7),
(2, 'demo_8547faea-b736-4fef-81fe-24024b00b085.jpg', 'demo_acfc41a2-b21b-40cb-9c76-f0b7b12f21df.jpg', 'The Birth of Venus', 'In the center the newly-born goddess Venus stands nude in a giant scallop shell. The size of the shell is purely imaginary, and is also found in classical depictions of the subject. At the left the wind god Zephyr blows at her, with the wind shown by lines radiating from his mouth. He is in the air, and carries a young female, who is also blowing, but less forcefully. Both have wings. Their joint efforts are blowing Venus towards the shore.', 699, 853.77, 80799, '2020-05-13 12:37:24.12', 8),
(3, 'demo_d37d306d-13cc-4fba-ad0f-31567b586529.jpg', 'demo_ca9c382a-847a-4049-9596-5c41e033e8a1.jpg', 'Guernica', 'The scene occurs within a room where, on the left, a wide-eyed bull stands over a grieving woman holding a dead child in her arms. A horse falls in agony in the center of the room, with a large gaping hole in its side, as if it had just been run through by a spear or javelin. The horse appears to be wearing chain mail armor, decorated with vertical tally marks arranged in rows. A dead and dismembered soldier lies under the horse.', 297, 1470.30, 105000, '2020-04-17 16:56:33.07', 1),
(4, 'demo_29014dff-836e-4213-9388-faa8cad1aeab.jpg', 'demo_86e34eb4-27a3-46ae-a59c-f227a909c9fe.jpg', 'The Starry Night', 'I''ve depicted the view at different times of the day and under various weather conditions, such as the sunrise, moon rise, sunshine days, windy days, and one day with rain. It''s the view from the east-facing window of asylum room at Saint-Remy-de-Provence, just before sunrise, with the addition of an imaginary village. The pigment of the sky is painted with ultramarine and cobalt blue, and for the stars and the moon, I''ve employed the rare pigment Indian yellow together with zinc yellow.', 1843, 503.00, 50202, '2020-02-10 13:17:56.56', 5),
(5, 'demo_833680bf-ff24-485d-8c73-a17aa43b86af.jpg', 'demo_7aea73e6-59a7-46bc-9682-a110b536ec81.jpg', 'Image 5', 'Image 5 description', 724, 123, 123, '2020-03-05 07:22:46.13', 3),
(6, 'demo_940a28a9-df41-4aca-808c-007d20e646af.jpg', 'demo_a01d3cfe-254d-4b41-a655-abb690caa6a5.jpg', 'Image 6', 'Image 6 description', 336, 123, 123, '2019-12-23 06:12:37.22', 1),
(7, 'demo_27e4ce7b-a193-40c6-8b41-cbbca55b7d4b.jpg', 'demo_9c18cca1-6ed0-4f88-89c1-1de921f7dd59.jpg', 'Image 7', 'Image 7 description', 1341, 123, 123, '2020-02-22 21:02:22.41', 3),
(8, 'demo_56d923c2-fe21-446b-a9f7-d5d1c3994413.jpg', 'demo_b56ab478-4ae8-44f9-bdba-8b96ef89ccef.jpg', 'Image 8', 'Image 8 description', 801, 123, 123, '2020-03-17 17:52:17.07', 4),
(9, 'demo_67ef8361-0f72-49cf-92ae-891fc8ee52ab.jpg', 'demo_876981fd-ae78-4abd-9d08-0384620eb140.jpg', 'Image 9', 'Image 9 description', 712, 123, 123, '2019-11-08 15:41:01.03', 1),
(10, 'demo_fb95c3a5-5bd4-4901-a12b-300859901004.jpg', 'demo_cc32c390-4f76-4bdb-b42e-5c86eaf231e9.jpg', 'Image 10', 'Image 10 description', 99, 123, 123, '2019-10-12 09:31:05.00', 9),
(11, 'demo_21cc0692-a1c0-4e34-b780-4e73d551a31b.jpg', 'demo_19f0b75a-5c34-4f33-8b24-791c8db55cbd.jpg', 'Image 11', 'Image 10 description', 206, 123, 123, '2020-05-12 09:31:05.00', 2),
(12, 'demo_133b0b62-053b-4712-a9b6-8a774860e0da.jpg', 'demo_c115fa00-806b-4abc-b0c4-b1b50d78c34c.jpg', 'Image 12', 'Image 10 description', 216, 123, 123, '2019-11-12 19:27:07.00', 2),
(13, 'demo_a529d11c-5aeb-4eec-9faa-e4bd31a3dc0b.jpg', 'demo_b8e33874-287b-4379-bf3c-4fe3784e1d67.jpg', 'Image 13', 'Image 10 description', 1608, 123, 123, '2020-02-12 09:31:05.00', 6);

INSERT INTO photo_owner (user_id, photo_id) VALUES
(1, 8);

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
