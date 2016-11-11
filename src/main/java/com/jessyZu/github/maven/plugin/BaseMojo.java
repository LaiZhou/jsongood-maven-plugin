
package com.jessyZu.github.maven.plugin;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Collection;


public abstract class BaseMojo extends AbstractMojo {

    /**
     * <i>Maven Internal</i>: Project to interact with.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     * @noinspection UnusedDeclaration
     */
    protected MavenProject project;

    /**
     * 改变plugin classloader，使其可以加载当前工程的classpath以及dependency
     *
     * @return ClassLoader
     * @noinspection unchecked
     */
    protected ClassLoader getClassLoader() {
        try {
            Collection<URL> urls = new ArrayList<URL>();
            for (String elements : (Collection<String>) project.getCompileClasspathElements()) {
                urls.add(new File(elements).toURI().toURL());
            }
            for (Artifact artifact : (Collection<Artifact>) project.getArtifacts()) {
                urls.add(artifact.getFile().toURI().toURL());
            }
            return new URLClassLoader((urls.toArray(new URL[urls.size()])), this.getClass()
                    .getClassLoader());
        } catch (Exception e) {
            e.printStackTrace();
            return this.getClass().getClassLoader();
        }
    }

    public MavenProject getProject() {
        return project;
    }

    protected String[] parseStringArray(String string) {
        return MojoUtil.parseStringArray(string);
    }

    protected boolean isOsWindows() {
        return MojoUtil.isOsWindows();
    }

    public static class MojoUtil {
        public static String[] parseStringArray(String string) {
            return string.split(",");
        }

        public static boolean isOsWindows() {
            return System.getProperty("os.name").toUpperCase().contains("Windows".toUpperCase());
        }

        public static String toConsistentFilePath(String path) {
            return new File(path).getAbsolutePath();
        }

        public static void openFileIfOnWindows() throws IOException {
            if (isOsWindows()) {
                Runtime.getRuntime().exec(
                        "cmd.exe /c start "
                                + MojoUtil.toConsistentFilePath("."));
            }
        }
    }
}
