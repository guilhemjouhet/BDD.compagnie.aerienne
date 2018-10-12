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
  `Numero de Securite Sociale` INT NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  PRIMARY KEY (`Numero de Securite Sociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numero de Securite Sociale` ASC) VISIBLE)
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
  `ville depart` VARCHAR(45) NULL,
  `ville arrivee` VARCHAR(45) NULL,
  `numero` INT NOT NULL,
  PRIMARY KEY (`numero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Vol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Vol` (
  `numero de vol` INT NOT NULL,
  `periode debut` DATE NULL,
  `periode fin` DATE NULL,
  `horaire depart` DATETIME NULL,
  `horaire arrivee` DATETIME NULL,
  `numero liaison` INT NULL,
  `immatriculation` INT NULL,
  PRIMARY KEY (`numero de vol`),
  INDEX `id_liaison_idx` (`numero liaison` ASC) VISIBLE,
  INDEX `immatriculation_idx` (`immatriculation` ASC) VISIBLE,
  CONSTRAINT `id_liaison`
    FOREIGN KEY (`numero liaison`)
    REFERENCES `mydb`.`Liaison` (`numero`)
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
  `numero de vol` INT NOT NULL,
  `date de depart` DATE NOT NULL,
  `nombre de places libres` INT NULL,
  `nombre de places occupees` INT NULL,
  PRIMARY KEY (`numero de vol`, `date de depart`),
  CONSTRAINT `id_vol`
    FOREIGN KEY (`numero de vol`)
    REFERENCES `mydb`.`Vol` (`numero de vol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Equipage`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Equipage` (
  `numero de Securite Sociale` INT NOT NULL,
  `nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `fonction` VARCHAR(20) NOT NULL,
  `heures de vol` FLOAT NULL,
  `id_vol` INT NULL,
  `date depart` DATE NULL,
  PRIMARY KEY (`numero de Securite Sociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`numero de Securite Sociale` ASC) VISIBLE,
  INDEX `fk_Equipage_Depart1_idx` (`id_vol` ASC, `date depart` ASC) VISIBLE,
  CONSTRAINT `fk_Equipage_Depart1`
    FOREIGN KEY (`id_vol` , `date depart`)
    REFERENCES `mydb`.`Depart` (`numero de vol` , `date de depart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pilotes`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pilotes` (
  `Numero de Securite Sociale` INT NOT NULL,
  `Nom` VARCHAR(20) NOT NULL,
  `prenom` VARCHAR(20) NOT NULL,
  `adresse` VARCHAR(50) NOT NULL,
  `salaire` FLOAT NOT NULL,
  `licence` INT NOT NULL,
  `heures de vol` FLOAT NULL,
  `id_vol` INT NULL,
  `date depart` DATE NULL,
  PRIMARY KEY (`Numero de Securite Sociale`),
  UNIQUE INDEX `Numéro de Sécurité Sociale_UNIQUE` (`Numero de Securite Sociale` ASC) VISIBLE,
  INDEX `fk_Pilotes_Depart1_idx` (`id_vol` ASC, `date depart` ASC) VISIBLE,
  CONSTRAINT `fk_Pilotes_Depart1`
    FOREIGN KEY (`id_vol` , `date depart`)
    REFERENCES `mydb`.`Depart` (`numero de vol` , `date de depart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Billets`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Billets` (
  `numero` INT NOT NULL,
  `date emission` DATE NULL,
  `prix` INT NULL,
  PRIMARY KEY (`numero`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Reservation`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Reservation` (
  `numero passager` INT NOT NULL,
  `numero billet` INT NOT NULL,
  `id_vol` INT NULL,
  `date depart` DATE NULL,
  PRIMARY KEY (`numero passager`, `numero billet`),
  INDEX `fk_Reservation_Billets1_idx` (`numero billet` ASC) VISIBLE,
  INDEX `fk_Reservation_Depart1_idx` (`id_vol` ASC, `date depart` ASC) VISIBLE,
  CONSTRAINT `fk_Reservation_Billets1`
    FOREIGN KEY (`numero billet`)
    REFERENCES `mydb`.`Billets` (`numero`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Reservation_Depart1`
    FOREIGN KEY (`id_vol` , `date depart`)
    REFERENCES `mydb`.`Depart` (`numero de vol` , `date de depart`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Passagers`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Passagers` (
  `numero` INT NOT NULL,
  `nom` VARCHAR(45) NULL,
  `prenom` VARCHAR(45) NULL,
  `adresse` VARCHAR(45) NULL,
  PRIMARY KEY (`numero`),
  CONSTRAINT `fk_Passagers_Reservation1`
    FOREIGN KEY (`numero`)
    REFERENCES `mydb`.`Reservation` (`numero passager`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

#CREATE USER 'admin' IDENTIFIED BY 'admin';

GRANT ALL ON `mydb`.* TO 'admin';

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
