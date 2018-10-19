-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`appareils`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`appareils` (
  `immatriculation` INT(11) NOT NULL,
  `type` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`immatriculation`),
  UNIQUE INDEX `Immatriculation_UNIQUE` (`immatriculation` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`billets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`billets` (
  `numerobillet` INT(11) NOT NULL,
  `dateemission` DATE NULL DEFAULT NULL,
  `prix` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`numerobillet`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`liaison`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`liaison` (
  `depart` VARCHAR(3) NOT NULL,
  `arrivee` VARCHAR(3) NOT NULL,
  `villedepart` VARCHAR(45) NULL DEFAULT NULL,
  `villearrivee` VARCHAR(45) NULL DEFAULT NULL,
  `idliaison` INT(11) NOT NULL,
  PRIMARY KEY (`idliaison`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`vol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`vol` (
  `idvol` INT(11) NOT NULL,
  `periodedebut` DATE NULL DEFAULT NULL,
  `periodefin` DATE NULL DEFAULT NULL,
  `horairedepart` DATETIME NULL DEFAULT NULL,
  `horairearrivee` DATETIME NULL DEFAULT NULL,
  `idliaison` INT(11) NULL DEFAULT NULL,
  `immatriculation` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idvol`),
  INDEX `id_liaison_idx` (`idliaison` ASC) VISIBLE,
  INDEX `immatriculation_idx` (`immatriculation` ASC) VISIBLE,
  CONSTRAINT `id_liaison`
    FOREIGN KEY (`idliaison`)
    REFERENCES `mydb`.`liaison` (`idliaison`),
  CONSTRAINT `immatriculation`
    FOREIGN KEY (`immatriculation`)
    REFERENCES `mydb`.`appareils` (`immatriculation`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`depart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`depart` (
  `idvol` INT(11) NOT NULL,
  `datedepart` DATE NOT NULL,
  `nbplaceslibres` INT(11) NULL DEFAULT NULL,
  `nbplacesoccupees` INT(11) NULL DEFAULT NULL,
  PRIMARY KEY (`idvol`, `datedepart`),
  CONSTRAINT `id_vol`
    FOREIGN KEY (`idvol`)
    REFERENCES `mydb`.`vol` (`idvol`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`equipage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`equipage` (
  `numerosecuritesociale` INT(11) NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `fonction` VARCHAR(20) NOT NULL,
  `heuresvol` FLOAT NULL DEFAULT NULL,
  `idvol` INT(11) NULL DEFAULT NULL,
  `datedepart` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`numerosecuritesociale` ASC) VISIBLE,
  INDEX `fk_Equipage_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Equipage_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`depart` (`idvol` , `datedepart`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`reservation` (
  `idpassager` INT(11) NOT NULL,
  `numerobillet` INT(11) NOT NULL,
  `idvol` INT(11) NULL DEFAULT NULL,
  `datedepart` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`idpassager`, `numerobillet`),
  INDEX `fk_Reservation_Billets1_idx` (`numerobillet` ASC) VISIBLE,
  INDEX `fk_Reservation_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Reservation_Billets1`
    FOREIGN KEY (`numerobillet`)
    REFERENCES `mydb`.`billets` (`numerobillet`),
  CONSTRAINT `fk_Reservation_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`depart` (`idvol` , `datedepart`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`passagers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`passagers` (
  `idpassager` INT(11) NOT NULL,
  `nom` VARCHAR(45) NULL DEFAULT NULL,
  `prenom` VARCHAR(45) NULL DEFAULT NULL,
  `adresse` VARCHAR(45) NULL DEFAULT NULL,
  PRIMARY KEY (`idpassager`),
  CONSTRAINT `fk_Passagers_Reservation1`
    FOREIGN KEY (`idpassager`)
    REFERENCES `mydb`.`reservation` (`idpassager`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`personnel sol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`personnel sol` (
  `Numerosecuritesociale` INT(11) NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  PRIMARY KEY (`Numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numerosecuritesociale` ASC) VISIBLE)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `mydb`.`pilotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`pilotes` (
  `Numerosecuritesociale` INT(11) NOT NULL,
  `Nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `licence` INT(11) NOT NULL,
  `heuresvol` FLOAT NULL DEFAULT NULL,
  `idvol` INT(11) NULL DEFAULT NULL,
  `datedepart` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`Numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numerosecuritesociale` ASC) VISIBLE,
  INDEX `fk_Pilotes_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Pilotes_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`depart` (`idvol` , `datedepart`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
