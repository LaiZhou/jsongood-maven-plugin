package com.jessyZu.github.maven.plugin;

import cn.org.rapid_framework.generator.provider.java.model.JavaClass;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhoulai on 16/10/21.
 */
public class ModelClass extends JavaClass {


    public ModelClass(Class clazz) {
        super(clazz);
    }


    public List<IOSField> getIOSFields() {
        List<IOSField> ls = new ArrayList<>();
        for (Field field : getClazz().getDeclaredFields()) {
            ls.add(new IOSField(field, this));
        }
        return ls;
    }

    public String getIOSClassName() {
        return ModelTypeUtil.getIOSType(getSimpleJavaType());
    }

    public ModelClass getIOSSuperclass() {
        return new ModelClass(getClazz().getSuperclass());
    }

    public Set<ModelClass> getIOSImportClasses() {
        Set<ModelClass> st = new HashSet<>();
        for (JavaClass javaClass : getImportClasses()) {
            if (!javaClass.getPackageName().startsWith("java") && !javaClass.getClassName().equals(getClassName())) {
                st.add(new ModelClass(javaClass.getClazz()));
            }
        }

        return st;
    }

    public String getImportIOSSuperClassName() {
        if (getIOSSuperclass().getIOSClassName().contains("NS")){
            return null;
        }else{
            return getIOSSuperclass().getIOSClassName();
        }
    }
}
