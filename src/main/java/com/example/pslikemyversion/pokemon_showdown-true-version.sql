-- phpMyAdmin SQL Dump
-- version 5.2.3
-- https://www.phpmyadmin.net/
--
-- Hôte : db
-- Généré le : mer. 18 mars 2026 à 22:26
-- Version du serveur : 5.7.44
-- Version de PHP : 8.3.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `pokemon_showdown`
--

-- --------------------------------------------------------

--
-- Structure de la table `ability`
--

CREATE TABLE `ability` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `ability`
--

INSERT INTO `ability` (`id`, `name`, `description`) VALUES
(1, 'Fouille', 'Permet de voir l\'objet de l\'adversaire.'),
(2, 'Sans Limite', 'Booste les attaques avec effet secondaire.'),
(3, 'Synchro', 'Transmet les problèmes de statut.'),
(4, 'Statik', 'Peut paralyser au contact.'),
(5, 'Intimidation', 'Baisse l\'Attaque de l\'adversaire.'),
(6, 'Cran', 'Booste l\'Attaque si statut négatif.'),
(7, 'Épée du Fléau', 'Baisse la Défense des autres.'),
(8, 'Piège Sable', 'Empêche l\'adversaire de s\'enfuir.');

-- --------------------------------------------------------

--
-- Structure de la table `attack`
--

CREATE TABLE `attack` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `power` int(11) DEFAULT NULL,
  `accuracy` int(11) DEFAULT '100',
  `pp` int(11) NOT NULL,
  `type_id` int(11) NOT NULL,
  `category` enum('Physical','Special','Status') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `attack`
--

INSERT INTO `attack` (`id`, `name`, `power`, `accuracy`, `pp`, `type_id`, `category`) VALUES
(1, 'Draco-Choc', 85, 100, 10, 15, 'Special'),
(2, 'Boutefeu', 120, 100, 15, 2, 'Physical'),
(3, 'Psyko', 90, 100, 10, 11, 'Special'),
(4, 'Telluriforce', 90, 100, 10, 9, 'Special'),
(5, 'Vampipoing', 75, 100, 10, 7, 'Physical'),
(6, 'Chute de Glace', 85, 90, 10, 6, 'Physical'),
(7, 'Retour', 80, 100, 20, 1, 'Physical'),
(8, 'Ball\'Ombre', 80, 100, 15, 14, 'Special'),
(9, 'Séisme', 100, 100, 10, 9, 'Physical'),
(10, 'Tonnerre', 90, 100, 15, 5, 'Special'),
(11, 'Lame de Roc', 100, 80, 5, 13, 'Physical'),
(12, 'Mach Punch', 40, 100, 30, 7, 'Physical'),
(13, 'Poing-Eclair', 75, 100, 15, 5, 'Physical'),
(14, 'Poing-Feu', 75, 100, 15, 2, 'Physical'),
(15, 'Direct Toxik', 80, 100, 20, 8, 'Physical'),
(16, 'Mâchouille', 80, 100, 15, 16, 'Physical'),
(17, 'Tranche-Nuit', 70, 100, 15, 16, 'Physical'),
(18, 'Giga-Sangsue', 75, 100, 10, 4, 'Special'),
(19, 'Vive-Attaque', 40, 100, 30, 1, 'Physical'),
(20, 'Eboulement', 75, 90, 10, 13, 'Physical');

-- --------------------------------------------------------

--
-- Structure de la table `item`
--

CREATE TABLE `item` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `item`
--

INSERT INTO `item` (`id`, `name`, `description`) VALUES
(1, 'Life Orb', 'Augmente les dégâts de 30% mais retire 10% de PV à chaque attaque.'),
(2, 'Leftovers', 'Rend un peu de PV à chaque fin de tour.'),
(3, 'Choice Band', 'Augmente l\'Attaque de 50% mais bloque sur la première capacité choisie.');

-- --------------------------------------------------------

--
-- Structure de la table `pokemon`
--

