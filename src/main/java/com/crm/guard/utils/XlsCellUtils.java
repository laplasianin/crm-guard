package com.crm.guard.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;

import java.math.BigDecimal;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.leftPad;

public class XlsCellUtils {

    private XlsCellUtils() {
    }

    public static String getStringValue(Cell cell) {
        return getStringValue(cell, 0);
    }

    public static Date getDateValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        return cell.getDateCellValue();
    }

    public static BigDecimal getBigDecimalValue(Cell cell) {
        if (cell == null) {
            return BigDecimal.ZERO;
        }
        String stringValue = getStringValue(cell);
        stringValue  = stringValue.replace(",", ".");
        return new BigDecimal(stringValue);
    }

    public static String getStringValue(Cell cell, Integer length) {
        if (cell == null) {
            return "";
        }
        try {
            String stringCellValue = cell.getStringCellValue();
            String trimmed = StringUtils.trimToEmpty(stringCellValue);
            return leftPad(trimmed, length, "0");
        } catch (Exception ignored) {}
        double numericCellValue = cell.getNumericCellValue();
        String value = String.valueOf(new BigDecimal(numericCellValue).setScale(0, BigDecimal.ROUND_CEILING));
        String trimmed = StringUtils.trimToEmpty(value);
        return leftPad(trimmed, length, "0");
    }
}
