-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.5.47-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for springmvcmock
DROP DATABASE IF EXISTS `springmvcmock`;
CREATE DATABASE IF NOT EXISTS `springmvcmock` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `springmvcmock`;

-- Dumping structure for table springmvcmock.employee
DROP TABLE IF EXISTS `employee`;
CREATE TABLE IF NOT EXISTS `employee` (
  `empId` int(11) NOT NULL AUTO_INCREMENT,
  `candLevel` varchar(255) DEFAULT NULL,
  `candName` varchar(255) DEFAULT NULL,
  `candSkill` varchar(255) DEFAULT NULL,
  `empDor` datetime DEFAULT NULL,
  `empName` varchar(255) DEFAULT NULL,
  `referralBonus` double DEFAULT NULL,
  PRIMARY KEY (`empId`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- Dumping data for table springmvcmock.employee: ~2 rows (approximately)
DELETE FROM `employee`;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` (`empId`, `candLevel`, `candName`, `candSkill`, `empDor`, `empName`, `referralBonus`) VALUES
	(1, '10', 'Manoj', 'J2EE', '2020-04-25 00:00:00', 'Kumar', 10000),
	(2, '10', 'Vijayakumar', 'J2EE', '2020-04-12 00:00:00', 'Rajarathinam', 10000);
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;

-- Dumping structure for table springmvcmock.skillset
DROP TABLE IF EXISTS `skillset`;
CREATE TABLE IF NOT EXISTS `skillset` (
  `skillid` int(11) DEFAULT NULL,
  `skill` varchar(20) DEFAULT NULL,
  `level` varchar(20) DEFAULT NULL,
  `bonus` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Dumping data for table springmvcmock.skillset: ~6 rows (approximately)
DELETE FROM `skillset`;
/*!40000 ALTER TABLE `skillset` DISABLE KEYS */;
INSERT INTO `skillset` (`skillid`, `skill`, `level`, `bonus`) VALUES
	(11, 'J2EE', '10', 10000),
	(12, 'J2EE', '9', 15000),
	(13, 'SAP ABAP', '9', 16000),
	(14, 'SAP ABAP', '10', 12000),
	(15, 'Salesforce', '10', 11000),
	(16, 'Salesforce', '8', 20000);
/*!40000 ALTER TABLE `skillset` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
