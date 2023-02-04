-- Insert a Admin in the Database
INSERT INTO T_company (ID, COMPANY_NAME, DEPARTMENT)
       VALUES ("1", "Hochschule Esslingen", "Fakultaet-IT");

INSERT INTO T_address (ID, AREA, CITY, COUNTRY, HOUSE_NUMBER, STREET_NAME, ZIP_CODE, COMPANY_ID)
       VALUES ("1", "Baden-Wuettemberg", "Esslingen", "Germany", "101", "Flandernstrasse", "73732", "1");

INSERT INTO T_user (ID, EMAIL, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, COMPANY_ID) 
       VALUES ("1","joergfriedrich@hs-esslingen.de", "jofrit00", "password", "Joerg", "Friedrich", TRUE, "1");

INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("1", "071139749", "1");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("2", "071112345", "1");

INSERT INTO T_contract (ID, END_DATE, LICENSE_KEY, START_DATE, VERSION, COMPANY_ID)
       VALUES ("1", "2023-01-30", "ThisIsASecureLicenceKey", "2022-10-01", "v1.0", "1");

INSERT INTO contract_user (CONTRACT_ID, USER_ID)
       VALUES ("1", "1");

INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("1", "243.122.178.25", "1");
INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("2", "201.102.108.205", "1");

INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("1", "10", "1");
INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("2", "20", "1");

-- Insert Additional Data
INSERT INTO T_company (ID, COMPANY_NAME, DEPARTMENT)
       VALUES ("2", "Hochschule Esslingen - SWB", "Fakultaet-IT");

INSERT INTO T_address (ID, AREA, CITY, COUNTRY, HOUSE_NUMBER, STREET_NAME, ZIP_CODE, COMPANY_ID)
       VALUES ("2", "Baden-Wuettemberg", "Esslingen", "Germany", "101", "Flandernstrasse", "73732", "2");

INSERT INTO T_user (ID, EMAIL, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, COMPANY_ID) 
       VALUES ("2","chmeit02@hs-esslingen.de", "chmeit02", "password", "Christoph", "Merck", FALSE, "2");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("3", "015207417058", "2");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("4", "0715633841", "2");

INSERT INTO T_user (ID, EMAIL, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, COMPANY_ID) 
       VALUES ("3","nimeit02@hs-esslingen.de", "nimeit02", "password", "Niklas", "Meier", FALSE, "2");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("5", "0123456789", "3");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("6", "01234567890", "3");

INSERT INTO T_user (ID, EMAIL, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, IS_ADMIN, COMPANY_ID) 
       VALUES ("4","glkoit00@hs-esslingen.de", "glkoit00", "password", "Glykeria", "Koutsianou", FALSE, "2");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("7", "012345678901", "4");
INSERT INTO T_phoneNumber (ID, NUMBER, USER_ID)
       VALUES ("8", "0123456789012", "4");


INSERT INTO T_contract (ID, END_DATE, LICENSE_KEY, START_DATE, VERSION, COMPANY_ID)
       VALUES ("2", "2023-03-30", "ThisIsASecureLicenceKeyFromCompanySWB", "2021-01-01", "v1.3", "2");

INSERT INTO contract_user (CONTRACT_ID, USER_ID)
       VALUES ("2", "2");
INSERT INTO contract_user (CONTRACT_ID, USER_ID)
       VALUES ("2", "3");

INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("3", "213.112.118.215", "2");
INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("4", "211.112.118.215", "2");

INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("3", "12", "2");
INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("4", "22", "2");

INSERT INTO T_contract (ID, END_DATE, LICENSE_KEY, START_DATE, VERSION, COMPANY_ID)
       VALUES ("3", "2023-03-30", "ThisIsASecureLicenceKeyFromCompanySWB-2", "2021-01-01", "v1.9", "2");

INSERT INTO contract_user (CONTRACT_ID, USER_ID)
       VALUES ("3", "3");
INSERT INTO contract_user (CONTRACT_ID, USER_ID)
       VALUES ("3", "4");

INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("5", "233.132.138.235", "3");
INSERT INTO T_ips (ID, ADDRESS, CONTRACT_ID)
       VALUES ("6", "231.132.138.235", "3");

INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("5", "19", "3");
INSERT INTO T_feature (ID, NUMBER, CONTRACT_ID)
       VALUES ("6", "27", "3");