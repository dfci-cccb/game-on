CREATE INDEX PUBLIC.IDX_MARKER_NAME ON PUBLIC.SNP(MARKER_NAME)
CREATE INDEX IDX_BUILD ON PUBLIC.SNP(BUILD)
CREATE INDEX IDX_CHROMOSOME_COORDINATE ON PUBLIC.SNP(CHROMOSOME, COORDINATE)
CREATE INDEX IDX_NSTUDY ON PUBLIC.SNP(N_STUDY)
