create table gameon.rs_number_gene (
ID BIGINT AUTO_INCREMENT PRIMARY KEY,
RS_NUMBER varchar(255),
GENE TEXT
);

ALTER TABLE gameon.rs_number_gene CHANGE RS_NUMBER RS_NUMBER varchar(255);

ALTER TABLE gameon.rs_number_gene ADD INDEX IDX_RSGENE_RS_NUMBER (RS_NUMBER);

create table gameon.rs (
RS_NUMBER VARCHAR(255)
);

create table gameon.rs_gene(
rs_number varchar(50) primary key,
gene varchar(500)
);


