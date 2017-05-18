public class Client {

    private static MLP mlp;

    public static void main(String[] args) {
        mlp = new MLP();
        mlp = mlp.open();
        if (args.length == 0) {
            System.out.println("No options passed, use -help for more info");
            System.exit(0);
        }
        else if (args[0].equals("new-network")) {
            String[] names = new String[args.length-1];
            for (int i = 1; i < args.length; i++)
                names[i-1] = args[i];
            mlp = new MLP(names);
            mlp.save(mlp);
        }

        if (null == mlp) {
            System.out.println("Create a new network first.");
            System.exit(0);
        } else {
            if (args[0].equals("learn")) {
                for (int i = 2; i < args.length; i++) {
                    //mlp.recognize(args[1], args[i]);
                    mlp.save(mlp);
                }
            }
            else if (args[0].equals("run")) {
                // args[1] - times
                mlp.run(Integer.parseInt(args[1]));
                mlp.save(mlp);
            }
            else if (args[0].equals("set")) {
                if (args[1].equals("paths")) {
                    String name = args[2];
                    String paths[] = new String[args.length-3];
                    for (int i = 3; i < args.length; i++)
                        paths[i-3] = args[i];
                    mlp.setPaths(name, paths);
                    mlp.save(mlp);
                }
                if (args[1].equals("learning-rate")) {
                    mlp.setLearningRate(Double.parseDouble(args[2]));
                    mlp.save(mlp);
                }
                if (args[1].equals("momentum")) {
                    mlp.setMomentum(Double.parseDouble(args[2]));
                    mlp.save(mlp);
                }
            }
            else if (args[0].equals("get")) {
                if (args[1].equals("paths")) {
                    mlp.showAllPaths();
                }
                if (args[1].equals("learning-rate")) {
                    System.out.println(mlp.getLearningRate());
                }
                if (args[1].equals("momentum")) {
                    System.out.println(mlp.getMomentum());
                }
            }
            else if (args[0].equals("shuffle")) {
                mlp.shufflePaths(Integer.parseInt(args[1]));
                mlp.save(mlp);
            }
            else if (args[0].equals("reset")) {

            }
            else if (args[0].equals("rec")) {
                double[] inputs = Normalizer.getNormalized(args[1], 64);
                double[] outputs = mlp.propagate(inputs);
                mlp.showOutput(outputs);
            }
        }
    }
}
