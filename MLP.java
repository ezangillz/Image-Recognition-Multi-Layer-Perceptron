import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

class MLP implements Serializable {

    private static final int NUMBER_OF_INPUTS = 64;
    private static final String FILE_NAME = "net.ser";
    private String[] names;
    private ArrayList<Dataset> datasets;

    private Neuron[] hiddenLayer;
    private Neuron[] outputLayer;

    private double momentum;
    private double learningRate;

    // -------------------PUBLIC-------------------

    MLP() {}

    MLP(String[] names) {
        this.names = names;
        datasets = new ArrayList<>();

        hiddenLayer = new Neuron[2 * (names.length + NUMBER_OF_INPUTS) / 3];
        for (int i = 0; i < hiddenLayer.length; i++)
            hiddenLayer[i] = new Neuron(NUMBER_OF_INPUTS);

        outputLayer = new Neuron[names.length];
        for (int i = 0; i < outputLayer.length; i++)
            outputLayer[i] = new Neuron(hiddenLayer.length);

        learningRate = 1;
        momentum = 1;
    }

    void run(int times) {
        if (times == 0)
            return;

        for (int i = 0; i < datasets.size(); i++) {
            double[] inputs = Normalizer.getNormalized(datasets.get(i).getPath(), NUMBER_OF_INPUTS);
            double[] output = propagate(inputs);
            backPropagate(datasets.get(i).getExpectedOutput(), output);
        }
        times--;
        run(times);
    }

    void shufflePaths(int times) {
        if (times == 0)
            return;

        for (int i = 0; i < datasets.size(); i++) {
            int k = ThreadLocalRandom.current().nextInt(i, datasets.size());
            Dataset temp = datasets.get(i);
            datasets.set(i, datasets.get(k));
            datasets.set(k, temp);
        }
        times--;
        shufflePaths(times);
    }

    double[] propagate(double[] inputs) {
        double[] hiddenOutputs = new double[hiddenLayer.length];
        for (int i = 0; i < hiddenLayer.length; i++)
            hiddenOutputs[i] = hiddenLayer[i].activate(inputs);

        double[] outputs = new double[outputLayer.length];
        for (int i = 0; i < outputs.length; i++)
            outputs[i] = outputLayer[i].activate(hiddenOutputs);

        return outputs;
    }

    private void backPropagate(double[] expectedOutput, double[] outputs) {
        for (int i = 0; i < outputLayer.length; i++) {
            outputLayer[i].setDelta(expectedOutput[i] - outputs[i]);
            System.out.println(i + ". Expected: " + expectedOutput[i]);
            System.out.println(i + ". Actual: " + outputs[i]);
        }

        for (int i = 0; i < hiddenLayer.length; i++) {
            double delta = 0;
            for (int j = 0; j < outputLayer.length; j++)
                delta += outputLayer[j].getDelta() * outputLayer[j].getWeight(i);
            hiddenLayer[i].setDelta(delta);
        }

        for (int i = 0; i < hiddenLayer.length; i++)
            hiddenLayer[i].correctWeights(momentum, learningRate);

        for (int i = 0; i < outputLayer.length; i++)
            outputLayer[i].correctWeights(momentum, learningRate);

    }

    void setPaths(String name, String[] paths) {
        for (int i = 0; i < paths.length; i++)
            datasets.add(new Dataset(paths[i], getExpectedOutput(name)));
    }

    void showAllPaths() {
        for (int i = 0; i < datasets.size(); i++) {
            System.out.print(datasets.get(i).getPath() + "    ");
            for (int j = 0; j < datasets.get(i).getExpectedOutput().length; j++)
                System.out.print(datasets.get(i).getExpectedOutput()[j] + "\n");
        }
    }

    MLP open() {
        try {
            ObjectInputStream os = new ObjectInputStream(new FileInputStream(FILE_NAME));
            MLP tmp = (MLP) os.readObject();
            os.close();
            return tmp;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    void save(MLP mlp) {
        try {
            ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            os.writeObject(mlp);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void showOutput(double[] outputs) {
        double max = 0;
        int j = 0;
        for (int i = 0; i < outputs.length; i++) {
            System.out.println(names[i] + ": " + outputs[i]);
            if (max < outputs[i]) {
                j = i;
                max = outputs[i];
            }
        }
        System.out.println(names[j]);
    }

    // -------------------PRIVATE-------------------

    private double[] getExpectedOutput(String name) {
        double[] assumedOutput = new double[outputLayer.length];
        for (int i = 0; i < outputLayer.length; i++) {
            if (name.equals(names[i])) {
                assumedOutput[i] = 1;
                for (int j = i+1; j < outputLayer.length; j++) {
                    assumedOutput[j] = 0;
                }
                return assumedOutput;
            }
            else {
                assumedOutput[i] = 0;
            }
        }
        return null;
    }

    public void setLearningRate(double learningRate) {
        this.learningRate = learningRate;
    }

    public void setMomentum(double momentum) {
        this.momentum = momentum;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public double getMomentum() {
        return momentum;
    }
}
