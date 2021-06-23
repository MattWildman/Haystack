--
-- Database: `haystack`
--

-- --------------------------------------------------------

--
-- Table structure for table `authorities`
--

CREATE TABLE IF NOT EXISTS `authorities` (
                                             `username` varchar(50) NOT NULL,
                                             `authority` varchar(50) NOT NULL DEFAULT 'ROLE_USER',
                                             UNIQUE KEY `ix_auth_username` (`username`,`authority`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `blockings`
--

CREATE TABLE IF NOT EXISTS `blockings` (
                                           `userId` int(11) NOT NULL,
                                           `blockedId` int(11) NOT NULL,
                                           PRIMARY KEY (`userId`,`blockedId`),
                                           KEY `blockedId` (`blockedId`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Table structure for table `candidates`
--

CREATE TABLE IF NOT EXISTS `candidates` (
                                            `id` int(11) NOT NULL AUTO_INCREMENT,
                                            `userId` int(11) NOT NULL,
                                            `userConId` int(11) NOT NULL,
                                            `candConId` int(11) NOT NULL,
                                            `status` varchar(30) NOT NULL DEFAULT 'pending',
                                            PRIMARY KEY (`id`),
                                            UNIQUE KEY `userId` (`userId`,`userConId`,`candConId`),
                                            KEY `userConId` (`userConId`),
                                            KEY `candConId` (`candConId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=65 ;

-- --------------------------------------------------------

--
-- Table structure for table `connections`
--

CREATE TABLE IF NOT EXISTS `connections` (
                                             `id` int(11) NOT NULL AUTO_INCREMENT,
                                             `userId` int(11) NOT NULL,
                                             `title` varchar(100) NOT NULL,
                                             `summary` text,
                                             `conType` varchar(10) NOT NULL,
                                             `status` varchar(50) NOT NULL DEFAULT 'unresolved',
                                             PRIMARY KEY (`id`),
                                             KEY `userId` (`userId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=60 ;

-- --------------------------------------------------------

--
-- Table structure for table `contexts`
--

CREATE TABLE IF NOT EXISTS `contexts` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `conId` int(11) NOT NULL,
                                          `earliest` datetime NOT NULL,
                                          `latest` datetime NOT NULL,
                                          `locationType` varchar(10) NOT NULL,
                                          PRIMARY KEY (`id`),
                                          KEY `conId` (`conId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

-- --------------------------------------------------------

--
-- Table structure for table `journeys`
--

CREATE TABLE IF NOT EXISTS `journeys` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `ctxId` int(11) NOT NULL,
                                          `startId` int(11) DEFAULT NULL,
                                          `endId` int(11) DEFAULT NULL,
                                          `type` varchar(20) NOT NULL,
                                          `company` varchar(50) DEFAULT NULL,
                                          `service` varchar(100) DEFAULT NULL,
                                          `description` text,
                                          PRIMARY KEY (`id`),
                                          KEY `ctxId` (`ctxId`),
                                          KEY `startId` (`startId`),
                                          KEY `endId` (`endId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

-- --------------------------------------------------------

--
-- Table structure for table `locations`
--

CREATE TABLE IF NOT EXISTS `locations` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `ctxId` int(11) NOT NULL,
                                           `journeyId` int(11) DEFAULT NULL,
                                           `name` varchar(100) NOT NULL,
                                           `area` varchar(100) NOT NULL,
                                           `postcode` varchar(20) DEFAULT NULL,
                                           `country` varchar(50) DEFAULT NULL,
                                           `description` text,
                                           `lat` float(8,6) DEFAULT NULL,
                                           `longd` float(9,6) DEFAULT NULL,
                                           `rad` int(11) DEFAULT NULL,
                                           PRIMARY KEY (`id`),
                                           KEY `ctxId` (`ctxId`),
                                           KEY `journeyId` (`journeyId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=65 ;

-- --------------------------------------------------------

--
-- Table structure for table `meetings`
--

CREATE TABLE IF NOT EXISTS `meetings` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `conId` int(11) NOT NULL,
                                          `userDesc` int(11) DEFAULT NULL,
                                          PRIMARY KEY (`id`),
                                          KEY `conId` (`conId`),
                                          KEY `userDesc` (`userDesc`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=55 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `messagepermissionsview`
--
CREATE TABLE IF NOT EXISTS `messagepermissionsview` (
                                                        `userId` int(11)
    ,`permittedId` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `messages`
--

CREATE TABLE IF NOT EXISTS `messages` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `toUser` int(11) NOT NULL,
                                          `fromUser` int(11) DEFAULT NULL,
                                          `msgType` varchar(20) NOT NULL,
                                          `isRead` bit(1) NOT NULL DEFAULT b'0',
                                          `subject` varchar(150) DEFAULT NULL,
                                          `content` mediumtext,
                                          `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                          `threadId` int(15) NOT NULL,
                                          PRIMARY KEY (`id`),
                                          UNIQUE KEY `id` (`id`),
                                          KEY `toUser` (`toUser`),
                                          KEY `fromUser` (`fromUser`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=198 ;

-- --------------------------------------------------------

--
-- Table structure for table `participants`
--

CREATE TABLE IF NOT EXISTS `participants` (
                                              `id` int(11) NOT NULL AUTO_INCREMENT,
                                              `meetingId` int(11) NOT NULL,
                                              `name` varchar(50) NOT NULL,
                                              `gender` varchar(20) DEFAULT NULL,
                                              `minAge` tinyint(6) DEFAULT NULL,
                                              `maxAge` tinyint(6) DEFAULT NULL,
                                              `height` int(9) DEFAULT NULL,
                                              `description` text,
                                              PRIMARY KEY (`id`),
                                              KEY `meetingId` (`meetingId`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=96 ;

-- --------------------------------------------------------

--
-- Stand-in structure for view `sharedconnectionsview`
--
CREATE TABLE IF NOT EXISTS `sharedconnectionsview` (
                                                       `conId1` int(11)
    ,`conId2` int(11)
);
-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `username` varchar(50) NOT NULL,
                                       `password` varchar(50) NOT NULL,
                                       `email` varchar(100) NOT NULL,
                                       `enabled` tinyint(1) NOT NULL DEFAULT '1',
                                       PRIMARY KEY (`id`),
                                       UNIQUE KEY `userName` (`username`),
                                       UNIQUE KEY `email` (`email`),
                                       UNIQUE KEY `username_2` (`username`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=49 ;

--
-- Triggers `users`
--
DROP TRIGGER IF EXISTS `user-auth`;
DELIMITER //
CREATE TRIGGER `user-auth` AFTER INSERT ON `users`
    FOR EACH ROW insert into authorities (username)
                 values (NEW.username)
//
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure for view `messagepermissionsview`
--
DROP TABLE IF EXISTS `messagepermissionsview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `messagepermissionsview` AS select distinct `c`.`userId` AS `userId`,`c1`.`userId` AS `permittedId` from ((`connections` `c` join `connections` `c1`) join `sharedconnectionsview` `s`) where ((`s`.`conId1` = `c`.`id`) and (`s`.`conId2` = `c1`.`id`) and (not(`c`.`userId` in (select `b`.`userId` from `blockings` `b` where (`b`.`blockedId` = `c1`.`userId`)))) and (not(`c1`.`userId` in (select `b`.`userId` from `blockings` `b` where (`b`.`blockedId` = `c`.`userId`)))));

-- --------------------------------------------------------

--
-- Structure for view `sharedconnectionsview`
--
DROP TABLE IF EXISTS `sharedconnectionsview`;

CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `sharedconnectionsview` AS select `cd`.`userConId` AS `conId1`,`cd`.`candConId` AS `conId2` from `candidates` `cd` where ((`cd`.`status` = 'accepted') and exists(select 1 from `candidates` `cd1` where ((`cd1`.`userConId` = `cd`.`candConId`) and (`cd1`.`status` = 'accepted'))) and exists(select 1 from `candidates` `cd2` where ((`cd2`.`candConId` = `cd`.`userConId`) and (`cd2`.`status` = 'accepted'))));

--
-- Constraints for dumped tables
--

--
-- Constraints for table `authorities`
--
ALTER TABLE `authorities`
    ADD CONSTRAINT `fk_authorities_users` FOREIGN KEY (`username`) REFERENCES `users` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `blockings`
--
ALTER TABLE `blockings`
    ADD CONSTRAINT `blockings_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `blockings_ibfk_2` FOREIGN KEY (`blockedId`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `candidates`
--
ALTER TABLE `candidates`
    ADD CONSTRAINT `candidates_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `candidates_ibfk_2` FOREIGN KEY (`userConId`) REFERENCES `connections` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `candidates_ibfk_3` FOREIGN KEY (`candConId`) REFERENCES `connections` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `connections`
--
ALTER TABLE `connections`
    ADD CONSTRAINT `connections_ibfk_1` FOREIGN KEY (`userId`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `contexts`
--
ALTER TABLE `contexts`
    ADD CONSTRAINT `contexts_ibfk_1` FOREIGN KEY (`conId`) REFERENCES `connections` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `items`
--
-- ALTER TABLE `items`
--    ADD CONSTRAINT `items_ibfk_1` FOREIGN KEY (`conId`) REFERENCES `connections` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `journeys`
--
ALTER TABLE `journeys`
    ADD CONSTRAINT `journeys_ibfk_1` FOREIGN KEY (`ctxId`) REFERENCES `contexts` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `journeys_ibfk_2` FOREIGN KEY (`startId`) REFERENCES `locations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `journeys_ibfk_3` FOREIGN KEY (`endId`) REFERENCES `locations` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `locations`
--
ALTER TABLE `locations`
    ADD CONSTRAINT `locations_ibfk_1` FOREIGN KEY (`ctxId`) REFERENCES `contexts` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `locations_ibfk_2` FOREIGN KEY (`journeyId`) REFERENCES `journeys` (`id`) ON DELETE CASCADE;

--
-- Constraints for table `meetings`
--
ALTER TABLE `meetings`
    ADD CONSTRAINT `meetings_ibfk_1` FOREIGN KEY (`conId`) REFERENCES `connections` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `meetings_ibfk_3` FOREIGN KEY (`userDesc`) REFERENCES `participants` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `messages`
--
ALTER TABLE `messages`
    ADD CONSTRAINT `messages_ibfk_1` FOREIGN KEY (`toUser`) REFERENCES `users` (`id`) ON DELETE CASCADE,
    ADD CONSTRAINT `messages_ibfk_2` FOREIGN KEY (`fromUser`) REFERENCES `users` (`id`) ON DELETE SET NULL;

--
-- Constraints for table `participants`
--
ALTER TABLE `participants`
    ADD CONSTRAINT `participants_ibfk_2` FOREIGN KEY (`meetingId`) REFERENCES `meetings` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
