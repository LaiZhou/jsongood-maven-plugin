package com.jessyZu.github.maven.plugin;

import cn.org.rapid_framework.generator.Generator;
import cn.org.rapid_framework.generator.Generator.GeneratorModel;
import static cn.org.rapid_framework.generator.GeneratorConstants.GG_IS_OVERRIDE;
import cn.org.rapid_framework.generator.GeneratorFacade;
import cn.org.rapid_framework.generator.GeneratorFacade.GeneratorModelUtils;
import cn.org.rapid_framework.generator.GeneratorProperties;
import cn.org.rapid_framework.generator.util.GeneratorException;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhoulai on 16/10/18.
 *
 * @goal genApi
 */
public class GenApiMojo extends BaseMojo {

    /**
     * @parameter expression="${className}"
     */
    private String className;

    /**
     * @parameter expression="${iosPrefix}"
     */
    private String iosPrefix;


    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getIosPrefix() {
        return iosPrefix;
    }

    public void setIosPrefix(String iosPrefix) {
        this.iosPrefix = iosPrefix;
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {

        Thread currentThread = Thread.currentThread();
        ClassLoader oldClassLoader = currentThread.getContextClassLoader();

        try {
            currentThread.setContextClassLoader(getClassLoader());
            System.out.println("parameter:");
            System.out.println("className=" + className);
            System.out.println("iosPrefix=" + iosPrefix);


            Class clazz = currentThread.getContextClassLoader().loadClass(className);
            System.out.println("class to genApi:" + clazz);
            List<Class> cs = realizeAllClassByTarget(clazz);

            GeneratorFacade g = new GeneratorFacade();
            Generator gg = g.getGenerator();

            gg.setTemplateRootDirs("classpath:template");
            String outputPath = project.getBuild().getDirectory() + File.separator + "gen";
            File outputFile = new File(outputPath);
            if (outputFile.exists()) {
                FileUtils.deleteDirectory(outputFile);
            }
            gg.setOutputEncoding("UTF-8");
            gg.setSourceEncoding("UTF-8");
            gg.setOutRootDir(outputPath);
            System.out.println("directory to gen :" + g.getGenerator().getOutRootDir() + " ... ");

            //新增model变量
            GeneratorProperties.setProperty(GG_IS_OVERRIDE,"true");
            ModelTypeUtil.IOS_PREFIX=iosPrefix;
            for (Class c : cs) {
                System.out.println("class to gen :" + c + " ... ");
                GeneratorModel m = GeneratorModelUtils.newGeneratorModel("clazz", new ModelClass(c));

                m.templateModel.put("iosPrefix",iosPrefix);
                m.filePathModel.put("iosPrefix",iosPrefix);

                try {

                    gg.generateBy(m.templateModel, m.filePathModel);
                } catch (GeneratorException ge) {
                    printExceptionsSumary(ge.getMessage(), gg.getOutRootDir(),
                            ge.getExceptions());
                    throw ge;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            currentThread.setContextClassLoader(oldClassLoader);
        }

    }

    public static List<Class> realizeAllClassByTarget(Class clazz) {
        List<Class> cs = new ArrayList<>();
        Class c = clazz;
        cs.add(c);
        while (c.getSuperclass() != null && !c.getSuperclass().equals(Object.class)) {
            c = clazz.getSuperclass();
            cs.add(c);
        }

        return cs;
    }

    private static void printExceptionsSumary(String msg, String outRoot,
                                              List<Exception> exceptions)
            throws FileNotFoundException {
        File errorFile = new File(outRoot, "generator_error.log");
        if (exceptions != null && exceptions.size() > 0) {
            System.err.println("[Generate Error Summary] : " + msg);
            errorFile.getParentFile().mkdirs();
            PrintStream output = new PrintStream(new FileOutputStream(errorFile));
            for (int i = 0; i < exceptions.size(); i++) {
                Exception e = exceptions.get(i);
                System.err.println("[GENERATE ERROR]:" + e);
                if (i == 0)
                    e.printStackTrace();
                e.printStackTrace(output);
            }
            output.close();
            System.err
                    .println("***************************************************************");
            System.err.println("* " + "* 输出目录已经生成generator_error.log用于查看错误 ");
            System.err
                    .println("***************************************************************");
        }
    }

}
