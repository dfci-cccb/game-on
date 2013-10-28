LOAD DATA INFILE 'geneinfo.only.txt' INTO TABLE gameon.rs_number_gene FIELDS TERMINATED BY '\t' (RS_NUMBER, GENE);

LOAD DATA INFILE 'rs.only.txt' INTO TABLE gameon.rs FIELDS TERMINATED BY '\t' ENCLOSED BY '"';


