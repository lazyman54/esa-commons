/*
 * Copyright 2020 OPPO ESA Stack Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package esa.commons;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtils {

    public static final String yyyyMMdd = "yyyy-MM-dd";
    public static final String yyyyMMddHHmmss = "yyyy-MM-dd HH:mm:ss";

    public static Date toDate(String dateStr, String format) {
        DateFormat dataFormat = new SimpleDateFormat(format);
        try {
            return dataFormat.parse(dateStr);
        } catch (ParseException e) {
            throw ExceptionUtils.asRuntime(e);
        }
    }

    public static String toString(Date date, String format) {
        DateFormat dataFormat = new SimpleDateFormat(format);
        return dataFormat.format(date);
    }

    private DateUtils() {
    }
}
