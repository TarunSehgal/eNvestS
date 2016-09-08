package com.envest.services.components.util;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import org.joda.time.LocalDate;

@Converter
public class LocalDatePersistenceConverter implements AttributeConverter<LocalDate, java.sql.Date> {

  @Override
  public java.sql.Date convertToDatabaseColumn(LocalDate entityValue) {
    if (entityValue != null) {
    // return java.sql.Date.valueOf(entityValue);
      return null;//new java.sql.Date(entityValue.gett)
    }
    return null;
  }

  @Override
  public LocalDate convertToEntityAttribute(java.sql.Date databaseValue) {
    if (databaseValue != null) {
    	LocalDate d = new LocalDate(databaseValue.getTime());
    	return d;
    }
    return null;
  }
}