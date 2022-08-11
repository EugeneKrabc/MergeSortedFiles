import CmdArgumentsParser.CmdArgumentsParser;

public class Main {
    public static void main(String[] args) {
        try {
            CmdArgumentsParser argsData = new CmdArgumentsParser(args);
            System.out.println("Output file name: " + argsData.getOutputFileName());
            System.out.println("Input file names: " + argsData.getInputFileNames());
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
