package jbolt.android.webservice.tools;

import jbolt.android.webservice.servlet.LocalMethod;
import jbolt.framework.crud.GenericCrudService;
import jbolt.framework.crud.impl.GenericCrudDefaultService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Copyright: Copyright (c) 2011</p>
 * <p>Company: Abolt Team</p>
 *
 * @author Jinni
 */
public class ServiceStubGenerator {

    private static final String VOID = "void";

    public static void main(String[] args) {
        String inputDir = args[0];
        String outputDir = args[1];
        if (!outputDir.endsWith(File.separator)) {
            outputDir += File.separator;
        }
        System.out.println("[ Start ServiceStubGenerator ...... ]");
        genStubs(inputDir.substring(0, inputDir.indexOf("\\src\\") + 5), inputDir, outputDir);
    }

    private static void genStubs(String rootDir, String inputDir, String outputDir) {
        File[] services = new File(inputDir).listFiles(
            new FileFilter() {
                public boolean accept(File file) {
                    return file.isDirectory() || file.getName().toLowerCase().endsWith(".java");
                }
            });
        for (File service : services) {
            String filePath = service.getPath();
            if (service.isDirectory()) {
                //genStubs(rootDir, filePath, outputDir);
            } else {
                String fullName = filePath.substring(rootDir.length());
                String outputFileName = outputDir + fullName.substring(0, fullName.length() - 5) + ".java";
                String className = StringUtils.replace(fullName, File.separator, ".");
                className = className.substring(0, className.length() - 5);
                genStub(className, outputFileName);
            }
        }
    }

