package com.stevenmassaro;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "replace", defaultPhase = LifecyclePhase.COMPILE)
public class ReplacerMojo extends AbstractMojo {

    /**
     * The file with placeholders that should be replaced.
     */
    @Parameter
    protected String file;

    /**
     * The filters to apply to the specified file. The filters are in the format of:
     * <code>
     *    key = placeholder text in the specified file
     *    value = the path to the file whose contents will be inserted in place of the placeholder text
     * </code>
     */
    @Parameter
    protected Map<String, String> filters;

    public void execute() throws MojoFailureException {
        getLog().info("Replacing contents of '" + file + "' using '" + filters.size() + "' filter(s).");

        try {
            File toModify = new File(file);
            if (!toModify.exists()) {
                throw new MojoFailureException("File '" + file + "' does not exist.");
            }

            String toModifyContents = FileUtils.readFileToString(toModify, StandardCharsets.UTF_8);
            for (Map.Entry<String, String> filter : filters.entrySet()) {
                String key = filter.getKey();
                String replacementFilePath = filter.getValue();

                getLog().debug("Replacing key '" + key + "' using contents from file '" + replacementFilePath + "'");

                File replacementFile = new File(replacementFilePath);
                if (!replacementFile.exists()) {
                    throw new MojoFailureException("File '" + replacementFilePath + "' does not exist.");
                }

                String replacementFileContents = FileUtils.readFileToString(replacementFile, StandardCharsets.UTF_8);

                toModifyContents = toModifyContents.replace(key, replacementFileContents);
            }

            FileUtils.write(toModify, toModifyContents, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new MojoFailureException("Failed to replace contents of file", e);
        }

    }
}
