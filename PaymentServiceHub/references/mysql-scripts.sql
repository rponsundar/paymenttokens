create table CUST_CARD (ID int NOT NULL,
    PAN_NUM int NOT NULL,
    EXP_DATE date NOT NULL,
    CUST_ID int NOT NULL, PRIMARY KEY(ID));
create table CUST_PYMT_TOKEN (
          ID int NOT NULL,
          TOKEN int NOT NULL,
          EXP_DATE date NOT NULL,
          CARD_ID int NOT NULL,
          PAN_LAST4_DIGIT int NOT NULL,
          PRIMARY KEY(ID), FOREIGN KEY (CARD_ID) REFERENCES CUST_CARD(ID));
create table CUST_URN_TOKEN       (
         ID int NOT NULL,
         URN int NOT NULL,
         EXP_DATE date NOT NULL,
         TOKEN_ID int NOT NULL,
         PRIMARY KEY(ID), FOREIGN KEY (TOKEN_ID) REFERENCES CUST_PYMT_TOKEN (ID));