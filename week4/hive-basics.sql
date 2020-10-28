-- open beeline with
-- beeline -u jdbc:hive2://

-- Hive Query Language, HiveQL is very similar to SQL
-- we still talk about DML, DDL, many of the commands are the same
-- often you'll be better served by looking up how to do something in SQL then porting to Hive

-- We talked a bit aobut SQL and what an RDBMS get you.  Hive offers some
-- of those features, provided we're using *managed* tables.
-- Each table we create in Hive is either *managed*, meaning Hive controls
-- it and can provide guarantees, or *external* meaning Hive does
-- not control it and can't guarantee the underlying data.

--make the database first, this will apear in /user/hive/warehouse
CREATE DATABASE STUDENT_DB;
-- SHOW DATABASES; will check if you have it

-- use the created database
USE STUDENT_DB;

-- create a *managed* table student
CREATE TABLE STUDENT
    (SSN STRING,
    FIRST_NAME STRING,
    LAST_NAME STRING,
    AGE INT,
    STATE STRING,
    HOUSE STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    TBLPROPERTIES("skip.header.line.count"="1");

DESCRIBE STUDENT;

-- load *local* csv into the managed table
LOAD DATA LOCAL INPATH '/home/adam/student-house.csv' INTO TABLE STUDENT;

-- we can see that data in /user/hive/warehouse/student_db.db/student in HDFS
-- it's managed by Hive, since it's contained in the warehouse there.

DROP TABLE STUDENT;

-- Dropping a managed table deletes its contents on HDFS

-- Create an external version
-- add the EXTERNAL keyword and provide a location for the data in HDFS
-- will make directory for you
CREATE EXTERNAL TABLE STUDENT
    (SSN STRING,
    FIRST_NAME STRING,
    LAST_NAME STRING,
    AGE INT,
    STATE STRING,
    HOUSE STRING)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ','
    LOCATION '/user/adam/mydata'
    TBLPROPERTIES("skip.header.line.count"="1");

-- we're going to load in a file from HDFS, so copy over to hdfs if necessary.
-- hdfs dfs -put myfile .

-- Load data from HDFS into table
LOAD DATA INPATH '/user/adam/student-house.csv' INTO TABLE STUDENT;
-- the file gets moved to the location of the external table.

-- If we DROP TABLE STUDENT; now, it won't delete the file.

-- List the first 50 students with lnames sorted in descending order
SELECT * FROM STUDENT
ORDER BY LAST_NAME DESC
LIMIT 50;

-- Display house and count per house, store result in hdfs dir
INSERT OVERWRITE DIRECTORY '/user/hive/output'
ROW FORMAT DELIMITED
FIELDS TERMINATED BY '\t'
SELECT HOUSE, COUNT(HOUSE) FROM STUDENT
GROUP BY HOUSE;
-- GROUP BY seperates records into groups based on values in a column(s)
-- This lets us use aggregate functions like COUNT(), which counts the number
-- of records in one of those groups.

-- move it to local disk using hdfs dfs -get /user/hive/output/* .
-- rename with mv 000000_0 housedata.csv

-- Display all students in California or Virginia
SELECT FIRST_NAME, LAST_NAME, STATE
FROM STUDENT
WHERE UPPER(STATE)='CALIFORNIA' OR UPPER(STATE)='VIRGINIA'
ORDER BY STATE;

-- Display SSN of students whose first name begins with a C and
-- are in Hufflepuff
-- the LIKE here uses a SQL wildcard syntax
SELECT SSN
FROM STUDENT
WHERE HOUSE='Hufflepuff' AND FIRST_NAME LIKE 'C%';

-- Display all students younger than 25 and older than 18
SELECT * FROM STUDENT
WHERE AGE<25 AND AGE>18;

-- Display all Californians, Virginians, Kentuckians over 30
SELECT * FROM STUDENT
WHERE (STATE='California' OR STATE='Virginia' OR STATE='Kentucky') AND AGE>30;

-- Display the average age of all students
SELECT AVG(AGE) FROM STUDENT;

-- Display average age by house, rounded to 2 decimal places
SELECT HOUSE, ROUND(AVG(AGE), 2) FROM STUDENT
GROUP BY HOUSE;

-- beeline -f filename -u jdbc:hive2:// to run an hql script

-- can create new tables from queries
-- these will be managed tables instead of external
CREATE TABLE STUDENTS_STATE
AS SELECT COUNT(*) AS NUM_STUDENTS, STATE FROM STUDENT
GROUP BY STATE;

-- One of the ways Hive makes its fast to query our data is partitioning.
-- When we partition, we organize the tables in our database into some
-- subtables (partitions) based on values.
-- Some examples of partitions based on value: have a partition for each day of the week
-- or each month of the year.  You can partition by date range, so
-- your table has partitions for each calendar month over the business' lifetime

CREATE TABLE STUDENT_AGE
    (SSN STRING, FIRST_NAME STRING, LAST_NAME STRING, STATE STRING, HOUSE STRING)
    PARTITIONED BY (AGE INT)
    ROW FORMAT DELIMITED
    FIELDS TERMINATED BY ',';

-- add data into our partitioned table by partition:
INSERT INTO TABLE STUDENT_AGE PARTITION(AGE=50)
SELECT SSN, FIRST_NAME, LAST_NAME, STATE, HOUSE FROM STUDENT WHERE AGE=50;

INSERT INTO TABLE STUDENT_AGE PARTITION(AGE=25)
SELECT SSN, FIRST_NAME, LAST_NAME, STATE, HOUSE FROM STUDENT WHERE AGE=25;

-- we end up with two different directories in hdfs, one per partition
-- we added data for.  This makes queries faster if they involve age
-- because the db is already organized by age.

-- inserting data partition by partition is a pain:
--  let's do dynamic partitioning

-- set properties
SET hive.exec.dynamic.partition=true;
SET hive.exec.dynamic.partition.mode=nonstrict;

INSERT INTO TABLE STUDENT_AGE PARTITION(AGE)
SELECT SSN, FIRST_NAME, LAST_NAME, STATE, HOUSE, AGE FROM STUDENT;

-- this doesn't work -- todo
-- UPDATE TABLE STUDENT_AGE SET AGE=22 WHERE FIRST_NAME='Troy';


