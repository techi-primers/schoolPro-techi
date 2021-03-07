package com.zak.pro.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author EL KOTB ZAKARIA
 *
 */
@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {

	private static final String SEPARATOR = ";";

	@Override
	public String convertToDatabaseColumn(List<String> attribute) {
		if (attribute != null) {
			return String.join(StringListConverter.SEPARATOR, attribute);
		}
		return null;
	}

	@Override
	public List<String> convertToEntityAttribute(String dbData) {
		if (dbData != null) {
			return new ArrayList<String>(Arrays.asList(dbData.split(StringListConverter.SEPARATOR)));
		}
		return new ArrayList<String>();
	}

}
