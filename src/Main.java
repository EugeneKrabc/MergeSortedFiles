import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
            FilesHandler files = new FilesHandler(argsData.getOutputFileName(), argsData.getInputFileNames());
            preparetionForSorting(argsData, files);
            mergeSortFiles();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Scanner[] inputFileScanners;
    private static String[] arrWithLastScannedValues;
    private static int scannerIndexWithMinValue = 0;
    private static int finalValueToWrite = 0;
    private static String finalStringToWrite = null;

    private static FileWriter outputWriter;
    
    private static SortType sortType;
    private static DataType dataType;

    private static HashSet<Integer> emptyFilesIndexes = new HashSet<>();

    private static void preparetionForSorting(CmdArgumentsParser argsData, FilesHandler files) throws IOException {
        ArrayList<File> inputFiles = files.getInputFiles();
        File outputFile = files.getOutputFile();
        sortType = argsData.getSortType();
        dataType = argsData.getDataType();

        outputWriter = new FileWriter(outputFile);
        inputFileScanners = new Scanner[inputFiles.size()];
        for (int i = 0; i < inputFiles.size(); i++)
            inputFileScanners[i] = new Scanner(inputFiles.get(i));
        
        arrWithLastScannedValues = new String[inputFileScanners.length];
    }

    private static void mergeSortFiles() throws IOException {
        try {
            mainSortingCycle();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            for (Scanner scanner : inputFileScanners)
                scanner.close();
            outputWriter.close();
        }
    }

    private static void mainSortingCycle() throws IOException {
        resetFinalValueToWrite();
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
                resetFinalValueToWrite();
            }
        }
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

    private static void resetFinalValueToWrite() {
        if (sortType == SortType.ASCENDING)
            finalValueToWrite = Integer.MAX_VALUE;
        else
            finalValueToWrite = Integer.MIN_VALUE;
    }

}
