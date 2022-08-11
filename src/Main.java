import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private enum DataType {
        INTEGERS,
        STRINGS
    }
    private enum SortType {
        ASCENDING,
        DESCENDING
    }

    private static boolean haveOptionalArgument;
    private static SortType sortType = null;
    private static DataType dataType = null;
    private static String outputFile;
    private static ArrayList<String> inputFiles = new ArrayList<>();

    static void parseCmdArgs(String[] args) throws Exception {
        if (args.length < 3)
            throw new Exception("Wrong number of arguments");

        getDataType(args);
        getSortType(args);

        if (dataType == null) {
            throw new Exception("Wrong arguments: no data");
        }

        if (sortType == null) {
            sortType = SortType.ASCENDING;
            haveOptionalArgument = false;
        } else {
            haveOptionalArgument = true;
        }

        int outputFileIndex;
        if (haveOptionalArgument)
            outputFileIndex = 2;
        else
            outputFileIndex = 1;
        outputFile = new String(args[outputFileIndex]);

        for (int i = outputFileIndex + 1; i < args.length; i++)
            inputFiles.add(args[i]);
    }

    static void getDataType(String[] args) throws Exception {
        if (args[0].equals("-s") || args[1].equals("-s")) {
            if (dataType == DataType.INTEGERS)
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            
            dataType = DataType.STRINGS;
        }
        
        if (args[0].equals("-i") || args[1].equals("-i")) {
            if (dataType == DataType.STRINGS)
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            
            dataType = DataType.INTEGERS;
        }
    }

    static void getSortType(String[] args) throws Exception {
        if (args[0].equals("-a") || args[1].equals("-a")) {
            if (sortType == SortType.DESCENDING)
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");

            sortType = SortType.ASCENDING;
        }

        if (args[0].equals("-d") || args[1].equals("-d")) {
            if (sortType == SortType.ASCENDING)
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");
        
            sortType = SortType.DESCENDING;
        }
    }
    

    public static void main(String[] args) {
        try {
            parseCmdArgs(args);
            System.out.println(inputFiles);
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}
