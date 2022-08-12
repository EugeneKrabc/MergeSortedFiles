import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
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

    private static LinkedList<File> inputFiles;
    private static File outputFile;
    private static SortType sortType;
    private static DataType dataType;
    private final static int MAX_LINE_SIZE = 1;

    private static void mergeSortFiles() throws Exception{
        FileWriter outputWriter = new FileWriter(outputFile);
        LinkedList<Scanner> inputFilesScanner = new LinkedList<>();
        for (File inputFile : inputFiles)
            inputFilesScanner.add(new Scanner(inputFile));
        
        int maxValue = Integer.MIN_VALUE;
        Scanner scannerWithMaxValue = inputFilesScanner.peek();
        while (inputFilesScanner.size() != 0) {
            for (Scanner scanner : inputFilesScanner) {
                if (scanner.hasNext() == false) {
                    scanner.close();
                    inputFilesScanner.remove(scanner);
                    continue;
                }
                String line = scanner.findInLine(".*");
                System.out.println("line = " + line);
                int currentValue = Integer.valueOf(line);
                if (currentValue > maxValue) {
                    maxValue = currentValue;
                    scannerWithMaxValue = scanner;
                }
            }
            if (inputFilesScanner.size() != 0) {
                outputWriter.write(maxValue + "\n");
                if (scannerWithMaxValue.hasNext())
                    scannerWithMaxValue.nextLine();
                maxValue = Integer.MIN_VALUE;
            }
        }
        outputWriter.close();
    }

}



// LinkedList<BufferedReader> fileReaders = new LinkedList<>();
// for (File file : inputFiles)
//     fileReaders.add(new BufferedReader(new FileReader(file)));

// int maxValue = Integer.MIN_VALUE;
// BufferedReader fileWithMaxValue = fileReaders.peek();
// while (fileReaders.size() != 0) {
//     for (BufferedReader reader : fileReaders) {
//         reader.mark(MAX_LINE_SIZE);
//         String buff = reader.readLine();
//         System.out.println("Buff = " + buff);
//         if (buff == null || buff.equals("\n")) {
//             reader.close();
//             fileReaders.remove(reader);
//             continue;
//         }

//         int currentValue = Integer.valueOf(buff);
//         if (currentValue > maxValue) {
//             maxValue = currentValue;
//             fileWithMaxValue = reader;
//         }

//     }
//     writer.write(String.valueOf(maxValue) + "\n");
//     System.out.println(String.valueOf(maxValue));
//     maxValue = Integer.MIN_VALUE;
//     if (fileReaders.size() != 0)
//         fileWithMaxValue.readLine();  // Move to next line in file where we find maxValue
// }
// writer.close();