import java.io.Serializable;
import java.util.concurrent.ThreadLocalRandom;

public class Neuron implements Serializable {

    private static final double minRange = -0.5;
    private static final double maxRange = 0.5;

    private double delta;
    private double sum;
    private double output;
    private double[] inputs;
    private double[] weights;

    Neuron(int capacity) {
        weights = new double[capacity];
        for (int i = 0; i < capacity; i++)
            weights[i] = ThreadLocalRandom.current().nextDouble(minRange, maxRange + 1);
        delta = 0;
        output = 0;
    }

    double activate(double[] inputs) {
        this.inputs = inputs;
        sum = 0;
        for (int i = 0; i < inputs.length; i++)
            sum += weights[i] * inputs[i];
        output = 1.0 / (1.0 + (Math.exp(-sum)));
        return output;
    }

    void correctWeights(double momentum, double learningRate) {
        for (int i = 0; i < weights.length; i++) {
            weights[i] = weights[i] * momentum + learningRate * delta * (output * (1 - output))  * inputs[i];

        }
    }

    double getOutput() { return output; }
    double getDelta() {return delta; }
    double getWeight(int number) { return weights[number]; }

    void setDelta(double value) { this.delta = value; }

}
