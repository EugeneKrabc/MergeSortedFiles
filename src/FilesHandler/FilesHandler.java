package FilesHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FilesHandler {
    public FilesHandler(String outputFileName, ArrayList<String> inputFileNames) throws IOException {
        outputFile = new File(outputFileName);
        if (outputFile.exists() == false) {
            outputFile.createNewFile();
        } else if (outputFile.exists() == true && outputFile.canWrite() == false) {
            throw new IOException("Can not write if file: " + outputFile);
        }

        inputFiles = new File[inputFileNames.size()];
        for (int i = 0; i < inputFileNames.size(); i++) {
            inputFiles[i] = new File(inputFileNames.get(i));
            if (inputFiles[i].canRead() == false)
                throw new IOException("Can't open file for reading: " + inputFiles[i]);
        }
    }

    public File getOutputFile() {
        return outputFile;
    }
    
    public File[] getInputFiles() {
        return inputFiles;
    }

    private File outputFile;
    private File[] inputFiles;

}
