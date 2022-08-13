import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
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
    private final static int MAX_LINE_SIZE = 1;

    private static void mergeSortFiles() throws Exception {
        FileWriter outputWriter = new FileWriter(outputFile);
        Scanner[] inputFileScanners = new Scanner[inputFiles.size()];
        for (int i = 0; i < inputFiles.size(); i++)
            inputFileScanners[i] = new Scanner(inputFiles.get(i));
        
        int minValue = Integer.MAX_VALUE;
        int scannerIndexWithMinValue = 0;
        HashSet<Integer> emptyFilesIndexes = new HashSet<>();
        String[] arrWithLastScannedValues = new String[inputFileScanners.length];
        while (emptyFilesIndexes.size() != inputFileScanners.length) {
            for (int i = 0; i < inputFileScanners.length; i++) {
                if (inputFileScanners[i].hasNext() == false) {
                    emptyFilesIndexes.add(i);
                    continue;
                }

                if (arrWithLastScannedValues[i] == null) {
                    String line = inputFileScanners[i].nextLine();
                    arrWithLastScannedValues[i] = line;
                }
                int currentValue = Integer.valueOf(arrWithLastScannedValues[i]);
                if (currentValue < minValue) {
                    minValue = currentValue;
                    scannerIndexWithMinValue = i;
                }
            }
            if (emptyFilesIndexes.size() != inputFileScanners.length) {
                outputWriter.write(minValue + "\n");
                System.out.println("Writing to out: " + minValue);
                arrWithLastScannedValues[scannerIndexWithMinValue] = null;
                minValue = Integer.MAX_VALUE;
            }
        }
        for (Scanner scanner : inputFileScanners)
            scanner.close();
        outputWriter.close();
    }

}



//     LinkedList<Scanner> inputFilesScanner = new LinkedList<>();
//     for (File inputFile : inputFiles)
//         inputFilesScanner.add(new Scanner(inputFile));
    
//     int minValue = Integer.MAX_VALUE;
//     int scannerWithMinValue = 0;
//     int emptyScannerIndex = -1;
//     String[] arrayWithLastScannedValues = new String[inputFilesScanner.size()];
//     while (inputFilesScanner.size() != 0) {
//         for (int i = 0; i < inputFilesScanner.size(); i++) {
//             if (inputFilesScanner.get(i).hasNext() == false) {
//                 emptyScannerIndex = i;
//                 continue;
//             }
//             String line = inputFilesScanner.get(i).findInLine(".*");
//             if (line != null)
//                 arrayWithLastScannedValues[i]
//             System.out.println("line = " + line);
//             int currentValue = Integer.valueOf(line);
//             if (currentValue < minValue) {
//                 minValue = currentValue;
//                 scannerWithMinValue = i;
//             }
//         }
//         if (inputFilesScanner.size() != 0) {
//             outputWriter.write(minValue + "\n");
//             System.out.println("Writing to out: " + minValue);
//             Scanner minValScanner = inputFilesScanner.get(scannerWithMinValue);
//             if (minValScanner.hasNext())
//                 minValScanner.nextLine();
//             minValue = Integer.MAX_VALUE;
//         }
//         if (emptyScannerIndex != -1) {
//             Scanner emptyScanner = inputFilesScanner.get(emptyScannerIndex);
//             emptyScanner.close();
//             emptyScanner.remove();
//             emptyScannerIndex = -1;
//         }
//     }
//     outputWriter.close();
//