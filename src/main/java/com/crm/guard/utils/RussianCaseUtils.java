package com.crm.guard.utils;

import java.util.HashMap;
import java.util.Map;

public final class RussianCaseUtils {

    public static Map<CASE, String> rubleCases = new HashMap<CASE, String>();
    static {
        rubleCases.put(RussianCaseUtils.CASE.single, "рубль");
        rubleCases.put(RussianCaseUtils.CASE.nominative, "рубля");
        rubleCases.put(RussianCaseUtils.CASE.genitive, "рублей");
    }

    public enum CASE {
        nominative, genitive, single
    }

    public static CASE defineCase(int number) {
        number = number % 10;
        if (number == 1)
            return CASE.single;
        if (number == 2 || number == 3 || number == 4 || number == 10)
            return CASE.nominative;
        else
            return CASE.genitive;
    }



}


