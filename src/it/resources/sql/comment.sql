--
-- user

INSERT INTO `user` (`id`, `enabled`, `email`, `name`, `password`, `username`) VALUES (1, true, 'user1@user.com', 'Javier Moreno Garcia', '$2a$10$EUQ6ntNY4f.0zcLrPezVOeDduB4KQ7afQ4FBxbXviFqlQJ6K1Ud/K', 'user1');

--
-- comment

INSERT INTO `comment` (`id`, `user_creator_id`, `description`, `title`) VALUES (1, 1, 'description1', 'title1');
