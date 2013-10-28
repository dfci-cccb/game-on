-- h2
SELECT * FROM CSVREAD('~/geneinfo.only.withHeader.txt', NULL, 'fieldSeparator=' || CHAR(9)) ;

DROP TABLE IF EXISTS RS_GENE_IMPORT
CREATE CACHED TABLE RS_GENE_IMPORT(
    RS_NUMBER VARCHAR(255) PRIMARY KEY,
    GENE VARCHAR(255) 
)
CREATE PRIMARY KEY PUBLIC.PK_RS_GENE ON PUBLIC.RS_GENE_IMPORT(ID)

INSERT INTO RS_GENE_IMPORT(RS_NUMBER, GENE)
SELECT * FROM CSVREAD('~/git/game-on/data/gameon.rsnumber-gene-match.txt', NULL, 'fieldSeparator=' || CHAR(9)) ;


CALL CSVWRITE('~/git/game-on/data/genes/rs.only.txt', 'SELECT MARKER_NAME from SNP;');
/*2608508 records written*/

/*1102204 matches*/
select count(*) from rs_gene_import rs inner join snp  on rs.rs_number = snp.marker_name;


/*h2 - match marker_name to gene */
select snp.rs_number, rs.gene from rs_gene_import rs inner join snp  on rs.rs_number = snp.marker_name;

/*add the gene column*/
ALTER TABLE SNP ADD COLUMN GENE_SYMBOL VARCHAR(255);
/*update the SNP table with gene info*/
select snp.marker_name, gi.rs_number, gi.gene from snp, rs_gene_import gi where snp.marker_name = gi.RS_NUMBER;
update snp set gene_symbol = (select gi.gene from rs_gene_import gi where snp.marker_name = gi.RS_NUMBER);
update snp set gene_info = (select gi.gene from rs_gene_import gi where snp.marker_name = gi.RS_NUMBER);
-- mysql
create table gameon.rs_number_gene (
ID BIGINT AUTO_INCREMENT PRIMARY KEY,
RS_NUMBER TEXT,
GENE TEXT
);

create table gameon.rs_number_gene (
ID BIGINT AUTO_INCREMENT PRIMARY KEY,
RS_NUMBER varchar(255),
GENE TEXT
);
/*ALTER TABLE gameon.rs_number_gene CHANGE RS_NUMBER RS_NUMBER varchar(255);*/
ALTER TABLE gameon.rs_number_gene ADD INDEX IDX_RSGENE_RS_NUMBER (RS_NUMBER);

-- note: file must be placed in  /var/lib/mysql/gameon
LOAD DATA INFILE 'geneinfo.only.txt' INTO TABLE gameon.rs_number_gene FIELDS TERMINATED BY '\t';

create table gameon.rs (
RS_NUMBER VARCHAR(255)
)
ALTER TABLE gameon.rs_number_gene ADD INDEX (RS_NUMBER) ;

LOAD DATA INFILE 'rs.only.txt' INTO TABLE gameon.rs FIELDS TERMINATED BY '\t' ENCLOSED BY '"';


