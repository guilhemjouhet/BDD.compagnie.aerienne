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
-- Table `mydb`.`Personnel Sol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Personnel Sol` (
  `Numerosecuritesociale` INT NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  PRIMARY KEY (`Numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numerosecuritesociale` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Appareils`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Appareils` (
  `immatriculation` INT NOT NULL,
  `type` VARCHAR(12) NOT NULL,
  PRIMARY KEY (`immatriculation`),
  UNIQUE INDEX `Immatriculation_UNIQUE` (`immatriculation` ASC) VISIBLE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Liaison`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Liaison` (
  `depart` VARCHAR(3) NOT NULL,
  `arrivee` VARCHAR(3) NOT NULL,
  `villedepart` VARCHAR(45) NULL,
  `villearrivee` VARCHAR(45) NULL,
  `idliaison` INT NOT NULL,
  PRIMARY KEY (`idliaison`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Vol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Vol` (
  `idvol` INT NOT NULL,
  `periodedebut` DATE NULL,
  `periodefin` DATE NULL,
  `horairedepart` DATETIME NULL,
  `horairearrivee` DATETIME NULL,
  `idliaison` INT NULL,
  `immatriculation` INT NULL,
  PRIMARY KEY (`idvol`),
  INDEX `id_liaison_idx` (`idliaison` ASC) VISIBLE,
  INDEX `immatriculation_idx` (`immatriculation` ASC) VISIBLE,
  CONSTRAINT `id_liaison`
    FOREIGN KEY (`idliaison`)
    REFERENCES `mydb`.`Liaison` (`idliaison`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `immatriculation`
    FOREIGN KEY (`immatriculation`)
    REFERENCES `mydb`.`Appareils` (`immatriculation`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Depart`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Depart` (
  `idvol` INT NOT NULL,
  `datedepart` DATE NOT NULL,
  `nbplaceslibres` INT NULL,
  `nbplacesoccupees` INT NULL,
  PRIMARY KEY (`idvol`, `datedepart`),
  CONSTRAINT `id_vol`
    FOREIGN KEY (`idvol`)
    REFERENCES `mydb`.`Vol` (`idvol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Equipage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Equipage` (
  `numerosecuritesociale` INT NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `fonction` VARCHAR(20) NOT NULL,
  `heuresvol` FLOAT NULL,
  `idvol` INT NULL,
  `datedepart` DATE NULL,
  PRIMARY KEY (`numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`numerosecuritesociale` ASC) VISIBLE,
  INDEX `fk_Equipage_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Equipage_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`Depart` (`idvol` , `datedepart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pilotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pilotes` (
  `Numerosecuritesociale` INT NOT NULL,
  `Nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `licence` INT NOT NULL,
  `heuresvol` FLOAT NULL,
  `idvol` INT NULL,
  `datedepart` DATE NULL,
  PRIMARY KEY (`Numerosecuritesociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numerosecuritesociale` ASC) VISIBLE,
  INDEX `fk_Pilotes_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Pilotes_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`Depart` (`idvol` , `datedepart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Billets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Billets` (
  `numerobillet` INT NOT NULL,
  `dateemission` DATE NULL,
  `prix` INT NULL,
  PRIMARY KEY (`numerobillet`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Reservation` (
  `idpassager` INT NOT NULL,
  `numerobillet` INT NOT NULL,
  `idvol` INT NULL,
  `datedepart` DATE NULL,
  PRIMARY KEY (`idpassager`, `numerobillet`),
  INDEX `fk_Reservation_Billets1_idx` (`numerobillet` ASC) VISIBLE,
  INDEX `fk_Reservation_Depart1_idx` (`idvol` ASC, `datedepart` ASC) VISIBLE,
  CONSTRAINT `fk_Reservation_Billets1`
    FOREIGN KEY (`numerobillet`)
    REFERENCES `mydb`.`Billets` (`numerobillet`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservation_Depart1`
    FOREIGN KEY (`idvol` , `datedepart`)
    REFERENCES `mydb`.`Depart` (`idvol` , `datedepart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Passagers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Passagers` (
  `idpassager` INT NOT NULL,
  `nom` VARCHAR(45) NULL,
  `prenom` VARCHAR(45) NULL,
  `adresse` VARCHAR(45) NULL,
  PRIMARY KEY (`idpassager`),
  CONSTRAINT `fk_Passagers_Reservation1`
    FOREIGN KEY (`idpassager`)
    REFERENCES `mydb`.`Reservation` (`idpassager`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
