package com.crm.guard.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ConvertionUtils {

    private ConvertionUtils() {
    }

    public static List<String> toStringCollection(String stringValue) {
        return Arrays.asList(StringUtils.split(stringValue, ","));
    }

    public static List<Long> toLongCollection(List<String> stringCollection) {
        List<Long> longCollection = new ArrayList<Long>();

        for (String s : stringCollection) {
            Number number = parseLong(s);
            if (number != null) {
                longCollection.add((Long) number);
            }
        }
        return longCollection;
    }

    public static Timestamp toTimestamp(Object value) {

        if (value instanceof Timestamp) {
            return ((Timestamp) value);
        }

        try {
            return new Timestamp(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(String.valueOf(value)).getTime());
        } catch (ParseException e) {
            return null;
        }
    }

    public static Number parseNumber(String property, String value) {
        Number numberValue;
        if (property.contains("invoice") || property.contains("debt")) {
            numberValue = parseBigDecimal(value);
        } else {
            numberValue = parseInteger(value);
        }
        return numberValue;
    }

    public static Number parseInteger(String value) {
        Integer integer = null;
        try {
            integer = Integer.parseInt(value);
        } catch (NumberFormatException e) {
        }
        return integer;
    }

    public static Number parseLong(String value) {
        Long val = null;
        try {
            val = Long.parseLong(value);
        } catch (NumberFormatException e) {
        }
        return val;
    }

    public static Number parseBigDecimal(String value) {
        BigDecimal bigDecimal = null;
        try {
            bigDecimal = BigDecimal.valueOf(Integer.parseInt(value));
        } catch (NumberFormatException e) {
        }
        return bigDecimal;
    }
}
