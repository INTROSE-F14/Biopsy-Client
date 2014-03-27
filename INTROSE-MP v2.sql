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
	birthday DATE NOT NULL,
	gender CHAR(1) NOT NULL,
	PRIMARY KEY(patientID)
);

DROP TABLE IF EXISTS Changelog;
CREATE TABLE Changelog
(
	patientID INT UNSIGNED AUTO_INCREMENT NOT NULL,
	dateEdited TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
	changesMade VARCHAR(100),
	PRIMARY KEY(dateEdited, patientID),
	FOREIGN KEY(patientID) REFERENCES Patients(patientID)
);


DROP TABLE IF EXISTS Records;
CREATE TABLE Records
(
	internalReferenceNumber VARCHAR(20) NOT NULL,
	recordType INT(1) UNSIGNED NOT NULL,
	specimenType VARCHAR(30) NOT NULL,
	patientID INT UNSIGNED AUTO_INCREMENT NOT NULL,
	physician VARCHAR(100) NOT NULL,
	pathologist VARCHAR(100) NOT NULL,
	dateReceived DATETIME NOT NULL,
	dateCompleted DATETIME NOT NULL,
	specimen VARCHAR(50) NOT NULL,
	remarks VARCHAR(200) NOT NULL,
	room VARCHAR(15),
	grossDescription VARCHAR(200) NOT NULL,
	microscopicNotes VARCHAR(200) NOT NULL,
	PRIMARY KEY(internalReferenceNumber),
	FOREIGN KEY(patientID) REFERENCES Patients(patientID)
);

DROP TABLE IF EXISTS Dictionary;
CREATE TABLE Dictionary
(
	word varchar(100) NOT NULL,
	wordType int(1) NOT NULL,
	PRIMARY KEY(word)
);

DROP TABLE IF EXISTS Categories;
CREATE TABLE Categories
(
	category_id INT UNSIGNED AUTO_INCREMENT,
	category_name varchar(100) NOT NULL,
	parent_category INT DEFAULT NULL,
	PRIMARY KEY(category_id)
);

DROP TABLE IF EXISTS Diagnosis;
CREATE TABLE Diagnosis
(
	category_id INT UNSIGNED NOT NULL,
	internalreferencenumber VARCHAR(20) NOT NULL,
	diagnosis_value VARCHAR(200) NOT NULL,
	PRIMARY KEY(category_id, internalReferenceNumber),
	FOREIGN KEY(category_id) REFERENCES Categories(category_id),
	FOREIGN KEY(internalreferencenumber) REFERENCES Records(internalreferencenumber)
);


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
(10, 'Specimen Adequacy', null);