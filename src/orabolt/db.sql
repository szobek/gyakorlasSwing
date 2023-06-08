create database oraBolt;

use oraBolt;

create table orak (
ID int auto_increment unique ,
name varchar(50) not null,
tipus varchar(10) not null,
ar int,
vizallo BOOLEAN default false
);

INSERT INTO `orak` (`ID`, `name`, `tipus`, `ar`, `vizallo`) VALUES (NULL, 'Festina', 'karóra', '110000', '1');
INSERT INTO `orak` (`ID`, `name`, `tipus`, `ar`, `vizallo`) VALUES (NULL, 'Casio', 'karóra', '34000', '1');
INSERT INTO `orak` (`ID`, `name`, `tipus`, `ar`, `vizallo`) VALUES (NULL, 'Rolex', 'karóra', '1000000', '1');
INSERT INTO `orak` (`ID`, `name`, `tipus`, `ar`, `vizallo`) VALUES (NULL, 'Hama', 'ébresztõóra', '4400', '0');