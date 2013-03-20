/*
 * File: $RCSfile: FormatUtilities.java,v $
 *
 * Copyright (c) 2005 Wincor Nixdorf International GmbH,
 * Heinz-Nixdorf-Ring 1, 33106 Paderborn, Germany
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Wincor Nixdorf ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered
 * into with Wincor Nixdorf.
 */
package jbolt.android.utils;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The class <code>com.kewill.mkernel.common.utilities.FormatUtilities</code>
 *
 * @author feng.xie, WN ASP SSD
 * @version $Revision: 1.4 $
 */

/**
 * Format utilities class
 *
 * @author feng.xie, WN ASP SSD
 * @version $Revision: 1.4 $
 */
public class FormatUtilities {

    public static final String SHORT_DATE_FORMAT = "yyyy-MM-dd";

    /**
     * Constructor
     */
    private FormatUtilities() {
    }

    /**
     * Format Date accoring to Patter of 'YYYY-MM-DD' and Locale of US
     *
     * @param dat Date to formate
     * @return Formatted Date
     */
    public static String formatDate(final Date dat) {
        return formatDate(dat, SHORT_DATE_FORMAT, Locale.US);
    }


    /**
     * Format Date accoring to Patter of 'YYYY-MM-DD 24H:mm' and Locale of US
     *
     * @param dat Date to formate
     * @return Formatted Date
     */
    public static String formatDateTime(final Date dat) {
        return formatDate(dat, "yyyy-MM-dd HH:mm", Locale.US);

    }

    /**
     * Formate Date according to Patter and Locale of US
     *
     * @param dat     Date to format
     * @param pattern Pattern of Date to format
     * @return Formatted Date
     */
    public static String formatDate(final Date dat, final String pattern) {
        return formatDate(dat, pattern, Locale.US);

    }

    /**
     * Formate Date accoring to Pattern and Locale
     *
     * @param dat     Date to formate
     * @param pattern Pattern of Date to Format
     * @param loc     Locale of Date to format
     * @return Formatted Date
     */
    public static String formatDate(
            final Date dat, final String pattern,
            final Locale loc) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
        if (dat != null) {
            return sdf.format(dat);
        } else {
            return "";
        }
    }

    /**
     * Format String in 'YYYY-MM-DD' to Date
     *
     * @param dateStr String to Convert
     * @return Formatted Date
     */
    public static Date strToDate(final String dateStr) {
        return strToDate(dateStr, SHORT_DATE_FORMAT, Locale.US);
    }

    /**
     * Format String in 'YYYY-MM-DD HH:mm' to Date
     *
     * @param dateStr String to Convert
     * @return Formatted Date
     */
    public static Date strToDateTime(final String dateStr) {
        return strToDate(dateStr, "yyyy-MM-dd HH:mm", Locale.US);
    }

    /**
     * Formate String in Pattern to Date
     *
     * @param dateStr String to Convert
     * @param pattern Convert Pattern
     * @return Formatted Date
     */
    public static Date strToDate(final String dateStr, final String pattern) {
        return strToDate(dateStr, pattern, Locale.US);
    }

    /**
     * Formate String in Pattern and Locale to Date
     *
     * @param dateStr String to Convert
     * @param pattern Convert Pattern
     * @param loc     Convert Locale
     * @return Formatted Date
     */
    public static Date strToDate(
            final String dateStr, final String pattern,
            final Locale loc) {
        if (StringUtilities.isEmpty(dateStr)) {
            return null;
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern, loc);
            return sdf.parse(dateStr);
        } catch (ParseException e) {
            Log.e(FormatUtilities.class.getName(), e.getMessage());
            return null;
        }
    }

    /**
     * Formate String in 'YYYY-MM-DD' to Timestemp
     *
     * @param dateStr String to Convert
     * @return Formatted Timestemp
     */
    public static Timestamp strToTimestamp(final String dateStr) {
        Date dat = strToDate(dateStr);
        return new Timestamp(dat.getTime());
    }

    /**
     * Formate String to Number
     *
     * @param strNum String to be converted
     * @return Number or NULL
     */
    public static String formatNumber(final String strNum) {
        NumberFormat nbf = new DecimalFormat();
        try {
            if (strNum == null) {
                return "0";
            } else {
                return nbf.parse(strNum).toString();
            }
        } catch (ParseException e) {
            Log.e(FormatUtilities.class.getName(), e.getMessage());
            return null;
        }
    }

    /**
     * format str to time str (hh:mm)
     *
     * @param str String to be converted
     * @return String
     */
    public static String formatToTimeStr(String str) {
        String result = "";
        if (str != null && str.length() > 0) {
            try {
                result = str.substring(0, 2) + ":" + str.substring(2);
            } catch (Exception e) {
                Log.e(FormatUtilities.class.getName(), e.getMessage());
                result = str;
            }
        }
        return result;
    }

    /**
     * format bigdecimal to string
     *
     * @param number bigdecimal converted to string
     * @return <code>String</code>
     */
    public static String formatBigDecimalWithPrecise(BigDecimal number) {
        String result = "";
        NumberFormat nbf = new DecimalFormat("#,##0.00");
        if (number != null) {
            result = nbf.format(number.doubleValue());
        }
        return result;
    }

    /**
     * format bigdecimal to string with specified pattern
     *
     * @param number  bigdecimal converted to string
     * @param pattern specified pattern
     * @return <code>String</code>
     */
    public static String formatBigDecimalWithPrecise(BigDecimal number, String pattern) {
        String result = "";
        NumberFormat nbf = new DecimalFormat(pattern);
        if (number != null) {
            result = nbf.format(number.doubleValue());
        }
        return result;
    }

    /**
     * format bigdecimal to string with specified pattern
     *
     * @param numberStr string value of bigdecimal
     * @param pattern   specified pattern
     * @return <code>String</code>
     */
    public static String formatStringWithPrecise(String numberStr, String pattern) {
        BigDecimal number = new BigDecimal(numberStr);
        NumberFormat nbf = new DecimalFormat(pattern);
        return nbf.format(number.doubleValue());
    }

}
/**
 * History:
 *
 * $Log: not supported by cvs2svn $
 * Revision 1.3  2008/10/23 11:51:46  fxie
 * adapt test case of dao
 *
 * Revision 1.2  2008/10/21 05:32:56  fxie
 * no message
 *
 * Revision 1.1  2008/10/21 05:14:40  fxie
 * no message
 *
 * Revision 1.2 2008/08/19 17:11:53CST feng.xie 
 * adapt utilities logic
 * Revision 1.1 2008/08/19 16:52:15CST feng.xie 
 * Initial revision
 * Member added to project d:/MKS/Asia/development/BSMP/dev/components/Utilities/src/src.pj
 * Revision 1.1 2008/07/29 16:21:00CST feng.xie
 * Initial revision
 * Member added to project d:/MKS/Asia/development/SDS/dev/components/Common/src/project.pj
 */





