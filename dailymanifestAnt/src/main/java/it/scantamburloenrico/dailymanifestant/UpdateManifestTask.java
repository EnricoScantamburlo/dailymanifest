package it.scantamburloenrico.dailymanifestant;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 *
 * @author Enrico Scantamburlo <scantamburlo at streamsim.com>
 */
public class UpdateManifestTask extends Task {

    private final static String VERSION_PREFIX = "OpenIDE-Module-Specification-Version:";

    @Override
    public void execute() throws BuildException {

        try {
            File manifestFile = new File(getProject().getBaseDir(), "manifest.mf");
            assert manifestFile.isFile() : manifestFile.getAbsolutePath();

            File baseDir = getProject().getBaseDir();
            File manifest = new File(baseDir, "manifest.mf");

            AtomicBoolean found = new AtomicBoolean(false);

            StringBuilder sb = new StringBuilder();

            Files.lines(manifest.toPath()).forEach((line) -> {
                if (line.startsWith(VERSION_PREFIX)) {
                    getProject().log("Updating " + manifest.toString());
                    found.set(true);
                    String version = line.substring(VERSION_PREFIX.length()).trim();

                    sb.append(VERSION_PREFIX).append(' ')
                            //.append(increaseManifest(version))
                            //.append(updateManifest(version, LocalDateTime.now()))
                            .append(increaseManifest(version))
                            .append('\n');
                } else {
                    sb.append(line).append('\n');
                }

            });

            if (found.get()) {
                try (Writer wr = Files.newBufferedWriter(manifest.toPath())) {
                    wr.write(sb.toString());
                }
            } else {
                // cannot find the version inside the manifest.mf
                // looking for properties
                File propFile = baseDir.toPath().resolve("nbproject").resolve("project.properties").toFile();
                Properties props = new Properties();
                try (Reader reader = new FileReader(propFile)) {
                    props.load(reader);
                }

                String specVersionBase = props.getProperty(PROP_SPEC_VERSION_BASE);
                if (specVersionBase != null) {
                    String newSpecVersionBase = increaseManifest(specVersionBase.trim());
                    props.setProperty(PROP_SPEC_VERSION_BASE, newSpecVersionBase);

                    try (Writer reader = new FileWriter(propFile)) {
                        props.store(reader, null);
                    }
                }

            }

        } catch (IOException ioe) {
            throw new BuildException(ioe);
        }

    }
    private static final String PROP_SPEC_VERSION_BASE = "spec.version.base";

    private static final DateTimeFormatter DATE_FORMATTER2 = DateTimeFormatter.ofPattern("yyyy.MMdd.HHmmss", Locale.US);

    // Not very good looking
    protected String updateManifest2(String original, LocalDateTime now) {
        return DATE_FORMATTER2.format(now);
    }

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.US);

    // Does not work because it generates numbers too big
    // for an Integer
    protected String updateManifest(String original, LocalDateTime now) {
        String[] parts = original.split("\\.");
        if (parts.length == 3) {

            String thirdPart = DATE_FORMATTER.format(now);
            return parts[0] + '.' + parts[1] + '.' + thirdPart;
        } else if (parts.length == 2) {

            String thirdPart = DATE_FORMATTER.format(now);
            return parts[0] + '.' + thirdPart;
        } else {
            final Project project = getProject();
            if (project != null) {// for tests
                project.log("Invalid version format: " + original, Project.MSG_WARN);
            }
        }
        return null;
    }

    protected String increaseManifest(String original) {
        String[] parts = original.split("\\.");

        String lastPart = parts[parts.length - 1];
        int lastInteger = Integer.parseInt(lastPart);
        // I increase the version number
        lastInteger++;
        parts[parts.length - 1] = String.valueOf(lastInteger);

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            sb.append(part);
            if (i != parts.length - 1) {
                sb.append('.');
            }
        }

        return sb.toString();
    }

}
