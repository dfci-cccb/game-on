// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package edu.dfci.cccb.gameon.web;

import edu.dfci.cccb.gameon.domain.Snp;
import edu.dfci.cccb.gameon.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Snp, String> ApplicationConversionServiceFactoryBean.getSnpToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<edu.dfci.cccb.gameon.domain.Snp, java.lang.String>() {
            public String convert(Snp snp) {
                return new StringBuilder().append(snp.getBetavalue()).append(' ').append(snp.getBuild()).append(' ').append(snp.getChromosome()).append(' ').append(snp.getCoordinate()).toString();
            }
        };
    }
    
    public Converter<Long, Snp> ApplicationConversionServiceFactoryBean.getIdToSnpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, edu.dfci.cccb.gameon.domain.Snp>() {
            public edu.dfci.cccb.gameon.domain.Snp convert(java.lang.Long id) {
                return Snp.findSnp(id);
            }
        };
    }
    
    public Converter<String, Snp> ApplicationConversionServiceFactoryBean.getStringToSnpConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, edu.dfci.cccb.gameon.domain.Snp>() {
            public edu.dfci.cccb.gameon.domain.Snp convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Snp.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getSnpToStringConverter());
        registry.addConverter(getIdToSnpConverter());
        registry.addConverter(getStringToSnpConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
