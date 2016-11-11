package com.jessyZu.github.maven.plugin;

import cn.org.rapid_framework.generator.provider.java.model.JavaClass;
import cn.org.rapid_framework.generator.provider.java.model.JavaField;

import java.lang.reflect.Field;

/**
 * Created by zhoulai on 16/10/21.
 */
public class IOSField extends JavaField {
    public IOSField(Field field, JavaClass clazz) {
        super(field, clazz);
    }

    public String getIOSType() {
        return ModelTypeUtil.getIOSType(this.getType().getSimpleJavaType());
    }


    public boolean isPrimitive4IOS() {
        String name = getIOSType();
        if ((name.equals("void")) ||
                (name.equals("BOOL")) ||
                (name.equals("unsigned char")) ||
                (name.equals("char")) ||
                (name.equals("double")) ||
                (name.equals("float")) ||
                (name.equals("int")) ||
                (name.equals("long long")) ||
                (name.equals("short"))) {
            return true;
        }
        return false;
    }
}