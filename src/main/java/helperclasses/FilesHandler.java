package helperclasses;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.ArrayList;

public class FilesHandler {
    public FilesHandler(String outputFileName, LinkedList<String> inputFileNames) throws IOException {
        convertFileNamesIntoFileType(outputFileName, inputFileNames);
    }

    public File getOutputFile() {
        return outputFile;
    }
    public ArrayList<File> getInputFiles() {
        return inputFiles;
    }

    private File outputFile;
    private ArrayList<File> inputFiles;

    private void convertFileNamesIntoFileType(String outputFileName,
                                              LinkedList<String> inputFileNames) throws IOException {
        outputFile = new File(outputFileName);
        if (!outputFile.exists()) {
            outputFile.createNewFile();
        } else if (outputFile.exists() && !outputFile.canWrite()) {
            throw new IOException("Can not write in file: " + outputFile);
        }

        inputFiles = new ArrayList<>();
        for (String fileNameStr : inputFileNames) {
            File file = new File(fileNameStr);
            if (file.canRead())
                inputFiles.add(file);
        }

        if (inputFiles.size() == 0) {
            throw new IOException("All input files are unable to read");
        }
    }
}
