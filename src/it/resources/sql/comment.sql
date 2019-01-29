--
-- user

INSERT INTO `user` (`id`, `enabled`, `email`, `name`, `password`, `username`) VALUES (1, true, 'user1@user.com', 'Javier Moreno Garcia', '$2a$10$EUQ6ntNY4f.0zcLrPezVOeDduB4KQ7afQ4FBxbXviFqlQJ6K1Ud/K', 'user1');
INSERT INTO `user` (`id`, `enabled`, `email`, `name`, `password`, `username`) VALUES (2, true, 'user2@user.com', 'John Smith', '$2a$10$EUQ6ntNY4f.0zcLrPezVOeDduB4KQ7afQ4FBxbXviFqlQJ6K1Ud/K', 'user');

--
-- comment

INSERT INTO `comment` (`id`, `user_creator_id`, `description`, `title`) VALUES (1, 1, 'description1', 'title1');
INSERT INTO `comment` (`id`, `user_creator_id`, `description`, `title`) VALUES (2, 1, 'description2', 'title3');
INSERT INTO `comment` (`id`, `user_creator_id`, `description`, `title`) VALUES (3, 1, 'whatever3'   , 'whatever3');


INSERT INTO `comment` (`id`, `user_creator_id`, `description`, `title`) VALUES (4, 2, 'description4', 'title4');
