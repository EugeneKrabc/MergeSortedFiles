import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import helperclasses.*;
import helperclasses.CmdArguments.*;

public class Sort {
    public static void main(String[] args) {
        try {
            CmdArguments argsData = new CmdArguments(args);
            FilesHandler files = new FilesHandler(argsData.getOutputFileName(), argsData.getInputFileNames());
            mergeSortFiles(argsData, files);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static Scanner[] inputFileScanners;
    private static String[] arrWithLastScannedValues;
    private static int scannerIndexWithMinValue;
    private static int finalValueToWrite;
    private static String finalStringToWrite;
    private static boolean wasAtLeastOneWrite;
    private static int lastWrittenValue;
    private static FileWriter outputWriter;
    private static SortType sortType;
    private static DataType dataType;
    private static final HashSet<Integer> emptyFilesIndexes = new HashSet<>();

    private static void mergeSortFiles(CmdArguments argsData, FilesHandler files) throws IOException {
        try {
            preparetionForSorting(argsData, files);
            mainSortingCycle();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            for (Scanner scanner : inputFileScanners)
                scanner.close();
            outputWriter.close();
        }
    }

    private static void preparetionForSorting(CmdArguments argsData, FilesHandler files) throws Exception {
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

    private static void mainSortingCycle() throws Exception {
        resetFinalValueToWrite();
        while (emptyFilesIndexes.size() != inputFileScanners.length) {
            for (int i = 0; i < inputFileScanners.length; i++) {
                if (arrWithLastScannedValues[i] == null) {
                    if (!inputFileScanners[i].hasNext()) {
                        emptyFilesIndexes.add(i);
                        continue;
                    }
                    arrWithLastScannedValues[i] = inputFileScanners[i].nextLine();
                    if (arrWithLastScannedValues[i].contains(" ")) {
                        throw new Exception("Spaces in input files are not allowed");
                    }
                }
                int currentValue = getValueDependsOnDataType(arrWithLastScannedValues[i]);
                if (compareDependsOnSortType(currentValue, finalValueToWrite)) {
                    finalValueToWrite = currentValue;
                    scannerIndexWithMinValue = i;
                    if (dataType == DataType.STRINGS) {
                        finalStringToWrite = arrWithLastScannedValues[i];
                    }
                }
            }
            if (emptyFilesIndexes.size() != inputFileScanners.length) {
                if (checkLastWrittenValue(finalValueToWrite)) {
                    if (dataType == DataType.STRINGS && finalStringToWrite != null) {
                        outputWriter.write(finalStringToWrite + "\n");
                    } else {
                        outputWriter.write(finalValueToWrite + "\n");
                    }
                    lastWrittenValue = finalValueToWrite;
                    wasAtLeastOneWrite = true;
                }
                arrWithLastScannedValues[scannerIndexWithMinValue] = null;
                resetFinalValueToWrite();
            }
        }
    }

    private static int getValueDependsOnDataType(String line) throws Exception {
        if (dataType == DataType.INTEGERS) {
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                throw new Exception("Can not convert string '" + line + "' to int");
            }
        } else {
            return line.length();
        }
    }

    private static boolean compareDependsOnSortType(int currValue, int finalValueToWrite) {
        if (sortType == SortType.ASCENDING) {
            return currValue < finalValueToWrite;
        } else {
            return currValue > finalValueToWrite;
        }
    }

    private static void resetFinalValueToWrite() {
        if (sortType == SortType.ASCENDING) {
            finalValueToWrite = Integer.MAX_VALUE;
        } else {
            finalValueToWrite = Integer.MIN_VALUE;
        }
    }

    // This check allows handling case when input files aren't sorted
    private static boolean checkLastWrittenValue(int currentWriteValue) {
        if (!wasAtLeastOneWrite) {
            return true;
        }

        if ((sortType == SortType.ASCENDING && currentWriteValue < lastWrittenValue)
        || sortType == SortType.DESCENDING && currentWriteValue > lastWrittenValue) {
            return false;
        } else {
            return true;
        }
    }
}
