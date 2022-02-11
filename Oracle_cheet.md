# Oracle cheat sheet
--------------------
## SQL Plus - nicer formatting
```
set colsep '|'
set linesize 167
set pagesize 30
set pagesize 1000
```
Change user in sqlplus with
```
conn system/ root
```

## Schema basics
### [Table details](http://docs.oracle.com/cd/B28359_01/server.111/b28310/tspaces014.htm#ADMIN11413)
_who owns which tables and where are they?_
```
select table_name, owner, tablespace_name from all_tables;
```
_show tablespace details - what their storage settings are_
```
SELECT TABLESPACE_NAME "TABLESPACE",
   INITIAL_EXTENT "INITIAL_EXT",
   NEXT_EXTENT "NEXT_EXT",
   MIN_EXTENTS "MIN_EXT",
   MAX_EXTENTS "MAX_EXT",
   PCT_INCREASE
   FROM DBA_TABLESPACES;
```
_show tablespace details and which tables are associated with them_
```
SELECT  FILE_NAME, BLOCKS, TABLESPACE_NAME
   FROM DBA_DATA_FILES;
```
_all tables for a given user/ schema_
```
  SELECT owner, table_name FROM dba_tables
```
_tables containing a known column name_
```
SELECT table_name, column_name
FROM cols
WHERE table_name LIKE 'OAM%' AND column_name LIKE '%CUST%';
```
### Table privileges
_Query the schema privileges for specific tables_
```
SELECT TABLE_SCHEMA, TABLE_NAME, PRIVILEGE
FROM ALL_TAB_PRIVS
WHERE TABLE_SCHEMA = 'OL_MASTER';
```
## DML
Create a user/ schema with tablespace and tables similar to DVSOL1_1
### [Database](http://docs.oracle.com/cd/B19306_01/server.102/b14200/statements_5004.htm#i2061233)
_assume this is already in place and default character sets and max tablespace sizes etc are acceptable_
### Tablespace
_These examples specify SMALLFILE (rather than BIGFILE), since we only need a modest database. (BIGFILE and SMALLFILE override any default settings for the database)_  
```
CREATE SMALLFILE TABLESPACE "D_DATA_01" DATAFILE  'C:\ProgramData\joe\centrica\Oracle\OracleXE\tablespace\data\d_data_01.dbf'
SIZE 10M LOGGING
;
```
### [Users](http://docs.oracle.com/cd/B28359_01/network.111/b28531/users.htm#DBSEG97869)
(Create user requires CREATE USER system privilege)
```
CREATE USER OL_MASTER
    IDENTIFIED BY ROLE_MASTER
    DEFAULT TABLESPACE D_DATA_01 REMARK default setting for the default tablespaces of all users is the SYSTEM tablespace - which is ok until a user needs to create objects, at which point it's advisable to separate user data from system data (i.e. avoid the system tablespace)
    QUOTA 5M ON D_DATA_01
    TEMPORARY TABLESPACE temp;
```
_(Creating with OL_MASTER - having granted privileges as shown [below](#grants))
```
CREATE USER OL_WEB_USER
    IDENTIFIED BY ROLE_WEB_USER
    DEFAULT TABLESPACE D_DATA_01
    QUOTA 5M ON D_DATA_01
    TEMPORARY TABLESPACE temp;
```
### Grants
_(CREATE SESSION provides similar privileges to CONNECT)_
```
GRANT CREATE SESSION, CREATE USER, CREATE TABLE, CREATE SEQUENCE TO OL_MASTER;
```
### Tables and Grants (to OL_WEB_USER)
#### Email tables and sequences
```
CREATE TABLE "OL_MASTER"."PO_TA_NOTIFICATION_DETAILS"
 (	"NOTIFICATION_ID" NUMBER,
"UCRN" VARCHAR2(14 BYTE),
"NOTIFICATION_SEND_TIMESTAMP" DATE,
"NOTIFICATION_CREATED_TIMESTAMP" DATE,
"NOTIFICATION_STATUS" VARCHAR2(10 BYTE),
"NOTIFICATION_SEND_COUNT" NUMBER(38,0),
"NOTIFICATION_EMAIL_ADDRESS" VARCHAR2(100 BYTE),
"NOTIFICATION_TYPE_ID" NUMBER,
"CUSTOMER_ID" NUMBER,
"NOTIFICATION_TEMPLATE_ID" NUMBER
 )
TABLESPACE "D_DATA_01" ;
GRANT DELETE, INSERT, SELECT, UPDATE ON "OL_MASTER"."PO_TA_NOTIFICATION_DETAILS" TO "OL_WEB_USER";
```
```
CREATE TABLE "OL_MASTER"."PO_TR_NOTIF_EMAIL_TEMPLATE"
   (	"TEMPLATE_ID" NUMBER,
  "EMAIL_TEMPLATE_ID" NUMBER,
  "EMAIL_TEMPLATE_PATH" VARCHAR2(200 BYTE),
  "CREATION_TIMESTAMP" TIMESTAMP (9),
  "BRAND_TEXT" VARCHAR2(100 BYTE),
  "IS_CQ_TEMPLATE" CHAR(1 BYTE) DEFAULT 'N',
  "IS_NEW_STACK" CHAR(1 BYTE) DEFAULT 'N'
   )
  TABLESPACE "D_DATA_01" ;
  GRANT DELETE, INSERT, SELECT, UPDATE ON "OL_MASTER"."PO_TR_NOTIF_EMAIL_TEMPLATE" TO "OL_WEB_USER";
```
```
CREATE TABLE "OL_MASTER"."PO_TR_NOTIFICATION_TYPE"
   (	"NOTIFICATION_TYPE_ID" NUMBER,
  "NOTIFICATION_TYPE" VARCHAR2(16 BYTE),
  "NOTIFICATION_TEMPLATE_ID" NUMBER,
  "NOTIFICATION_TEMPLATE" CLOB,
  "CREATION_TIMESTAMP" TIMESTAMP (9)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "D_DATA_01"
 LOB ("NOTIFICATION_TEMPLATE") STORE AS BASICFILE (
  TABLESPACE "D_DATA_01" ENABLE STORAGE IN ROW CHUNK 8192 PCTVERSION 10
  NOCACHE LOGGING ) ;
  GRANT DELETE, INSERT, SELECT, UPDATE ON "OL_MASTER"."PO_TR_NOTIFICATION_TYPE" TO "OL_WEB_USER";
```
```
CREATE TABLE "OL_MASTER"."PO_TA_EMAIL_PARAMETERS"
 (	"PARAMETER_ID" NUMBER,
"NOTIFICATION_ID" NUMBER,
"INSERTION_TIMESTAMP" TIMESTAMP (6),
"PARAMETER_MAP" BLOB
 )
TABLESPACE "D_DATA_01"
LOB ("PARAMETER_MAP") STORE AS BASICFILE (
TABLESPACE "D_DATA_01" ENABLE STORAGE IN ROW CHUNK 8192 RETENTION
NOCACHE LOGGING
) ;
REMARK index
CREATE UNIQUE INDEX "OL_MASTER"."PO_TA_EMAIL_PARAMETERS_PK" ON "OL_MASTER"."PO_TA_EMAIL_PARAMETERS" ("PARAMETER_ID")
TABLESPACE "D_DATA_01" ;
REMARK foreign key constraint
ALTER TABLE "OL_MASTER"."PO_TA_EMAIL_PARAMETERS" ADD CONSTRAINT "PO_TA_EMAIL_PARAMETERS_PK" PRIMARY KEY ("PARAMETER_ID")
USING INDEX TABLESPACE "D_DATA_01"  ENABLE;
ALTER TABLE "OL_MASTER"."PO_TA_EMAIL_PARAMETERS" MODIFY ("PARAMETER_ID" NOT NULL ENABLE);
```
```
REMARK sequence
CREATE SEQUENCE  "OL_MASTER"."NOTIFICATION_REFERENCE_SEQ"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 10001602 NOCACHE  NOORDER  NOCYCLE ;
```
```
REMARK sequence
CREATE SEQUENCE  "OL_MASTER"."NOTIFICATION_ID_SEQ"  MINVALUE 1 MAXVALUE 999999999999999999999999999 INCREMENT BY 1 START WITH 1000206852 CACHE 20 NOORDER  NOCYCLE ;
GRANT SELECT ON "OL_MASTER"."NOTIFICATION_ID_SEQ" TO "OL_WEB_USER";
````

## It's not working in SQLPlus!
Connect to the db as the SYS user with SYSDBA privileges
```
conn SYS as SYSDBA
REMARK run some scripts, intended to correct any malfunctioning (this allowed me to execute DROP USER... statements that were previously failing).
@ ?/rdbms/admin/catalog
@ ?/rdbms/admin/catproc
```
Turn on tracing
```
alter session set sql_trace=true;
```
## Docs
[Oracle dba guide](http://docs.oracle.com/cd/B28359_01/server.111/b28310/tables014.htm#ADMIN01508)
