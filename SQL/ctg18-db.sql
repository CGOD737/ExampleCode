--HW7 Christopher Godfrey ctg18

--SCHEMA FROM HOMEWORK 2--

---DROP ALL TABLES TO MAKE SURE THE SCHEMA IS CLEAR
DROP TABLE RECORDS CASCADE CONSTRAINTS;
DROP TABLE STATEMENTS CASCADE CONSTRAINTS;
DROP TABLE PAYMENTS CASCADE CONSTRAINTS;
DROP TABLE CUSTOMERS CASCADE CONSTRAINTS;
DROP TABLE DIRECTORY CASCADE CONSTRAINTS;
DROP TABLE COMPANY CASCADE CONSTRAINTS;
DROP TABLE COMP_BILL CASCADE CONSTRAINTS;

---CREATING DIRECTORY TABLE FIRST, SINCE MANY TABLES REFERENCES IT
CREATE TABLE DIRECTORY(
pn      number(10)       not null,
fname   varchar2(20)    not null,
lname   varchar2(20 )    not null,
street          varchar2(40),
city            varchar2(20),
zip             number(5),
state           varchar2(20),
CONSTRAINT DIRECTORY_PK PRIMARY KEY (pn) INITIALLY IMMEDIATE DEFERRABLE
);

---CREATING CUSTOMERS TABLE
CREATE TABLE CUSTOMERS(
SSN         number(9) 	    not null,
fname       varchar2(20)    not null,
lname       varchar2(20)    not null,
cell_pn     number(10)      not null,
home_pn     number(10),
street      varchar2(40),
city        varchar2(20),
zip         number(5),
state       varchar2(20),
free_min    number default 0,
dob       date,
free_SMS number default 0,
CONSTRAINT CUSTOMERS_PK PRIMARY KEY (cell_pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT CUSTOMERS_FK1 FOREIGN KEY (cell_pn) REFERENCES DIRECTORY(pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT CUSTOMERS_FK2 FOREIGN KEY (home_pn) REFERENCES DIRECTORY(pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT CUSTOMERS_UN UNIQUE (SSN) INITIALLY IMMEDIATE DEFERRABLE
);


---CREATING RECORDS TABLE
CREATE TABLE RECORDS(
from_pn         number(10)      not null,
to_pn           number(10)      not null,
start_timestamp  TIMESTAMP       not null,
duration number(4),
type       varchar2(20)     not null,
CONSTRAINT RECORDS_PK PRIMARY KEY(from_pn, start_timestamp) INITIALLY IMMEDIATE DEFERRABLE,
CONSTRAINT RECORDS_FK1 FOREIGN KEY(from_pn) REFERENCES DIRECTORY(pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT RECORDS_FK2 FOREIGN KEY(to_pn) REFERENCES DIRECTORY(pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT records_UQ_toPn UNIQUE(to_pn, start_timestamp) INITIALLY IMMEDIATE DEFERRABLE
);

---CREATING STATEMENTS TABLE
CREATE TABLE STATEMENTS(
cell_pn         number(10)      not null,
start_date      DATE    not null,
end_date        DATE    not null,
total_minutes   number,
total_SMS       number,
amount_due      number(6,2),
previous_balance number(6,2),
CONSTRAINT STATEMENTS_PK PRIMARY KEY (cell_pn, start_date) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT STATEMENTS_FK FOREIGN KEY (cell_pn) REFERENCES CUSTOMERS(cell_pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT STATEMENTS_UN UNIQUE(cell_pn, end_date) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT statements_UQ_endDate UNIQUE(end_date, cell_pn) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT START_END_STATEMENTS
    CHECK (end_date > start_date) INITIALLY IMMEDIATE DEFERRABLE
);

---CREATING PAYMENTS TABLE
CREATE TABLE PAYMENTS(
cell_pn	number(10)	not null,
paid_on	TIMESTAMP,
amount_paid	number(6,2),
CONSTRAINT PAYMENTS_PK PRIMARY KEY(cell_pn, paid_on) INITIALLY IMMEDIATE DEFERRABLE ,
CONSTRAINT PAYMENTS_FK FOREIGN KEY(cell_pn) REFERENCES CUSTOMERS(cell_pn) INITIALLY IMMEDIATE DEFERRABLE
);

Commit;
Purge recyclebin;

------------------------------------------------------------------------------------------------------------------------
--END OF HW2 SCHEME-- ADJUSTED for Q1 on HW7 WHERE ALL constraints are INTIALLY IMMEDIATE DERERRABLE and attributes declared within table
------------------------------------------------------------------------------------------------------------------------

--Q2--
CREATE TABLE COMPANY(
comp_ID number ,
comp_name varchar2(20) ,
street varchar2(40) ,
city varchar2(20) ,
zip number(5) ,
state varchar2(2) ,
CONSTRAINT COMPANY_PK PRIMARY KEY(comp_ID) INITIALLY DEFERRED DEFERRABLE,
CONSTRAINT COMPANY_UN UNIQUE(comp_name) INITIALLY DEFERRED DEFERRABLE
);
--Assumption was made that due to copyright and all that, no two cell phone companies will have the same name
--Q3--
CREATE TABLE COMP_BILL(
comp_ID number ,
start_date date ,
end_date date ,
total_minutes number(10,2) ,
amount_due number(10,2) ,
CONSTRAINT COMP_BILL_PK PRIMARY KEY(comp_ID, start_date) INITIALLY DEFERRED DEFERRABLE ,
CONSTRAINT COMP_BILL_FK FOREIGN KEY(comp_ID) REFERENCES COMPANY(comp_ID) INITIALLY DEFERRED DEFERRABLE ,
CONSTRAINT START_END_COMP_BILL
    CHECK (end_date > start_date) INITIALLY IMMEDIATE DEFERRABLE
);

Commit;
Purge recyclebin;

--Q4--
ALTER TABLE DIRECTORY add comp_ID number not null REFERENCES COMPANY(comp_ID);

--Q5--
ALTER TABLE COMPANY add charge_rate number(6,2) default 0.2;

--Q6-- Added into Statements and CompBill tables

Commit;
Purge recyclebin;

--=======================================================
-- INSERT THE DATA AFTER Q#6 AND BEFORE Q#7
--=======================================================

INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(1,'P_Mobile','210 Sennott Square','Pittsburgh',15260,'PA',0);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(2,'K_tele','22 2nd Ave','Philadelphia',22222,'PA',0.15);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(3,'L_tele','33 3rd Ave','Tridelphia',16161,'WV',0.20);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(4,'M_tele','44 4th Ave','Butler',13421,'PA',0.15);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(5,'D_tele','55 5th Ave','Harrisburgh',33333,'PA',0.10);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(6,'D_Mobile','66 6th Ave','Harrisburgh',33333,'PA',0.10);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(7,'H_tele','67 Center Ave','Harrisburgh',33333,'PA',0.10);
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(8,'M_Mobile','46 6th Ave','Butler',13421,'PA',0.15);

INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(2,'01-APR-19','31-MAY-19',16500,002475.00);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(3,'01-APR-19','31-MAY-19',26771,005354.20);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(4,'01-APR-19','31-MAY-19',16500,002475.00);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(5,'01-APR-19','31-MAY-19',26771,002677.10);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(6,'01-APR-19','31-MAY-19',26771,002677.10);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(7,'01-APR-19','31-MAY-19',26771,002677.10);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(8,'01-APR-19','31-MAY-19',16500,002475.00);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(2,'01-JUN-19','31-JUL-19',17394,002609.10);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(3,'01-JUN-19','31-JUL-19',28456,005691.20);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(4,'01-JUN-19','31-JUL-19',17394,002609.10);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(5,'01-JUN-19','31-JUL-19',28456,002845.60);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(6,'01-JUN-19','31-JUL-19',28456,002845.60);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(7,'01-JUN-19','31-JUL-19',28456,002845.60);
INSERT INTO COMP_BILL(comp_ID,start_date,end_date,total_minutes,amount_due) values(8,'01-JUN-19','31-JUL-19',17394,002609.10);

COMMIT;

--Q7--

BEGIN
INSERT INTO COMPANY(comp_ID,comp_name,street,city,zip,state,charge_rate) values(9, 'Q_Mobile', '1111 Fifth Ave', 'Pittsburgh', 15213, 'PA', 0.10);
INSERT INTO DIRECTORY values(7248881212, 'James', 'Kennedy'  , '5550 Fifth Avenue'  , 'Pittsburgh'   ,15216, 'PA',9);
INSERT INTO DIRECTORY values(7248884545, 'Robin', 'Gates'  , '6660 Sixth St'  , 'Pittsburgh'   ,15666, 'PA',9);
END;

COMMIT;

--=======================================================
-- INSERT THE DATA AFTER Q#7
--=======================================================


INSERT INTO DIRECTORY values(4121231231, 'Michael', 'Johnson'  , '320 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4121232131, 'Michael', 'Johnson'  , '320 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4124564564, 'Kate'   , 'Stevenson', '310 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4127897897, 'Bill'   , 'Stevenson', '310 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4123121231, 'Bill'   , 'Stevenson', '310 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4127417417, 'Richard', 'Hurson'   , '340 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4127417612, 'Richard', 'Hurson'   , '340 Fifth Avenue'  , 'Pittsburgh'   ,15213, 'PA',1);
INSERT INTO DIRECTORY values(4122582582, 'Mary'   , 'Davis'    , '350 Fifth Avenue'  , 'Pittsburgh'   ,15217, 'PA',1);
INSERT INTO DIRECTORY values(4122581324, 'Mary'   , 'Davis'    , '350 Fifth Avenue'  , 'Pittsburgh'   ,15217, 'PA',1);
INSERT INTO DIRECTORY values(7247779797, 'Julia'  , 'Hurson'   , '3350 Fifth Avenue' , 'Philadelphia' ,22221, 'PA',2);
INSERT INTO DIRECTORY values(7247778787, 'Chris'  , 'Lyn'      , '62 Sixth St'       , 'Philadelphia' ,22222, 'PA',2);
INSERT INTO DIRECTORY values(7243413412, 'Chris'  , 'Lyn'      , '62 Sixth St'       , 'Philadelphia' ,22222, 'PA',2);
INSERT INTO DIRECTORY values(7248889898, 'Jones'  , 'Steward'  , '350 Fifth Avenue'  , 'Philadelphia' ,22222, 'PA',1);
INSERT INTO DIRECTORY values(7249879879, 'James'  , 'Sam'      , '1210 Forbes Avenue', 'Philadelphia' ,22132, 'PA',1);
INSERT INTO DIRECTORY values(7249871253, 'James'  , 'Sam'      , '1210 Forbes Avenue', 'Philadelphia' ,22132, 'PA',1);
INSERT INTO DIRECTORY values(6243780132, 'Harry'  , 'Lee'      , '3721 Craig Street' , 'Tridelphia'   ,16161, 'WV',3);
INSERT INTO DIRECTORY values(6241121342, 'Kate'   , 'Lee'      , '3721 Craig Street' , 'Tridelphia'   ,16161, 'WV',3);
INSERT INTO DIRECTORY values(6242311322, 'Jack'   , 'Barry'    , '3521 Craig Street' , 'Tridelphia'   ,16161, 'WV',3);
INSERT INTO DIRECTORY values(6241456123, 'Neil'   , 'Jackson'  , '2134 Seventh St'   , 'Tridelphia'   ,16161, 'WV',3);
INSERT INTO DIRECTORY values(4843504021, 'Frank'  , 'Shaw'     , '23 Fifth Avenue'   , 'Allentown'    ,14213, 'PA',1);
INSERT INTO DIRECTORY values(4843504245, 'Frank'  , 'Shaw'     , '23 Fifth Avenue'   , 'Allentown'    ,14213, 'PA',1);
INSERT INTO DIRECTORY values(4846235161, 'Liam'   , 'Allen'    , '345 Craig Street'  , 'Allentown'    ,14213, 'PA',1);
INSERT INTO DIRECTORY values(4846452231, 'Justin' , 'Blosser'  , '231 Tenth Street'  , 'Allentown'    ,14213, 'PA',1);
INSERT INTO DIRECTORY values(4846452124, 'Justin' , 'Blosser'  , '231 Tenth Street'  , 'Allentown'    ,14213, 'PA',1);
INSERT INTO DIRECTORY values(2133504021, 'Patrick', 'Araon'    , '44 4th Avenue'     , 'Butler'    ,13421, 'PA',4);
INSERT INTO DIRECTORY values(2133504245, 'Abby'   , 'Red'      , '444 Fifth Avenue'  , 'Butler'    ,13421, 'PA',4);
INSERT INTO DIRECTORY values(2136235161, 'Reed'   , 'Michaels' , '345 Liberty Street', 'Butler'    ,13421, 'PA',4);
INSERT INTO DIRECTORY values(2136452231, 'Kristy' , 'Ray'      , '231 Liberty Street', 'Butler'    ,13421, 'PA',4);
INSERT INTO DIRECTORY values(2136452124, 'Kristy' , 'Ray'      , '231 Liberty Street', 'Butler'    ,13421, 'PA',4);
INSERT INTO DIRECTORY values(7773504021, 'Reese'  , 'Carter'   , '55 Fifth Avenue'   , 'Harrisburgh',33333, 'PA',5);
INSERT INTO DIRECTORY values(7773504245, 'Rubben' , 'Carter'   , '55 Fifth Avenue'   , 'Harrisburgh',33333, 'PA',5);
INSERT INTO DIRECTORY values(7776235161, 'Chris'  , 'Wynn'     , '125 Liberty Street', 'Harrisburgh',33333, 'PA',5);
INSERT INTO DIRECTORY values(7776452231, 'Zack'   , 'Wilson'   , '231 May Street'    , 'Harrisburgh',33333, 'PA',5);
INSERT INTO DIRECTORY values(7776452124, 'Alex'   , 'Casper'   , '111 May Street'    , 'Harrisburgh',33333, 'PA',5);
INSERT INTO DIRECTORY values(7783504021, 'Sophie' , 'Harper'   , '556 Fifth Avenue'  , 'Harrisburgh',33333, 'PA',6);
INSERT INTO DIRECTORY values(7783504245, 'Rachel' , 'Hanna'    , '557 Fifth Avenue'  , 'Harrisburgh',33333, 'PA',6);
INSERT INTO DIRECTORY values(7786235161, 'Jack'   , 'Steven'   , '12 Liberty Street' , 'Harrisburgh',33333, 'PA',6);
INSERT INTO DIRECTORY values(7786452231, 'Britney', 'Cook'     , '231 June Street'   , 'Harrisburgh',33333, 'PA',6);
INSERT INTO DIRECTORY values(7686452124, 'Anna'   , 'Nilson'   , '111 June Street'   , 'Harrisburgh',33333, 'PA',7);
INSERT INTO DIRECTORY values(7686452231, 'Katie'  , 'Jackson'  , '89  June Street'   , 'Harrisburgh',33333, 'PA',7);
INSERT INTO DIRECTORY values(7686452345, 'Clare'  , 'Green'    , '24  June Street'   , 'Harrisburgh',33333, 'PA',7);
INSERT INTO DIRECTORY values(2146452231, 'Bree'   , 'Julia'    , '11  Center Ave'   , 'Butler',13421, 'PA',8);
INSERT INTO DIRECTORY values(2146452345, 'Ben'    , 'Cooper'   , '14  Center Ave'   , 'Butler',13421, 'PA',8);

INSERT INTO CUSTOMERS values(123456789, 'Michael', 'Johnson',  4121231231, 4121232131,'320 Fifth Avenue', 'Pittsburgh', 15213,'PA',300,'01-JAN-91',100);
INSERT INTO CUSTOMERS values(123232343, 'Kate'   , 'Stevenson',4124564564, 4123121231,'310 Fifth Avenue', 'Pittsburgh', 15213,'PA',300,'05-FEB-90',100);
INSERT INTO CUSTOMERS values(445526754, 'Bill'   , 'Stevenson',4127897897, 4123121231,'310 Fifth Avenue', 'Pittsburgh', 15213,'PA',600,'11-DEC-92',50);
INSERT INTO CUSTOMERS values(254678898, 'Richard', 'Hurson'   ,4127417417, 4127417612,'340 Fifth Avenue', 'Pittsburgh', 15213,'PA',600,'03-OCT-89',500);
INSERT INTO CUSTOMERS values(256796533, 'Mary'   , 'Davis'    ,4122582582, 4122581324,'350 Fifth Avenue', 'Pittsburgh', 15217,'PA',100,'04-MAR-93',1000);
INSERT INTO CUSTOMERS values(245312567, 'Frank'  , 'Shaw'     ,4843504021, 4843504245,'23 Fifth Avenue' , 'Allentown' , 14213,'PA',0,'05-JUN-87',50);
INSERT INTO CUSTOMERS values(251347682, 'Jones'  , 'Steward'  ,7248889898, null, '350 Fifth Avenue' , 'Philadelphia' ,  22222,'PA',150,'04-JAN-90',500);
INSERT INTO CUSTOMERS values(312567834, 'James'  , 'Sam'      ,7249879879, 7249871253,'1210ForbesAvenue','Philadelphia',22132,'PA',100,'15-AUG-88',100);
INSERT INTO CUSTOMERS values(421356312, 'Liam'   , 'Allen'    ,4846235161, null, '345 Craig Street' , 'Allentown'    ,  14213,'PA',0  ,'16-SEP-92',100);
INSERT INTO CUSTOMERS values(452167351, 'Justin' , 'Blosser'  ,4846452231, 4846452124,'231 Tenth Street' , 'Allentown', 14213,'PA',300,'01-MAY-90',100);

INSERT INTO RECORDS values(4121231231, 4124564564, TO_TIMESTAMP('25-DEC-17:11:05','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(4121231231, 7247779797, TO_TIMESTAMP('25-DEC-17:17:10','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(4124564564, 7247778787, TO_TIMESTAMP('08-AUG-19:11:05','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(7248889898, 7247779797, TO_TIMESTAMP('15-AUG-19:14:17','DD-MON-RR:HH24:MI'), 60,'CALL');
INSERT INTO RECORDS values(7248889898, 7247778787, TO_TIMESTAMP('01-SEP-19:11:03','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(7249879879, 7248889898, TO_TIMESTAMP('03-SEP-19:17:24','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(4127417417, 7249879879, TO_TIMESTAMP('05-SEP-19:18:24','DD-MON-RR:HH24:MI'), 123,'CALL');
INSERT INTO RECORDS values(4843504021, 4846235161, TO_TIMESTAMP('07-SEP-19:15:23','DD-MON-RR:HH24:MI'), 50,'CALL');
INSERT INTO RECORDS values(4846235161, 4846452231, TO_TIMESTAMP('23-SEP-19:12:23','DD-MON-RR:HH24:MI'), 120,'CALL');
INSERT INTO RECORDS values(4846452231, 7248884545, TO_TIMESTAMP('25-SEP-19:13:34','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(6243780132, 6242311322, TO_TIMESTAMP('26-SEP-19:23:12','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(6241456123, 6241121342, TO_TIMESTAMP('30-SEP-19:23:11','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(4121231231, 6241121342, TO_TIMESTAMP('02-OCT-19:11:22','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(4121231231, 7773504021, TO_TIMESTAMP('01-AUG-19:23:05','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(4121231231, 7773504245, TO_TIMESTAMP('02-AUG-19:13:10','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(4124564564, 7776235161, TO_TIMESTAMP('08-AUG-19:14:05','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(7776452231, 4121231231, TO_TIMESTAMP('15-AUG-19:21:17','DD-MON-RR:HH24:MI'), 60,'CALL');
INSERT INTO RECORDS values(7248889898, 7776452231, TO_TIMESTAMP('01-SEP-19:13:03','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(7776235161, 7248889898, TO_TIMESTAMP('03-SEP-19:15:24','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(4127417417, 2133504021, TO_TIMESTAMP('05-SEP-19:16:24','DD-MON-RR:HH24:MI'), 123,'CALL');
INSERT INTO RECORDS values(2133504245, 4122582582, TO_TIMESTAMP('07-SEP-19:11:23','DD-MON-RR:HH24:MI'), 50,'CALL');
INSERT INTO RECORDS values(2136452231, 4122582582, TO_TIMESTAMP('23-SEP-19:14:23','DD-MON-RR:HH24:MI'), 120,'CALL');
INSERT INTO RECORDS values(4846452231, 2136452231, TO_TIMESTAMP('25-SEP-19:21:34','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(4846452231, 2136235161, TO_TIMESTAMP('26-SEP-19:22:12','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(2133504021, 4846452231, TO_TIMESTAMP('30-SEP-19:12:11','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(4121231231, 2133504021, TO_TIMESTAMP('02-OCT-19:18:22','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(7686452231, 4122582582, TO_TIMESTAMP('23-SEP-19:16:23','DD-MON-RR:HH24:MI'), 230,'CALL');
INSERT INTO RECORDS values(4846452231, 7686452124, TO_TIMESTAMP('25-SEP-19:22:34','DD-MON-RR:HH24:MI'), 120,'CALL');
INSERT INTO RECORDS values(4846452231, 7686452231, TO_TIMESTAMP('26-SEP-19:23:12','DD-MON-RR:HH24:MI'), 300,'CALL');
INSERT INTO RECORDS values(7686452231, 4846452231, TO_TIMESTAMP('01-OCT-19:12:11','DD-MON-RR:HH24:MI'), 160,'CALL');
INSERT INTO RECORDS values(4121231231, 7686452345, TO_TIMESTAMP('04-OCT-19:18:22','DD-MON-RR:HH24:MI'), 550,'CALL');
INSERT INTO RECORDS values(4121231231, 7783504245, TO_TIMESTAMP('04-AUG-19:18:22','DD-MON-RR:HH24:MI'), 400,'CALL');
INSERT INTO RECORDS values(7783504021, 4122582582, TO_TIMESTAMP('25-SEP-19:22:23','DD-MON-RR:HH24:MI'), 250,'CALL');
INSERT INTO RECORDS values(4846452231, 7786235161, TO_TIMESTAMP('05-OCT-19:16:34','DD-MON-RR:HH24:MI'), 130,'CALL');
INSERT INTO RECORDS values(4121231231, 2146452231, TO_TIMESTAMP('06-AUG-19:18:22','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(2146452231, 4122582582, TO_TIMESTAMP('27-SEP-19:19:23','DD-MON-RR:HH24:MI'), 550,'CALL');
INSERT INTO RECORDS values(4846452231, 2146452345, TO_TIMESTAMP('10-OCT-19:20:34','DD-MON-RR:HH24:MI'), 150,'CALL');
INSERT INTO RECORDS values(4843504021, 4846235161, TO_TIMESTAMP('13-OCT-19:15:23','DD-MON-RR:HH24:MI'), 50,'CALL');
INSERT INTO RECORDS values(4846235161, 4846452231, TO_TIMESTAMP('13-OCT-19:12:23','DD-MON-RR:HH24:MI'), 120,'CALL');
INSERT INTO RECORDS values(4846452231, 4846235161, TO_TIMESTAMP('25-OCT-19:13:34','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(6243780132, 6242311322, TO_TIMESTAMP('01-NOV-19:23:12','DD-MON-RR:HH24:MI'), 200,'CALL');
INSERT INTO RECORDS values(6241456123, 6241121342, TO_TIMESTAMP('05-NOV-19:23:11','DD-MON-RR:HH24:MI'), 100,'CALL');
INSERT INTO RECORDS values(7248889898, 7247779797, TO_TIMESTAMP('25-DEC-19:10:17','DD-MON-RR:HH24:MI'), 60,'CALL');
INSERT INTO RECORDS values(7248889898, 7247779797, TO_TIMESTAMP('25-DEC-19:15:17','DD-MON-RR:HH24:MI'), 120,'CALL');

INSERT INTO STATEMENTS values(4121231231, '01-AUG-19','31-AUG-19', 250, 0, 39.99,  0);
INSERT INTO STATEMENTS values(4124564564, '01-AUG-19','31-AUG-19', 600, 0, 299.99, 200.99);
INSERT INTO STATEMENTS values(4127897897, '01-AUG-19','31-AUG-19', 650, 0, 59.99,  0);
INSERT INTO STATEMENTS values(4127417417, '01-AUG-19','31-AUG-19', 517, 0, 49.99,  49.99);
INSERT INTO STATEMENTS values(4122582582, '01-AUG-19','31-AUG-19', 500, 0, 139.99, 39.99);
INSERT INTO STATEMENTS values(4843504021, '01-AUG-19','31-AUG-19', 230, 0, 59.99,  0);
INSERT INTO STATEMENTS values(7248889898, '01-AUG-19','31-AUG-19', 50,  0, 25.99,  25.99);
INSERT INTO STATEMENTS values(7249879879, '01-AUG-19','31-AUG-19', 700, 0, 159.99, 50);
INSERT INTO STATEMENTS values(4846235161, '01-AUG-19','31-AUG-19', 200, 0, 199.99, 100);
INSERT INTO STATEMENTS values(4846452231, '01-AUG-19','31-AUG-19', 500, 0, 179.99, 59.99);

INSERT INTO PAYMENTS values(4121231231, '05-AUG-19', 39.99);
INSERT INTO PAYMENTS values(4121231231, '04-SEP-19', 39.99);
INSERT INTO PAYMENTS values(4124564564, '03-AUG-19', 100);
INSERT INTO PAYMENTS values(4127897897, '06-AUG-19', 39.99);
INSERT INTO PAYMENTS values(4127417417, '06-AUG-19', 10.00);
INSERT INTO PAYMENTS values(4121231231, '05-Aug-18', 39.99);
INSERT INTO PAYMENTS values(4121231231, '04-Sep-18', 39.99);
INSERT INTO PAYMENTS values(4124564564, '03-Aug-18', 100);
INSERT INTO PAYMENTS values(4127897897, '06-Aug-18', 39.99);
INSERT INTO PAYMENTS values(4127417417, '06-Aug-18', 10.00);

COMMIT;

--Q8a
SELECT C.fname || ' ' || C.lname AS FULL_NAME, C.cell_pn,
    CASE
        WHEN amount_due IS NULL THEN 0
        WHEN ( C.city = 'Pittsburgh' ) AND amount_due-20 >= 0 THEN amount_due-20
        WHEN ( C.city = 'Philadelphia' ) AND amount_due-15 >= 0 THEN amount_due-15
        WHEN ( C.city = 'Pittsburgh' ) AND amount_due-20 < 0 THEN 0
        WHEN ( C.city = 'Philadelphia' ) AND amount_due-15 < 0 THEN 0
        ELSE amount_due END AS amount_due
    FROM CUSTOMERS C LEFT JOIN STATEMENTS S on C.cell_pn = S.cell_pn;

--Q8b
SELECT c.comp_name AS COMPANY_NAME, c.state AS STATE,
    CASE
        WHEN ( c.state = 'PA') AND CHARGE_RATE-0.05 >= 0.01 AND (c.comp_name != 'P_Mobile') THEN (CHARGE_RATE-0.05)*b.TOTAL_MINUTES
        WHEN ( c.state = 'PA') AND CHARGE_RATE-0.05 < 0.01 AND (c.comp_name != 'P_Mobile') THEN (0.01)*b.TOTAL_MINUTES
        WHEN ( c.state != 'PA') THEN (CHARGE_RATE+0.10)*b.TOTAL_MINUTES
        ELSE CHARGE_RATE*b.TOTAL_MINUTES END AS amount_due
    FROM COMPANY c LEFT JOIN COMP_BILL b on c.comp_ID = b.comp_ID
    WHERE b.start_date >= '01-APR-19' AND b.end_date <= '31-MAY-19';

--Q9a
--View that selects all the latest end dates
CREATE OR REPLACE VIEW LATEST_DATES (COMP_ID, END_DATE)
AS
    SELECT COMPANY.comp_ID, max(COMP_BILL.end_date) AS LATEST_END_DATE
    FROM COMPANY LEFT JOIN COMP_BILL on COMPANY.comp_ID = COMP_BILL.comp_ID
    GROUP BY COMPANY.comp_ID;

--View that takes LATEST_DATES and yields the amount due for each date
CREATE OR REPLACE VIEW LATEST_AMOUNT_DUE(COMP_ID, AMOUNT_DUE)
AS
    SELECT LD.COMP_ID AS COMP_ID, CB.AMOUNT_DUE
    FROM LATEST_DATES LD LEFT JOIN COMP_BILL CB on LD.COMP_ID = CB.COMP_ID
    WHERE LD.END_DATE = CB.end_date;

CREATE OR REPLACE TRIGGER DISPLAY_LATEST_DUES
    BEFORE UPDATE OF CHARGE_RATE ON COMPANY
    FOR EACH ROW
    DECLARE LATEST_DUE number(10, 2)
    BEGIN
        select AMOUNT_DUE INTO LATEST_DUE FROM LATEST_AMOUNT_DUE;
        DBMS_OUTPUT.PUT_LINE(LATEST_DUE);
    END;
/

--Q9b-

CREATE OR REPLACE TRIGGER BILLING
    AFTER INSERT ON RECORDS
    FOR EACH ROW
    DECLARE MINUTES number;
    BEGIN
         SELECT duration INTO MINUTES FROM RECORDS, STATEMENTS WHERE cell_pn = to_pn OR cell_pn = from_pn;
         UPDATE STATEMENT
         SET total_minutes = total_minutes + MINUTES;
    END;

CREATE OR REPLACE PROCEDURE PROC_UPDATE_CHARGE_RATE(input_comp_id IN number, rate_charge in number(6,2)) AS RATE(6, 2)
BEGIN
    SELECT CHARGE_RATE INTO RATE(6,2) FROM COMPANY;
    UPDATE COMPANY SET CHARGE_RATE = rate_charge WHERE COMPANY.comp_ID = input_comp_id;
END;

SELECT * FROM COMPANY;
SET TRANSACTION read write;
set constraints all deferred;
call PROC_UPDATE_CHARGE_RATE(2, 0.22);
SELECT * FROM COMPANY;

--Q11

SELECT * FROM RECORDS;
SELECT * FROM STATEMENTS;
INSERT INTO RECORDS values(4121231231, 2146452231, TO_TIMESTAMP('10-AUG-19:18:22','DD-MON-RR:HH24:MI'), 100,'CALL');
SELECT * FROM STATEMENTS;