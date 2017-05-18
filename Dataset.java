import java.io.Serializable;

public class Dataset implements Serializable {

    private String path;
    private double[] expectedOutput;

    Dataset(String path, double[] expectedOutput) {
        this.path = path;
        this.expectedOutput = expectedOutput;
    }

    public String getPath() {
        return path;
    }

    public double[] getExpectedOutput() {
        return expectedOutput;
    }
}
