import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Normalizer {

    /**
     * Opens image, normalizes it and returns a normalized array
     */
    public static double[] getNormalized(String imagePath, int numberOfInputs) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            double[] bins = new double[numberOfInputs];
            int all = image.getWidth() * image.getHeight();

            for (int x = 0; x < image.getWidth(); x++) {
                for (int y = 0; y < image.getHeight(); y++) {
                    int rgb = image.getRGB(x, y);
                    int bin = getBin((rgb>>16)&0xff, (rgb>>8)&0xff, rgb&0xff);
                    bins[bin] += 1;
                }
            }
            for (int i = 0; i < numberOfInputs; i++) {
                bins[i] = bins[i] / all;
            }

            return bins;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static int getBin(int R, int G, int B) {
        R = getBinHelper(R);
        G = getBinHelper(G);
        B = getBinHelper(B);
        int bin = R * 4*4 + G * 4 + B;
        return bin;
    }

    private static int getBinHelper(int value) {
        if (value < 64) {
            return 0;
        }
        else if (value < 128) {
            return 1;
        }
        else if (value < 192) {
            return 2;
        }
        else {
            return 3;
        }
    }

}
