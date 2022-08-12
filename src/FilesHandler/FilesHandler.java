package FilesHandler;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class FilesHandler {
    public FilesHandler(String outputFileName, LinkedList<String> inputFileNames) throws IOException {
        outputFile = new File(outputFileName);
        if (outputFile.exists() == false) {
            outputFile.createNewFile();
        } else if (outputFile.exists() == true && outputFile.canWrite() == false) {
            throw new IOException("Can not write if file: " + outputFile);
        }

        inputFiles = new LinkedList<>();
        for (String fileNameStr : inputFileNames) {
            File file = new File(fileNameStr);
            if (file.canRead())
                inputFiles.add(file);
        }
        if (inputFiles.size() == 0)
            throw new IOException("All input files are unable to read");
    }

    public File getOutputFile() {
        return outputFile;
    }
    
    public LinkedList<File> getInputFiles() {
        return inputFiles;
    }

    private File outputFile;
    private LinkedList<File> inputFiles;

}
