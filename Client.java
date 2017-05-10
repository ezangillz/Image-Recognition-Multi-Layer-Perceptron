import java.io.Serializable;
import java.util.ArrayList;

public class Client implements Serializable {

    private static final int NUMBER_OF_INPUTS = 32;
    private ArrayList<String> names;
    private ArrayList<Neuron> hiddenLayer;
    private ArrayList<Neuron> outputLayer;
    private double learningRate;
    private double momentum;

    public static void main(String[] args) {
        Client client = new Client();
    }

    /**
     * checks if there is a saved network already exists, if not then prints an error
     */
    private Client() {

    }

    /**
     * adds NAME to the NAMES list, checks the size of NAMES list and based on that generates assumed output
     * say it's the 2nd learnt object out of 4, then assumed output will be [0, 1, 0, 0]
     * after that it passes the image to the network, gets output from output level and checks results with the assumed output
     * @param imagePath - path to the image, that is going to be opened and used
     * @param name - name of the object
     */
    private void learn(String[] imagePath, String name) {

    }

    /**
     * @param imagePath just passes image path to the picture that is to be recognized by the network
     */
    private void feedPicture(String [] imagePath) {

    }

    /**
     * also changes number of hidden layer neurons based on the number of input and output layers
     * @param numberOfObjects number of objects which will be used for the recognition
     */
    private void newNetwork(int numberOfObjects) {

    }

    /**
     * goes through the whole network and corrects the weights using the back propagation
     * @param delta - is passed for each neuron to relearn
     */
    private void relearn(double delta) {

    }

    /**
     * resets the network
     */
    private void reset() {

    }

    private void save() {

    }

    private void open() {

    }

}
