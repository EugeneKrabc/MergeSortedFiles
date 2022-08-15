package helperclasses;
import java.util.Arrays;
import java.util.LinkedList;

public final class CmdArguments {
    public CmdArguments(String[] args) throws Exception{
        this.args = args;
        parseCmdArgs();
    }

    public String getOutputFileName() {
        return outputFile;
    }

    public LinkedList<String> getInputFileNames() {
        return inputFiles;
    }

    public DataType getDataType() {
        return dataType;
    }

    public SortType getSortType() {
        return sortType;
    }

//    public enum SortType {
//        ASCENDING,
//        DESCENDING
//    }
//    public enum DataType {
//        INTEGERS,
//        STRINGS
//    }
    private SortType sortType;
    private DataType dataType;
    private final String[] args;
    private String outputFile;
    private final LinkedList<String> inputFiles = new LinkedList<>();

    private void parseCmdArgs() throws Exception {
        if (args.length < 3) {
            throw new Exception("Wrong number of arguments.");
        }

        parseDataType(args);
        parseSortType(args);
        if (dataType == null) {
            throw new Exception("Wrong arguments: no data type specified( -i or -s)");
        }

        boolean haveOptionalArgument;
        if (sortType == null) {
            sortType = SortType.ASCENDING;
            haveOptionalArgument = false;
        } else {
            haveOptionalArgument = true;
        }

        int outputFileIndex;
        if (haveOptionalArgument) {
            outputFileIndex = 2;
        } else {
            outputFileIndex = 1;
        }
        outputFile = args[outputFileIndex];
        inputFiles.addAll(Arrays.asList(args).subList(outputFileIndex + 1, args.length));
    }

    private void parseDataType(String[] args) throws Exception {
        if (args[0].equals(args[1])) {
            throw new Exception("Wrong arguments: identical flags are not allowed");
        }

        if (args[0].equals("-s") || args[1].equals("-s")) {
            if (dataType == DataType.INTEGERS) {
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            }

            dataType = DataType.STRINGS;
        }

        if (args[0].equals("-i") || args[1].equals("-i")) {
            if (dataType == DataType.STRINGS) {
                throw new Exception("Wrong date type: you should pass '-s' or '-i', not both");
            }

            dataType = DataType.INTEGERS;
        }
    }

    private void parseSortType(String[] args) throws Exception {
        if (args[0].equals("-a") || args[1].equals("-a")) {
            if (sortType == SortType.DESCENDING) {
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");
            }

            sortType = SortType.ASCENDING;
        }

        if (args[0].equals("-d") || args[1].equals("-d")) {
            if (sortType == SortType.ASCENDING) {
                throw new Exception("Wrong sort type: you should pass '-a' or '-d', not both");
            }

            sortType = SortType.DESCENDING;
        }
    }
}
