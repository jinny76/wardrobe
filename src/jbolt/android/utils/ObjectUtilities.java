/*
 * File: $RCSfile: ObjectUtilities.java,v $
 *
 * Copyright (c) 2009 Kewill,
 *
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information
 * of Kewill ("Confidential Information"). You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered
 * into with Kewill.
 */
package jbolt.android.utils;

import java.io.*;
import java.math.BigDecimal;

/**
 * Object utilities class, includes several methods responsible for reducing duplicated code for
 * common logic, such comparison between two objects and getting boolean value from string object, etc.
 *
 * @author feng.xie
 * @version $Revision: 1.25 $
 */
public class ObjectUtilities {

    /**
     * Compare two object, denote whether these two objects are different , skip null value
     *
     * @param o1 <code>java.lang.Object</code>
     * @param o2 <code>java.lang.Object</code>
     * @return if o1 and o2 have null value, then return true, else, return o1.equals(o2)
     */
    public static boolean isDifferent(Object o1, Object o2) {
        boolean isDifferent = false;
        if (o1 != null ^ o2 != null) {
            isDifferent = true;
            if (o1 != null) {
                if (o1 instanceof String) {
                    if (StringUtilities.replaceNull(o1).equals(StringUtilities.replaceNull(o2))) {
                        isDifferent = false;
                    }
                }
            } else {
                if (o2 instanceof String) {
                    if (StringUtilities.replaceNull(o1).equals(StringUtilities.replaceNull(o2))) {
                        isDifferent = false;
                    }
                }
            }
        } else {
            if (o1 != null) {
                if (!o1.equals(o2)) {
                    isDifferent = true;
                }
            }
        }
        return isDifferent;
    }

    /**
     * Compare two object approximately. If types of o1 and o2 are string, then
     * if o1 begin with o2, return false.
     *
     * @param o1 <code>java.lang.Object</code>
     * @param o2 <code>java.lang.Object</code>
     * @return boolean value denotes whether two objects are different.
     */
    public static boolean approxDifferent(Object o1, Object o2) {
        boolean isDifferent = false;
        if (o1 != null ^ o2 != null) {
            isDifferent = true;
        } else {
            if (o1 != null) {
                if (o1 instanceof String && o2 instanceof String) {
                    String s1 = ((String) o1).trim().toLowerCase();
                    String s2 = (((String) o2)).trim().toLowerCase();
                    if (!s1.startsWith(s2)) {
                        isDifferent = true;
                    }
                } else if (!o1.equals(o2)) {
                    isDifferent = true;
                }
            }
        }
        return isDifferent;
    }

    /**
     * get boolean value from <code>java.lang.Boolean</code>, skip null value
     *
     * @param value <code>java.lang.Boolean</code>
     * @return if value if null or has false value return false, else return true
     */
    public static boolean booleanValueForBoolean(Boolean value) {
        return value != null && value;
    }

    /**
     * get boolean value from <code>java.lang.Integer</code>, skip null value
     *
     * @param value <code>java.lang.Integer</code>
     * @return if value if null or its value is 0 return false, else return true
     */
    public static boolean booleanValueForInteger(Integer value) {
        return value != null && value == 1;
    }

    /**
     * get boolean value from <code>java.lang.String</code>, skip null value
     *
     * @param value <code>java.lang.value</code>
     * @return if value if null or its value is 0 return false, else return true
     */
    public static boolean booleanValueForString(String value) {
        return "true".equals(value) || "1".equals(value);
    }

    /**
     * Return boolean value denotes whether the specified value is null.
     * Additionally, If className is "java.lang.String" and the value is empty, then
     * return true.
     *
     * @param value     Value to be checked
     * @param className Class name of value
     * @return if value is empty return true, else return false.
     */
    public static boolean isNullOrEmpty(Object value, String className) {
        boolean isEmpty = false;
        if ("java.lang.String".equals(className)) {
            if (StringUtilities.isEmpty(value)) {
                isEmpty = true;
            }
        } else {
            isEmpty = (value == null);
        }
        return isEmpty;
    }

