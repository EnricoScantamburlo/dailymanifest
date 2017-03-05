package it.scantamburloenrico.dailymanifestant;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.Task;

/**
 *
 * @author Enrico Scantamburlo <scantamburlo at streamsim.com>
 */
public class UpdateManifestTask extends Task {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMddHHmmss", Locale.US);

    @Override
    public void execute() throws BuildException {

        File manifestFile = new File(getProject().getBaseDir(), "manifest.mf");
        assert manifestFile.isFile() : manifestFile.getAbsolutePath();
        LocalDateTime now = LocalDateTime.now();
    }

    protected String updateManifest(String original, LocalDateTime now) {
        String[] parts = original.split("\\.");
        if (parts.length == 3) {

            String thirdPart = DATE_FORMATTER.format(now);
            return parts[0] + '.' + parts[1] + '.' + thirdPart;
        } else {
            final Project project = getProject();
            if (project != null) {// for tests
                project.log("Invalid version format: " + original, Project.MSG_WARN);
            }
        }
        return null;
    }

}
