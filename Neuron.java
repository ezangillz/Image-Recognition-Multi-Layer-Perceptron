import java.io.Serializable;

public class Neuron implements Serializable {

    private double[] weights;
    private double bias;

    public void generate(int numberOfInputs) {

    }

    /**
     * Takes inputs, multiplies each input by each weight and adds bias, activates it and returns the output
     */
    public double[] calculate(double[] inputs) {
        return null;
    }

    private double sumUp(double[] inputs) {
        return 0.0;
    }

    private double sigmoid(double sum) {
        return 0.0;
    }

    private double relearn(double delta) {
        return 0.0;
    }

}
