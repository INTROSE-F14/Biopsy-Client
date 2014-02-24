CREATE DATABASE IF NOT EXISTS IntroseMp;
USE IntroseMp;

DROP TABLE IF EXISTS Patients;
CREATE TABLE Patients(
patientID VARCHAR(20) NOT NULL,
lastName VARCHAR(30) NOT NULL,
firstName VARCHAR(30) NOT NULL,
middleName VARCHAR(30) NOT NULL,
birthday DATE NOT NULL,
gender CHAR(1) NOT NULL,
room VARCHAR(15),
PRIMARY KEY(patientID)
);

DROP TABLE IF EXISTS Changelog;
CREATE TABLE Changelog(
patientID VARCHAR(20),
dateEdited TIMESTAMP DEFAULT CURRENT_TIMESTAMP, 
changesMade VARCHAR(100),
PRIMARY KEY(dateEdited, patientID),
FOREIGN KEY(patientID) REFERENCES Patients(patientID)
);


DROP TABLE IF EXISTS Records;
CREATE TABLE Records(
internalReferenceNumber VARCHAR(20) NOT NULL,
recordType INT(1) UNSIGNED NOT NULL,
patientID VARCHAR(20) NOT NULL,
physician VARCHAR(100) NOT NULL,
pathologist VARCHAR(100) NOT NULL,
dateReceived DATETIME NOT NULL,
dateCompleted DATETIME NOT NULL,
specimen VARCHAR(50) NOT NULL,
diagnosis VARCHAR(200) NOT NULL,
remarks VARCHAR(200) NOT NULL,
PRIMARY KEY(internalReferenceNumber, patientID),
FOREIGN KEY(patientID) REFERENCES Patients(patientID)
);

DROP TABLE IF EXISTS Dictionary;
CREATE TABLE Dictionary(
word varchar(100) NOT NULL,
wordType int(1) NOT NULL,
PRIMARY KEY(word)
);