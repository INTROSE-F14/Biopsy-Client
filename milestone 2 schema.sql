CREATE DATABASE IF NOT EXISTS IntroseMp;
USE IntroseMp;
SET FOREIGN_KEY_CHECKS=0;
DROP TABLE IF EXISTS Patients;
CREATE TABLE Patients
(
	patientID INT UNSIGNED AUTO_INCREMENT NOT NULL,
	lastName VARCHAR(30) NOT NULL,
	firstName VARCHAR(30) NOT NULL,
	middleName VARCHAR(30) NOT NULL,
	birthday DATE,
	gender CHAR(1) NOT NULL,
	PRIMARY KEY(patientID)
)ENGINE = MyISAM;


DROP TABLE IF EXISTS Records;
CREATE TABLE Records
(
	recordYear TINYINT NOT NULL,
	recordNumber SMALLINT NOT NULL AUTO_INCREMENT,
	recordType ENUM('G','H','C') NOT NULL,
	patientID INT UNSIGNED NOT NULL,
	physician VARCHAR(100) NOT NULL,
	pathologist VARCHAR(100) NOT NULL,
	dateReceived DATETIME NOT NULL,
	dateCompleted DATETIME NOT NULL,
	specimen VARCHAR(100) NOT NULL,
	specimenType VARCHAR (30) NOT NULL,
	room VARCHAR(15),
	remarks VARCHAR(10000),
	grossDescription VARCHAR(10000),
	microscopicNotes VARCHAR(10000),
	PRIMARY KEY(recordType, recordYear, recordNumber),
	FOREIGN KEY(patientID) REFERENCES Patients(patientID)
) ENGINE = MyISAM;

DROP TABLE IF EXISTS Dictionary;
CREATE TABLE Dictionary
(
	word varchar(100) NOT NULL,
	wordType int(1) NOT NULL,
	PRIMARY KEY(word, wordtype)
)ENGINE = MyISAM;

DROP TABLE IF EXISTS Categories;
CREATE TABLE Categories
(
	category_id INT UNSIGNED AUTO_INCREMENT,
	category_name varchar(100) NOT NULL,
	parent_category INT DEFAULT NULL,
	PRIMARY KEY(category_id)
)ENGINE = MyISAM;

DROP TABLE IF EXISTS Diagnosis;
CREATE TABLE Diagnosis
(
	category_id INT UNSIGNED NOT NULL,
	recordYear TINYINT NOT NULL,
	recordNumber SMALLINT NOT NULL,
	recordType ENUM('G','H','C') NOT NULL,
	diagnosis_value VARCHAR(10000) NOT NULL,
	PRIMARY KEY(category_id, recordYear, recordNumber, recordType),
	FOREIGN KEY(category_id) REFERENCES Categories(category_id),
	FOREIGN KEY(recordType, recordYear, recordNumber) REFERENCES Records(recordType, recordYear, recordNumber)
) ENGINE = MyISAM;


insert into categories values
(1, 'Negative for Intraepithelial Lesion or Malignancy (NILM)', null),
(2, 'Epithelial Cell Abnormalities', null),
(3, 'Other Malignant Neoplasms', null),
(4, 'Others', null),
(5, 'Organisms', 1),
(6, 'Other non-neoplastic findings', 1),
(7, 'Other', 1),
(8, 'Squamous Cell', 2),
(9, 'Glandular Cell', 2), 
(10, 'Specimen Adequacy', null),
(11, 'Hormonal Evaluation', null),
(12, 'Superficials', 11),
(13, 'Intermediates', 11),
(14, 'Parabasals', 11);

