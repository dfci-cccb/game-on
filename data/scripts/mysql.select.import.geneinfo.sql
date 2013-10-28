select count(*) from gameon.rs_number_gene;

select count(rs_number) from gameon.rs_number_gene;
/* all rs_numbers not null (21036432)*/

select count(distinct rs_number) from gameon.rs_number_gene;
/* all rs_numbers are unique (21036432)*/

select count(gene) from gameon.rs_number_gene;
/* 891 rs_numbers don't have a gene*/

select count(*) from gameon.rs_number_gene where rs_number is null;

select count(*) from gameon.rs_number_gene where gene is null;

select max(length(rs_number)) from gameon.rs_number_gene;
/*max rs_nubmer length is 11*/

select max(length(gene)) from gameon.rs_number_gene;
/*max gene length is 307*/

SET SQL_SAFE_UPDATES=0;

delete from rs where rs_number like ('MARKER_NAME');

select count(*) from gameon.rs;
select count(distinct rs_number) from gameon.rs;
/*all 2608508 are distinct */

select count(*) from rs inner join rs_number_gene g on rs.rs_number = g.RS_NUMBER;
/*only 1102205 rs matches found*/
/* find those rs that don't have a gene*/
select rs.rs_number, g.gene from rs left outer join rs_number_gene g on rs.rs_number = g.RS_NUMBER where g.gene is null;

/*find genes by rs_number*/
select rs.rs_number, g.gene from rs inner join rs_number_gene g on rs.rs_number = g.RS_NUMBER;

/*output rs_number, gene to file*/
select g.gene, instr(g.gene, ':'), mid(g.gene, 1, instr(g.gene, ':')-1) from rs inner join rs_number_gene g on rs.rs_number = g.RS_NUMBER;

select rs.rs_number as 'rs_number', mid(g.gene, 1, instr(g.gene, ':')-1) as 'gene' from rs inner join rs_number_gene g on rs.rs_number = g.RS_NUMBER 
into outfile 'gameon.rsnumber-gene-match.txt';
/* 1102205 */
