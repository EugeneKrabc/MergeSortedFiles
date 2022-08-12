import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

import CmdArgumentsParser.CmdArgumentsParser;
import CmdArgumentsParser.CmdArgumentsParser.DataType;
import CmdArgumentsParser.CmdArgumentsParser.SortType;
import FilesHandler.FilesHandler;

public class Main {
    public static void main(String[] args) {
        try {
            CmdArgumentsParser argsData = new CmdArgumentsParser(args);
            System.out.println("Output file name: " + argsData.getOutputFileName());
            System.out.println("Input file names: " + argsData.getInputFileNames());
            
            FilesHandler files = new FilesHandler(argsData.getOutputFileName(), argsData.getInputFileNames());
            inputFiles = files.getInputFiles();
            outputFile = files.getOutputFile();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static File[] inputFiles;
    private static File outputFile;
    private static SortType sortType;
    private static DataType dataType;

    private static void mergeSortFiles() throws Exception{
        FileWriter writer = new FileWriter(outputFile);
        Scanner[] scanners = new Scanner[inputFiles.length];
        for (int i = 0; i < inputFiles.length; i++)
            scanners[i] = new Scanner(inputFiles[i]);
        
    }

}
