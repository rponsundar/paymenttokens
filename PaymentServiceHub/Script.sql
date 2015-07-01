--<ScriptOptions statementTerminator=";"/>

ALTER TABLE cust_card DROP PRIMARY KEY;

ALTER TABLE cust_pymt_token DROP PRIMARY KEY;

ALTER TABLE cust_urn_token DROP PRIMARY KEY;

ALTER TABLE cust_urn_token DROP INDEX TOKEN_ID;

ALTER TABLE cust_pymt_token DROP INDEX CARD_ID;

DROP TABLE cust_pymt_token;

DROP TABLE cust_card;

DROP TABLE cust_urn_token;

CREATE TABLE cust_pymt_token (
	ID INT NOT NULL AUTO_INCREMENT,
	TOKEN BIGINT DEFAULT 0 NOT NULL,
	EXP_DATE DATE DEFAULT '0000-00-00' NOT NULL,
	CARD_ID INT DEFAULT 0 NOT NULL,
	PAN_LAST4_DIGIT INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (ID)
);


DROP TABLE CARD_TOKEN_VAULT;

create table CARD_TOKEN_VAULT (
ID INT NOT NULL AUTO_INCREMENT,
PAN BIGINT DEFAULT 0 NOT NULL,
PAN_EXP_DATE varchar(40) DEFAULT NULL,
PAN_LAST4_DIGIT INT DEFAULT 0 NOT NULL,
TOKEN BIGINT DEFAULT 0 NOT NULL,
TOKEN_EXP_DATE varchar(40) DEFAULT NULL,
PRIMARY KEY (ID)
);

select * from Cust_urn_token

CREATE TABLE cust_card (
	ID INT NOT NULL AUTO_INCREMENT,
	PAN_NUM BIGINT DEFAULT 0 NOT NULL,
	EXP_DATE DATE DEFAULT '0000-00-00' NOT NULL,
	CUST_ID INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (ID)
);

CREATE TABLE cust_urn_token (
	ID INT NOT NULL AUTO_INCREMENT,
	URN VARCHAR(8) DEFAULT '00000000' NOT NULL,
	EXP_DATE DATE DEFAULT '0000-00-00' NOT NULL,
	MONETARY_VALUE FLOAT DEFAULT 0 NOT NULL,
	TOKEN_ID INT DEFAULT 0 NOT NULL,
	PRIMARY KEY (ID)
);

CREATE INDEX TOKEN_ID ON cust_urn_token (TOKEN_ID ASC);

CREATE INDEX CARD_ID ON cust_pymt_token (CARD_ID ASC);
commit;

select * from cust_urn_token
select token.token, token.exp_date, urn from cust_urn_token urn, cust_pymt_token token where urn.token_id = token.id;

DROP TABLE IF EXISTS oauth_client_details;

CREATE TABLE oauth_client_details (
  client_id varchar(40) NOT NULL,
  client_name varchar(256) default NULL,
  client_domain varchar(256) default NULL,
  resource_ids varchar(256) DEFAULT NULL,
  client_secret varchar(256) DEFAULT NULL,
  scope varchar(256) DEFAULT NULL,
  authorized_grant_types varchar(256) DEFAULT NULL,
  web_server_redirect_uri varchar(256) DEFAULT NULL,
  authorities varchar(256) DEFAULT NULL,
  access_token_validity int(11) DEFAULT NULL,
  refresh_token_validity int(11) DEFAULT NULL,
  additional_information varchar(256) DEFAULT NULL,
  autoapprove varchar(256) DEFAULT NULL,
  PRIMARY KEY (client_id)
);


delete FROM oauth_client_details
select * FROM oauth_client_details