    /**
     * Return byte array of serializable object
     *
     * @param object serializable object
     * @return <code>byte[]</code>
     * @throws java.io.IOException <code>java.io.IOException</code>
     */
    public static byte[] getObjectByteArray(Serializable object) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(object);
        oos.close();
        return bos.toByteArray();
    }

    /**
     * Deserialize byte array to object
     *
     * @param objByteArray <code>bytep[</code>
     * @return <code>java.lang.Object</code>
     * @throws java.io.IOException    <code>java.io.IOException</code>
     * @throws ClassNotFoundException <code>java.lang.ClassNotFoundException</code>
     */
    public static Object readObject(byte[] objByteArray) throws IOException, ClassNotFoundException {
        Object result;
        ByteArrayInputStream bis = new ByteArrayInputStream(objByteArray);
        ObjectInputStream is = new ObjectInputStream(bis);
        result = is.readObject();
        is.close();
        return result;
    }

    /**
     * Compare two object
     *
     * @param o1 object 1
     * @param o2 object 2
     * @return {@link Comparable#compareTo(Object)}
     */
    @SuppressWarnings("unchecked")
    public static int compareTwoObject(Object o1, Object o2) {
        Class objClass;
        if (o1 == null && o2 == null) {
            return 0;
        } else if (o1 == null) {
            return -1;
        } else if (o2 == null) {
            return 1;
        }
        objClass = o1.getClass();
        if (o1 instanceof Comparable && o2 instanceof Comparable) {
            int result = ((Comparable) o1).compareTo(o2);
            return normalizeResult(result);
        } else if (objClass.getSuperclass() == Number.class) {
            Number n1 = (Number) o1;
            double d1 = n1.doubleValue();
            Number n2 = (Number) o2;
            double d2 = n2.doubleValue();
            if (d1 < d2) {
                return -1;
            } else if (d1 > d2) {
                return 1;
            } else {
                return 0;
            }
        } else if (objClass == Boolean.class) {
            Boolean bool1 = (Boolean) o1;
            boolean b1 = bool1.booleanValue();
            Boolean bool2 = (Boolean) o2;
            boolean b2 = bool2.booleanValue();
            if (b1 == b2) {
                return 0;
            } else if (b1) {
                return 1;
            } else {
                return -1;
            }
        } else if (objClass == BigDecimal.class) {
            BigDecimal n1 = (BigDecimal) o1;
            double d1 = n1.doubleValue();
            BigDecimal n2 = (BigDecimal) o2;
            double d2 = n2.doubleValue();
            if (d1 < d2) {
                return -1;
            } else if (d1 > d2) {
                return 1;
            } else {
                return 0;
            }
        } else {
            String s1 = o1.toString();
            String s2 = o2.toString();
            int result = s1.compareTo(s2);
            return normalizeResult(result);
        }
    }

    private static int normalizeResult(int compareResult) {
        if (compareResult < 0) {
            return -1;
        } else if (compareResult > 0) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * Return exception stack of a specified exception
     *
     * @param e Exception
     * @return Exception stack
     */
    public static String printExceptionStack(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        printWriter.flush();
        return stringWriter.getBuffer().toString();
    }


    /**
     * Transform input stream to byte[]
     *
     * @param input Input stream
     * @return Content of input stream
     * @throws Exception Runtime exception
     */
    public static byte[] transformInputStream(InputStream input) throws Exception {
        byte[] byt = null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int b = 0;
        b = input.read();
        while (b != -1) {
            baos.write(b);
            b = input.read();
        }
        byt = baos.toByteArray();
        baos.close();
        return byt;
    }

}
/**
 * History:
 *
 * $Log: ObjectUtilities.java,v $
 * Revision 1.25  2010/03/17 09:37:57  fxie
 * no message
 *
 * Revision 1.24  2010/03/15 09:59:53  fxie
 * no message
 *
 * Revision 1.23  2009/12/21 15:35:53  fxie
 * no message
 *
 * Revision 1.22  2009/12/20 03:00:38  fxie
 * no message
 *
 * Revision 1.21  2009/10/20 05:44:26  fxie
 * *** empty log message ***
 *
 * Revision 1.20  2009/09/29 03:20:10  fxie
 * update
 *
 * Revision 1.19  2009/09/27 09:42:07  fxie
 * no message
 *
 * Revision 1.18  2009/09/15 12:47:12  fxie
 * no message
 *
 * Revision 1.17  2009/09/14 07:37:21  fxie
 * no message
 *
 * Revision 1.16  2009/09/10 07:31:19  fxie
 * no message
 *
 * Revision 1.15  2009/09/04 02:33:39  fxie
 * no message
 *
 * Revision 1.14  2009/09/01 06:44:59  fxie
 * no message
 *
 * Revision 1.13  2009/08/21 09:27:48  fxie
 * no message
 *
 * Revision 1.12  2009/08/20 13:04:48  fxie
 * no message
 *
 * Revision 1.11  2009/08/14 06:57:42  fxie
 * no message
 *
 * Revision 1.10  2009/08/04 16:13:52  fxie
 * Failed commit: Default (2)
 *
 * Revision 1.9  2009/07/29 03:09:53  fxie
 * *** empty log message ***
 *
 * Revision 1.8  2009/05/28 10:20:23  fxie
 * no message
 *
 * Revision 1.7  2009/05/27 09:36:38  fxie
 * no message
 *
 * Revision 1.6  2009/03/28 16:54:56  fxie
 * no message
 *
 * Revision 1.5  2009/03/14 15:33:28  fxie
 * no message
 *
 * Revision 1.4  2009/03/14 06:28:31  fxie
 * no message
 *
 * Revision 1.3  2009/03/12 13:01:49  fxie
 * no message
 *
 * Revision 1.2  2009/03/09 07:10:57  fxie
 * no message
 *
 * Revision 1.1  2009/02/24 06:09:46  fxie
 * no message
 *
 * Revision 1.1  2008/10/17 10:03:26  fxie
 * *** empty log message ***
 *
 * Revision 1.8  2008/08/11 12:28:44  fxie
 * add new component
 *
 * Revision 1.7  2008/05/17 08:37:01  fxie
 * add new component
 *
 * Revision 1.6  2008/02/18 12:21:00  fxie
 * add data source executor
 *
 * Revision 1.5  2007/11/26 15:19:26  fxie
 * add functions in designer
 *
 * Revision 1.4  2007/09/10 14:02:53  fxie
 * add jdbc query fixture
 *
 * Revision 1.3  2007/08/02 14:36:00  fxie
 * no message
 *
 * Revision 1.2  2007/07/28 15:18:00  fxie
 * no message
 *
 * Revision 1.1  2007/06/30 02:21:25  fxie
 * Failed commit: Default (2)
 *
 * Revision 1.1  2007/06/18 11:38:23  fxie
 * no message
 *
 * Revision 1.1  2007/06/16 06:22:22  fxie
 * no message
 *
 * Revision 1.1  2007/06/16 05:47:52  fxie
 * no message
 *
 */