    private static void genStub(String className, String javaFileName) {
        File targetFile = new File(javaFileName);
        targetFile.getParentFile().mkdirs();
        System.out.println("[ generate java " + javaFileName + " ...... ]");
        try {
            FileUtils.write(targetFile, genSource(className));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String genSource(String className) {
        try {
            Class<?> serviceClass = Class.forName(className);

            StringBuffer stubClass = new StringBuffer(
                serviceClass.getPackage() + ";\r\n\r\n"
                    + "import jbolt.android.stub.BaseStub;\r\n"
                    + "import android.os.*;\r\n\r\n"
                    + "public class " + serviceClass.getSimpleName());

            stubClass.append(" extends BaseStub");
            stubClass.append(" {\r\n\r\n");

            Map mapMethods = new HashMap();
            Method[] methods = serviceClass.getDeclaredMethods();
            Class superClass = serviceClass.getSuperclass();
            if (superClass.equals(GenericCrudDefaultService.class)) {
                methods = (Method[]) ArrayUtils.addAll(methods, GenericCrudService.class.getDeclaredMethods());
            }

            for (int j = 0; j < methods.length; j++) {
                Method method = methods[j];
                LocalMethod ignoreStub = method.getAnnotation(LocalMethod.class);
                boolean iocInjectMethod = method.getName().startsWith("set") && method.getName().endsWith("Manager");

                if (Modifier.isPublic(method.getModifiers()) && ignoreStub == null && !iocInjectMethod) {

                    StringBuffer stubMethod = new StringBuffer();
                    stubMethod.append("    public static ");

                    /*Class returnType = method.getReturnType();
                         if (method.getGenericReturnType() instanceof TypeVariable) {
                             returnType = getActualClass(
                                     clazz, ((TypeVariable) method.getGenericReturnType()).getName(), method.getReturnType());
                         }
                         String returnTypeName = null;
                         String returnObjectType = null;
                         if (returnType != null) {
                             returnTypeName = returnType.getName();
                             if (returnType.isArray()) {
                                 Class itemClass = returnType.getComponentType();
                                 returnTypeName = itemClass.getName() + "[]";
                             }

                             if (returnType.isPrimitive() && !void.class.equals(returnType)) {
                                 returnObjectType = getPrimitiveWrapperClass(returnType).getName();
                             } else {
                                 returnObjectType = returnTypeName;
                             }
                         } else {
                             returnTypeName = VOID;
                         }
                          */
                    String returnTypeName = VOID;
                    stubMethod.append(returnTypeName);
                    stubMethod.append(" ");
                    StringBuffer methodStr = new StringBuffer(method.getName() + "(");
                    Class[] actualParamTypes = getActualParamTypes(serviceClass, method, method.getParameterTypes());
                    Class[] paramTypes = method.getParameterTypes();
                    String[] paramTypeNames = new String[paramTypes.length];
                    String[] paramValueNames = new String[actualParamTypes.length];

                    for (int k = 0; k < paramTypes.length; k++) {
                        if (k > 0) {
                            methodStr.append(", ");
                        }
                        Class paramType = paramTypes[k];
                        Class actualParamType = actualParamTypes[k];
                        String actualParamClass = "";
                        if (paramType.isArray()) {
                            if (paramType.getComponentType().isArray()) {
                                String paramTypeName = paramType.getComponentType().getComponentType().getName();
                                actualParamClass = actualParamType.getComponentType().getComponentType().getName();
                                String paramValueName =
                                    actualParamType.getComponentType().getComponentType().getSimpleName().toLowerCase()
                                        + k;
                                paramTypeNames[k] = paramTypeName + "[][]";
                                actualParamClass += "[][]";
                                paramValueNames[k] = paramValueName;
                            } else {
                                String paramTypeName = paramType.getComponentType().getName();
                                actualParamClass = actualParamType.getComponentType().getName();
                                String paramValueName =
                                    actualParamType.getComponentType().getSimpleName().toLowerCase() + k;
                                paramTypeNames[k] = paramTypeName + "[]";
                                actualParamClass += "[]";
                                paramValueNames[k] = paramValueName;
                            }
                        } else {
                            paramTypeNames[k] = paramType.getName();
                            actualParamClass = actualParamType.getName();
                            paramValueNames[k] = actualParamType.getSimpleName().toLowerCase() + k;
                        }
                        paramTypeNames[k] = convertToClientModel(paramTypeNames[k]);
                        actualParamClass = convertToClientModel(actualParamClass);
                        methodStr.append(actualParamClass + " " + paramValueNames[k]);
                    }
                    if (paramTypes.length > 0) {
                        methodStr.append(", Handler handler)");
                    } else {
                        methodStr.append("Handler handler)");
                    }
                    methodStr.append(" {\r\n");

                    if (mapMethods.containsKey(methodStr.toString())) {
                        continue;
                    } else {
                        mapMethods.put(methodStr.toString(), "");
                    }

                    stubMethod.append(methodStr);
                    if (paramTypes.length == 0) {
                        stubMethod.append("        Class[] paramTypes = null;\r\n");
                        stubMethod.append("        Object[] params = null;\r\n");
                    } else {
                        stubMethod.append("        Class[] paramTypes = new Class[" + paramTypes.length + "];\r\n");
                        stubMethod.append("        Object[] params = new Object[" + paramTypes.length + "];\r\n");
                        for (int k = 0; k < paramTypeNames.length; k++) {
                            String paramTypeName = paramTypeNames[k];
                            if (paramTypes[k].isPrimitive()) {
                                paramTypeName = getPrimitiveWrapperClass(paramTypes[k]).getName();
                            } else if (paramTypeName.equals(Object.class.getName())) {
                                paramTypeName = convertToClientModel(actualParamTypes[k].getName());
                            }
                            stubMethod.append("        paramTypes[" + k + "] = " + paramTypeName + ".class;\r\n");
                            stubMethod.append("        params[" + k + "] = " + paramValueNames[k] + ";\r\n");
                        }
                    }

                    if (paramTypes != null && paramTypes.length > 0 && paramTypes[paramTypes.length - 1]
                        .equals(File[].class)) {
                        stubMethod.append(
                            "        try{\r\n"
                                + "            invokeUpload(\"" + className + "\", \""
                                + method.getName() + "\", paramTypes, params, handler);\r\n");
                    } else {
                        stubMethod.append(
                            "        try{\r\n"
                                + "            invoke(\"" + className + "\", \""
                                + method.getName() + "\", paramTypes, params, handler);\r\n");
                    }
                    /*if (!VOID.equals(returnTypeName)) {
                        stubMethod.append("            return (" + returnObjectType + ")result;\r\n");
                    }*/
                    stubMethod.append("        } catch(Exception e){\r\n");
                    stubMethod.append("            throw new RuntimeException(e);\r\n");
                    stubMethod.append("        }\r\n");
                    stubMethod.append("    }\r\n\r\n");
                    stubClass.append(stubMethod);
                }
            }
            stubClass.append("}");
            return stubClass.toString();
        } catch (Exception e) {
            //tracer.logInfo(className + " is not found!");
            e.printStackTrace();
        }

        return "";
    }

    private static String convertToClientModel(String paramClassName) {
        if (paramClassName.contains(".service.po.")) {
            return StringUtils.replace(paramClassName, ".service.po.", ".models.");
        } else {
            return paramClassName;
        }
    }

    private static Class getActualClass(Class implClass, String genericName, Class defaultValue) {
        Class returnClass = defaultValue;
        Map<String, Class> genericMap = null;
        Type[] types = implClass.getGenericInterfaces();
        boolean isFound = false;
        for (Type type : types) {
            genericMap = getGenericMap(type);
            if (genericMap.get(genericName) != null) {
                returnClass = genericMap.get(genericName);
                isFound = true;
                break;
            }
        }
        if (!isFound) {
            Type type = implClass.getGenericSuperclass();
            genericMap = getGenericMap(type);
            if (genericMap.get(genericName) != null) {
                returnClass = genericMap.get(genericName);
            }
        }
        return returnClass;
    }

    private static Map<String, Class> getGenericMap(Type type) {
        Map<String, Class> rtnMap = new HashMap<String, Class>();
        if (type instanceof ParameterizedType) {
            Type[] actTypes = ((ParameterizedType) type).getActualTypeArguments();
            Type intfType = ((ParameterizedType) type).getRawType();
            if (intfType instanceof Class) {
                TypeVariable[] tvs = ((Class) intfType).getTypeParameters();
                for (int i = 0; i < tvs.length; i++) {
                    if (tvs[i] instanceof TypeVariable) {
                        rtnMap.put(((TypeVariable) tvs[i]).getName(), (Class) actTypes[i]);
                    }
                }
            }
        }
        return rtnMap;
    }

    private static Class[] getActualParamTypes(Class implClass, Method method, Class[] defaultValue) {
        Class[] returnClassArray = defaultValue;
        if (defaultValue != null) {
            Type[] genericParamTypes = method.getGenericParameterTypes();
            returnClassArray = new Class[defaultValue.length];
            for (int i = 0; i < genericParamTypes.length; i++) {
                if (genericParamTypes[i] instanceof TypeVariable) {
                    returnClassArray[i] =
                        getActualClass(implClass, ((TypeVariable) genericParamTypes[i]).getName(), defaultValue[i]);
                } else {
                    returnClassArray[i] = defaultValue[i];
                }
            }
        }
        return returnClassArray;
    }

    private static Class[] getActualExceptionTypes(Class implClass, Method method, Class[] defaultValue) {
        Class[] returnClassArray = defaultValue;
        if (defaultValue != null) {
            Type[] genericParamTypes = method.getGenericExceptionTypes();
            returnClassArray = new Class[defaultValue.length];
            for (int i = 0; i < genericParamTypes.length; i++) {
                if (genericParamTypes[i] instanceof TypeVariable) {
                    returnClassArray[i] =
                        getActualClass(implClass, ((TypeVariable) genericParamTypes[i]).getName(), defaultValue[i]);
                } else {
                    returnClassArray[i] = (Class) genericParamTypes[i];
                }
            }
        }
        return returnClassArray;
    }

    private static Class getPrimitiveWrapperClass(Class primitiveClass) {
        if (Boolean.TYPE.equals(primitiveClass)) {
            return Boolean.class;
        }
        if (Character.TYPE.equals(primitiveClass)) {
            return Character.class;
        }
        if (Byte.TYPE.equals(primitiveClass)) {
            return Byte.class;
        }
        if (Short.TYPE.equals(primitiveClass)) {
            return Short.class;
        }
        if (Integer.TYPE.equals(primitiveClass)) {
            return Integer.class;
        }
        if (Long.TYPE.equals(primitiveClass)) {
            return Long.class;
        }
        if (Float.TYPE.equals(primitiveClass)) {
            return Float.class;
        }
        if (Double.TYPE.equals(primitiveClass)) {
            return Double.class;
        }

        return null;
    }

    public static String GetElement(String s, HttpServletRequest request) throws ServletException, IOException {
        java.util.Enumeration e = request.getParameterNames();
        String Element = "";
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            String[] values = request.getParameterValues(key);
            if (key.compareTo(s) == 0) {
                for (int i = 0; i < values.length; i++) {
                    if (Element.compareTo("") == 0) Element = Element + values[i];
                    else Element = Element + "," + values[i];
                }
            }
        }
        return Element;
    }

    public static String getCookieValue(HttpServletRequest req, String cookieName) {
        String cookieValue = "";
        try {
            Cookie c[] = req.getCookies();
            for (int n = 0; n < c.length; n++) {
                if (c[n].getName().equals(cookieName)) {
                    cookieValue = c[n].getValue();
                }
            }
        } catch (Exception ex) {
        }
        return cookieValue;
    }
}