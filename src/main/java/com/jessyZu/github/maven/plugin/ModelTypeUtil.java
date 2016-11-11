package com.jessyZu.github.maven.plugin;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhoulai on 16/10/21.
 */
public class ModelTypeUtil {

    public static String IOS_PREFIX = "";
    public static final String JAVA_boolean = "boolean";
    public static final String JAVA_char = "char";
    public static final String JAVA_double = "double";
    public static final String JAVA_float = "float";
    public static final String JAVA_int = "int";
    public static final String JAVA_long = "long";
    public static final String JAVA_short = "short";
    public static final String JAVA_Boolean = "Boolean";
    public static final String JAVA_Character = "Character";
    public static final String JAVA_Double = "Double";
    public static final String JAVA_Float = "Float";
    public static final String JAVA_Integer = "Integer";
    public static final String JAVA_Long = "Long";
    public static final String JAVA_Short = "Short";
    public static final String JAVA_String = "String";
    public static final String JAVA_Object = "Object";
    public static final String JAVA_List = "List";
    public static final String JAVA_Map = "Map";
    public static final String JAVA_Set = "Set";
    public static final String JAVA_Date = "Date";
    public static final String JAVA_void = "void";

    public static final String IOS_boolean = "BOOL";
    public static final String IOS_char = "char";
    public static final String IOS_double = "double";
    public static final String IOS_float = "float";
    public static final String IOS_int = "int";
    public static final String IOS_long = "long long";
    public static final String IOS_short = "short";
    public static final String IOS_Boolean = "BOOL";
    public static final String IOS_Character = "NSString";
    public static final String IOS_Double = "NSNumber";
    public static final String IOS_Float = "NSNumber";
    public static final String IOS_Integer = "NSNumber";
    public static final String IOS_Long = "NSNumber";
    public static final String IOS_Short = "NSNumber";
    public static final String IOS_String = "NSString";
    public static final String IOS_Object = "NSObject";
    public static final String IOS_List = "NSArray";
    public static final String IOS_Map = "NSDictionary";
    public static final String IOS_Set = "NSSet";
    public static final String IOS_Date = "NSDate";
    public static final String IOS_void = "void";

    private static final List<String> types = Arrays.asList(new String[]{
            JAVA_boolean, JAVA_char,
            JAVA_double, JAVA_float, JAVA_int, JAVA_long, JAVA_short, JAVA_Boolean,
            JAVA_Character, JAVA_Double, JAVA_Float, JAVA_Integer, JAVA_Long, JAVA_Short,
            JAVA_String, JAVA_Object, JAVA_List, JAVA_Map, JAVA_Set, JAVA_Date, JAVA_void,
    });


    public static String getIOSType(String javaType) {
        String name = javaType;
        if (name == null) {
            return "null";
        }
        if (name.equals(JAVA_void))
            return IOS_void;
        if (name.equals(JAVA_boolean))
            return IOS_boolean;
        if (name.equals(JAVA_Boolean))
            return IOS_Boolean;
        if (name.equals(JAVA_char))
            return IOS_char;
        if (name.equals(JAVA_Character))
            return IOS_Character;
        if (name.equals(JAVA_double))
            return IOS_double;
        if (name.equals(JAVA_Double))
            return IOS_Double;
        if (name.equals(JAVA_float))
            return IOS_float;
        if (name.equals(JAVA_Float))
            return IOS_Float;
        if (name.equals(JAVA_int))
            return IOS_int;
        if (name.equals(JAVA_Integer))
            return IOS_Integer;
        if (name.equals(JAVA_long))
            return IOS_long;
        if (name.equals(JAVA_Long))
            return IOS_Long;
        if (name.equals(JAVA_short))
            return IOS_short;
        if (name.equals(JAVA_Short))
            return IOS_Short;
        if (name.equals(JAVA_String))
            return IOS_String;
        if (name.equals(JAVA_Object))
            return IOS_Object;
        if (name.equals(JAVA_List))
            return IOS_List;
        if (name.equals(JAVA_Map))
            return IOS_Map;
        if (name.equals(JAVA_Set))
            return IOS_Set;
        if (name.equals(JAVA_Date))
            return IOS_Date;
        return IOS_PREFIX + name;
    }
}
