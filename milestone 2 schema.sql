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

DROP TABLE IF EXISTS ResultCategories;
CREATE TABLE ResultCategories
(
	category_id INT UNSIGNED AUTO_INCREMENT,
	category_name varchar(100) NOT NULL,
	parent_category INT DEFAULT NULL,
	PRIMARY KEY(category_id)
)ENGINE = MyISAM;

DROP TABLE IF EXISTS Results;
CREATE TABLE Results
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


insert into resultcategories values
(1, 'Diagnosis', null),
(2, 'Comment', null),
(3, 'Negative for Intraepithelial Lesion or Malignancy (NILM)', 1),
(4, 'Epithelial Cell Abnormalities', 1),
(5, 'Other Malignant Neoplasms', 1),
(6, 'Others', 1),
(7, 'Organisms', 3),
(8, 'Other non-neoplastic findings', 3),
(9, 'Other NILM', 3),
(10, 'Squamous Cell', 4),
(11, 'Glandular Cell', 4), 
(12, 'Specimen Adequacy', 6),
(13, 'Hormonal Evaluation', 6),
(14, 'Superficials', 13),
(15, 'Intermediates', 13),
(16, 'Parabasals', 13),
(17, 'Remarks', 2),
(18, 'Gross Description', 2),
(19, 'Microscopic Notes', 2);