CREATE TABLE `pokemon` (
  `id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type1_id` int(11) NOT NULL,
  `type2_id` int(11) DEFAULT NULL,
  `ability_id` int(11) DEFAULT NULL,
  `hp` int(11) NOT NULL,
  `attack` int(11) NOT NULL,
  `defense` int(11) NOT NULL,
  `sp_attack` int(11) NOT NULL,
  `sp_defense` int(11) NOT NULL,
  `speed` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pokemon`
--

INSERT INTO `pokemon` (`id`, `name`, `type1_id`, `type2_id`, `ability_id`, `hp`, `attack`, `defense`, `sp_attack`, `sp_defense`, `speed`) VALUES
(1, 'Noadkoko d\'Alola', 4, 15, 1, 95, 105, 85, 125, 75, 45),
(2, 'Darumacho', 2, NULL, 2, 105, 140, 55, 30, 55, 95),
(3, 'Alakazam', 11, NULL, 3, 55, 50, 45, 135, 95, 120),
(4, 'Limonde', 9, 5, 4, 109, 66, 84, 81, 99, 32),
(5, 'Cerfrousse', 1, NULL, 5, 73, 95, 62, 85, 65, 85),
(6, 'Betochef', 7, NULL, 6, 105, 140, 95, 55, 65, 45),
(7, 'Baojian', 16, 6, 7, 80, 120, 80, 90, 65, 135),
(8, 'Kraknoix', 9, NULL, 8, 45, 100, 45, 45, 45, 10);

-- --------------------------------------------------------

--
-- Structure de la table `pokemon_attacks`
--

CREATE TABLE `pokemon_attacks` (
  `pokemon_id` int(11) NOT NULL,
  `attack_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `pokemon_attacks`
--

INSERT INTO `pokemon_attacks` (`pokemon_id`, `attack_id`) VALUES
(1, 1),
(3, 1),
(2, 2),
(3, 3),
(1, 4),
(4, 4),
(8, 4),
(6, 5),
(7, 6),
(5, 7),
(1, 8),
(3, 8),
(2, 9),
(4, 9),
(6, 9),
(8, 9),
(3, 10),
(4, 10),
(2, 11),
(8, 11),
(6, 12),
(4, 13),
(2, 14),
(6, 15),
(5, 16),
(7, 16),
(5, 17),
(7, 17),
(1, 18),
(5, 19),
(7, 19),
(8, 19);

-- --------------------------------------------------------

--
-- Structure de la table `type`
--

CREATE TABLE `type` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type`
--

INSERT INTO `type` (`id`, `name`) VALUES
(1, 'Normal'),
(2, 'Feu'),
(3, 'Eau'),
(4, 'Plante'),
(5, 'Electrik'),
(6, 'Glace'),
(7, 'Combat'),
(8, 'Poison'),
(9, 'Sol'),
(10, 'Vol'),
(11, 'Psy'),
(12, 'Insecte'),
(13, 'Roche'),
(14, 'Spectre'),
(15, 'Dragon'),
(16, 'Ténèbres'),
(17, 'Acier'),
(18, 'Fée');

-- --------------------------------------------------------

--
-- Structure de la table `type_interaction`
--

CREATE TABLE `type_interaction` (
  `attacker_type_id` int(11) NOT NULL,
  `defender_type_id` int(11) NOT NULL,
  `multiplier` float NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Déchargement des données de la table `type_interaction`
--

INSERT INTO `type_interaction` (`attacker_type_id`, `defender_type_id`, `multiplier`) VALUES
(1, 13, 0.5),
(1, 14, 0),
(1, 17, 0.5),
(2, 2, 0.5),
(2, 3, 0.5),
(2, 4, 2),
(2, 6, 2),
(2, 12, 2),
(2, 13, 0.5),
(2, 15, 0.5),
(2, 17, 2),
(3, 2, 2),
(3, 3, 0.5),
(3, 4, 0.5),
(3, 9, 2),
(3, 13, 2),
(3, 15, 0.5),
(4, 2, 0.5),
(4, 3, 2),
(4, 4, 0.5),
(4, 8, 0.5),
(4, 9, 2),
(4, 10, 0.5),
(4, 12, 0.5),
(4, 13, 2),
(4, 15, 0.5),
(4, 17, 0.5),
(5, 3, 2),
(5, 4, 0.5),
(5, 5, 0.5),
(5, 9, 0),
(5, 10, 2),
(5, 15, 0.5),
(6, 2, 0.5),
(6, 3, 0.5),
(6, 4, 2),
(6, 6, 0.5),
(6, 9, 2),
(6, 10, 2),
(6, 15, 2),
(6, 17, 0.5),
(7, 1, 2),
(7, 6, 2),
(7, 8, 0.5),
(7, 10, 0.5),
(7, 11, 0.5),
(7, 12, 0.5),
(7, 13, 2),
(7, 14, 0),
(7, 16, 2),
(7, 17, 2),
(7, 18, 0.5),
(8, 4, 2),
(8, 8, 0.5),
(8, 9, 0.5),
(8, 13, 0.5),
(8, 14, 0.5),
(8, 17, 0),
(8, 18, 2),
(9, 2, 2),
(9, 4, 0.5),
(9, 5, 2),
(9, 8, 2),
(9, 10, 0),
(9, 12, 0.5),
(9, 13, 2),
(9, 17, 2),
(10, 4, 2),
(10, 5, 0.5),
(10, 7, 2),
(10, 12, 2),
(10, 13, 0.5),
(10, 17, 0.5),
(11, 7, 2),
(11, 8, 2),
(11, 11, 0.5),
(11, 16, 0),
(11, 17, 0.5),
(12, 2, 0.5),
(12, 4, 2),
(12, 7, 0.5),
(12, 8, 0.5),
(12, 10, 0.5),
(12, 11, 2),
(12, 14, 0.5),
(12, 16, 2),
(12, 17, 0.5),
(12, 18, 0.5),
(13, 2, 2),
(13, 6, 2),
(13, 7, 0.5),
(13, 9, 0.5),
(13, 10, 2),
(13, 12, 2),
(13, 17, 0.5),
(14, 1, 0),
(14, 11, 2),
(14, 14, 2),
(14, 16, 0.5),
(15, 15, 2),
(15, 17, 0.5),
(15, 18, 0),
(16, 7, 0.5),
(16, 11, 2),
(16, 14, 2),
(16, 16, 0.5),
(16, 18, 0.5),
(17, 2, 0.5),
(17, 3, 0.5),
(17, 5, 0.5),
(17, 6, 2),
(17, 13, 2),
(17, 17, 0.5),
(17, 18, 2),
(18, 2, 0.5),
(18, 7, 2),
(18, 8, 0.5),
(18, 15, 2),
(18, 16, 2),
(18, 17, 0.5);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `ability`
--
ALTER TABLE `ability`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `attack`
--
ALTER TABLE `attack`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type_id` (`type_id`);

--
-- Index pour la table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `pokemon`
--
ALTER TABLE `pokemon`
  ADD PRIMARY KEY (`id`),
  ADD KEY `type1_id` (`type1_id`),
  ADD KEY `type2_id` (`type2_id`),
  ADD KEY `ability_id` (`ability_id`);

--
-- Index pour la table `pokemon_attacks`
--
ALTER TABLE `pokemon_attacks`
  ADD PRIMARY KEY (`pokemon_id`,`attack_id`),
  ADD KEY `attack_id` (`attack_id`);

--
-- Index pour la table `type`
--
ALTER TABLE `type`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_interaction`
--
ALTER TABLE `type_interaction`
  ADD KEY `attacker_type_id` (`attacker_type_id`),
  ADD KEY `defender_type_id` (`defender_type_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `ability`
--
ALTER TABLE `ability`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `attack`
--
ALTER TABLE `attack`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT pour la table `item`
--
ALTER TABLE `item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `pokemon`
--
ALTER TABLE `pokemon`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `type`
--
ALTER TABLE `type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `attack`
--
ALTER TABLE `attack`
  ADD CONSTRAINT `attack_ibfk_1` FOREIGN KEY (`type_id`) REFERENCES `type` (`id`);

--
-- Contraintes pour la table `pokemon`
--
ALTER TABLE `pokemon`
  ADD CONSTRAINT `pokemon_ibfk_1` FOREIGN KEY (`type1_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `pokemon_ibfk_2` FOREIGN KEY (`type2_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `pokemon_ibfk_3` FOREIGN KEY (`ability_id`) REFERENCES `ability` (`id`);

--
-- Contraintes pour la table `pokemon_attacks`
--
ALTER TABLE `pokemon_attacks`
  ADD CONSTRAINT `pokemon_attacks_ibfk_1` FOREIGN KEY (`pokemon_id`) REFERENCES `pokemon` (`id`),
  ADD CONSTRAINT `pokemon_attacks_ibfk_2` FOREIGN KEY (`attack_id`) REFERENCES `attack` (`id`);

--
-- Contraintes pour la table `type_interaction`
--
ALTER TABLE `type_interaction`
  ADD CONSTRAINT `type_interaction_ibfk_1` FOREIGN KEY (`attacker_type_id`) REFERENCES `type` (`id`),
  ADD CONSTRAINT `type_interaction_ibfk_2` FOREIGN KEY (`defender_type_id`) REFERENCES `type` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
