-- phpMyAdmin SQL Dump
-- version 3.4.10.1deb1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le : Mer 11 Décembre 2013 à 22:42
-- Version du serveur: 5.5.32
-- Version de PHP: 5.3.10-1ubuntu3.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `android_absences`
--

-- --------------------------------------------------------

--
-- Structure de la table `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idEleve` int(11) NOT NULL,
  `idSeance` int(11) NOT NULL,
  `boolRetard` tinyint(1) NOT NULL,
  `remarque` text NOT NULL,
  `urlCertificat` varchar(500) NOT NULL,
  `boolAbsent` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=3 ;

--
-- Contenu de la table `data`
--

INSERT INTO `data` (`id`, `idEleve`, `idSeance`, `boolRetard`, `remarque`, `urlCertificat`, `boolAbsent`) VALUES
(1, 1, 1, 0, '', '', 1),
(2, 2, 1, 0, '', '', 1);

-- --------------------------------------------------------

--
-- Structure de la table `groupes`
--

CREATE TABLE IF NOT EXISTS `groupes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `groupes`
--

INSERT INTO `groupes` (`id`, `nom`) VALUES
(1, 'iser'),
(2, 'uod'),
(3, 'l5');

-- --------------------------------------------------------

--
-- Structure de la table `membres`
--

CREATE TABLE IF NOT EXISTS `membres` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `idGroupe` int(11) NOT NULL,
  `idEleve` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=5 ;

--
-- Contenu de la table `membres`
--

INSERT INTO `membres` (`id`, `idGroupe`, `idEleve`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 2, 1),
(4, 2, 3);

-- --------------------------------------------------------

--
-- Structure de la table `seances`
--

CREATE TABLE IF NOT EXISTS `seances` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dtDebut` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `duree` tinyint(4) NOT NULL DEFAULT '4',
  `nom` varchar(100) NOT NULL,
  `idEnseignant` int(11) NOT NULL,
  `idGroupe` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=7 ;

--
-- Contenu de la table `seances`
--

INSERT INTO `seances` (`id`, `dtDebut`, `duree`, `nom`, `idEnseignant`, `idGroupe`) VALUES
(1, '2012-09-17 19:42:45', 127, 'seance1_iser', 1, 1),
(2, '2012-09-17 19:43:09', 127, 'seance1_uod', 3, 2),
(3, '2012-09-17 19:50:06', 0, 'seance2_iser', 1, 1),
(4, '2012-09-17 19:50:35', 0, 'seance2_uod', 3, 2),
(5, '2013-12-12 07:00:00', 4, '', 0, 0),
(6, '2013-12-12 07:00:00', 4, 'IME5', 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `users`
--

CREATE TABLE IF NOT EXISTS `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(100) NOT NULL,
  `passe` varchar(100) NOT NULL,
  `nom` varchar(100) NOT NULL,
  `prenom` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

--
-- Contenu de la table `users`
--

INSERT INTO `users` (`id`, `login`, `passe`, `nom`, `prenom`) VALUES
(1, 'tom', 'toto', 'Bous', 'Thomas'),
(2, 'phil', 'phil', 'Kub', 'Philippe'),
(3, 'sam', 'sam', 'EK', 'Samir');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
