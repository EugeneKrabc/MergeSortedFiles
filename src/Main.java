import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;

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

    private static LinkedList<File> inputFiles;
    private static File outputFile;
    private static SortType sortType;
    private static DataType dataType;

    private static void mergeSortFiles() throws Exception{
        FileWriter writer = new FileWriter(outputFile);
        LinkedList<BufferedReader> fileReaders = new LinkedList<>();
        for (File file : inputFiles)
            fileReaders.add(new BufferedReader(new FileReader(file)));

        int maxValue = Integer.MIN_VALUE;
        BufferedReader fileWithMaxValue = fileReaders.peek();
        while (fileReaders.size() != 0) {
            for (BufferedReader reader : fileReaders) {
                reader.mark(1);
                String buff = reader.readLine();
                if (buff == null)
                    fileReaders.remove(reader);
                
                int currentValue = Integer.valueOf(buff);
                if (currentValue > maxValue) {
                    maxValue = currentValue;
                    fileWithMaxValue = reader;
                }
                reader.reset();  // Return 1 line back
            }
            writer.write(String.valueOf(maxValue) + "\n");
            fileWithMaxValue.readLine();  // Move to next line in file where we find maxValue
        }

    }

}
