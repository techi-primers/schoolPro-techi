package com.zak.pro.converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.zak.pro.enumartion.Category;

@Converter
public class CategoryListConverter implements AttributeConverter<List<Category>, String> {

	private static final String SEPARATOR = ";";

	@Override
	public String convertToDatabaseColumn(List<Category> attribute) {
		if (attribute != null) {
			return String.join(CategoryListConverter.SEPARATOR,
					attribute.stream().map(Category::name).collect(Collectors.toList()));
		}
		return null;
	}

	@Override
	public List<Category> convertToEntityAttribute(String dbData) {
		if (dbData != null) {
			List<String> names = new ArrayList<String>(Arrays.asList(dbData.split(CategoryListConverter.SEPARATOR)));
			return names.stream().map(s -> Enum.valueOf(Category.class, s)).collect(Collectors.toList());
		}
		return new ArrayList<Category>();
	}

}
