import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashSet;
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
            sortType = argsData.getSortType();
            dataType = argsData.getDataType();
            System.out.println("SortType: " + sortType);
            System.out.println("DataType: " + dataType);
            mergeSortFiles();

        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }

    private static ArrayList<File> inputFiles;
    private static File outputFile;
    
    private static SortType sortType;
    private static DataType dataType;


    private static void mergeSortFiles() throws Exception {
        FileWriter outputWriter = new FileWriter(outputFile);
        Scanner[] inputFileScanners = new Scanner[inputFiles.size()];
        for (int i = 0; i < inputFiles.size(); i++)
            inputFileScanners[i] = new Scanner(inputFiles.get(i));
        
        int scannerIndexWithMinValue = 0;
        HashSet<Integer> emptyFilesIndexes = new HashSet<>();
        String[] arrWithLastScannedValues = new String[inputFileScanners.length];
        int finalValueToWrite = 0;
        String finalStringToWrite = null;
        if (sortType == SortType.ASCENDING)
            finalValueToWrite = Integer.MAX_VALUE;
        else
            finalValueToWrite = Integer.MIN_VALUE;
        while (emptyFilesIndexes.size() != inputFileScanners.length) {
            for (int i = 0; i < inputFileScanners.length; i++) {
                if (arrWithLastScannedValues[i] == null) {
                    if (inputFileScanners[i].hasNext() == false) {
                        emptyFilesIndexes.add(i);
                        continue;
                    }
                    String line = inputFileScanners[i].nextLine();
                    arrWithLastScannedValues[i] = line;
                }
                int currentValue = getValueDependsOnDataType(arrWithLastScannedValues[i]);
                if (compareDependsOnSortType(currentValue, finalValueToWrite)) {
                    finalValueToWrite = currentValue;
                    scannerIndexWithMinValue = i;
                    if (dataType == DataType.STRINGS)
                        finalStringToWrite = arrWithLastScannedValues[i];
                }
            }
            if (emptyFilesIndexes.size() != inputFileScanners.length) {
                if (dataType == DataType.STRINGS && finalStringToWrite != null)
                    outputWriter.write(finalStringToWrite + "\n");
                else
                    outputWriter.write(finalValueToWrite + "\n");
                arrWithLastScannedValues[scannerIndexWithMinValue] = null;
                if (sortType == SortType.ASCENDING)
                    finalValueToWrite = Integer.MAX_VALUE;
                else
                    finalValueToWrite = Integer.MIN_VALUE;
            }
        }
        for (Scanner scanner : inputFileScanners)
            scanner.close();
        outputWriter.close();
    }

    private static int getValueDependsOnDataType(String line) {
        if (dataType == DataType.INTEGERS)
            return Integer.valueOf(line);
        else
            return line.length();
    }

    private static boolean compareDependsOnSortType(int currValue, int finalValueToWrite) {
        if (sortType == SortType.ASCENDING)
            return currValue < finalValueToWrite;
        else
            return currValue > finalValueToWrite;
    }

}
