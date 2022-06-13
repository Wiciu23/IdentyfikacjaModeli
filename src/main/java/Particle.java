import java.util.Random;

/**
 * Represents a particle from the Particle Swarm Optimization algorithm.
 */
class Particle {

    private Vector position;        // Current position.
    private Vector velocity;
    private Vector bestPosition;    // Personal best solution.
    private double bestEval;        // Personal best value.
    private FunctionType function;  // The evaluation function to use.

    ObjectProperties[] dataTable = ExcelReader.getObjectPropertiesExcel("C:\\Users\\java\\Particle_swarm\\IdentyfikacjaModeli\\Dane_lab5.xlsx");

    /**
     * Construct a Particle with a random starting position.
     * @param beginRange    the minimum xyz values of the position (inclusive)
     * @param endRange      the maximum xyz values of the position (exclusive)
     */
    Particle (FunctionType function, int beginRange, int endRange, int vectorLength) {
        if (beginRange >= endRange) {
            throw new IllegalArgumentException("Begin range must be less than end range.");
        }
        this.function = function;
        position = new Vector(vectorLength);
        velocity = new Vector(vectorLength);
        setRandomPosition(vectorLength);
        bestPosition = velocity.clone();
        bestEval = eval();
    }

    Particle (FunctionType function, int beginRange, int endRange, int vectorLength, ObjectProperties[] dataTable) {
        this(function,beginRange,endRange,vectorLength);
        this.dataTable = dataTable;
    }

    /**
     * The evaluation of the current position.
     * @return      the evaluation
     */
    private double eval () {
        if (function == FunctionType.FunctionA) {
            return Function.functionA(position.getCordinates());
        } else if (function == FunctionType.FunctionB) {
            return Function.functionB(position.getCordinates());
        } else if (function == FunctionType.FunctionB1) {
            return Function.functionB1(position.getCordinates());
        } else if (function == FunctionType.FunctionC) {
            return Function.functionC(position.getCordinates(),dataTable);
        } else
            return 0;

    }


     private void setRandomPosition (int vectorLength) {
        double a[] = new double[vectorLength];

        if(function == FunctionType.FunctionA) {
            a[0] = rand(1, 1000);
            a[1] = rand(0, 1);
            a[2] = rand(0, 1);
            a[3] = rand(1, 10000);
            a[4] = rand(0, 1);
            a[5] = rand(1, 90000);
            a[6] = rand(0, 1);
        }else if (function == FunctionType.FunctionB){
            //USTAWIĆ TAK, ŻEBY MÓC ZAFIXOWAĆ PARAMETRY, I ZEBY NIE BYŁ NA NOWO USTAWIONY WEKTOR PRĘDKOSCI
            /*a[0] = 1.2659262424840746E13;
            a[1] = 4.328702792037288;
            a[2] = 0.0189090108863801; */
            a[0] = rand(1E+12, 1E+14);
            a[1] = rand(3.0, 6.0);
            a[2] = rand(1E-4, 0.1);
            a[3] = rand(1E+11, 1E+13);
            a[4] = a[1];
            a[5] = rand(1E-4, 0.01);
            a[6] = rand(1E+12, 1E+14);
            a[7] = rand(2.0, 5.0);
            a[8] = rand(1E-4, 0.01);
            a[9] = rand(0.01, 0.5);
            a[10] = rand(1E-6, 0.001);
            a[11] = rand(1E-6, 0.1);
            a[12] = rand(0, 1.0);
            a[13] = rand(1.0, 50.0);
            a[14] = rand(-1.0, 0);
        }else if (function == FunctionType.FunctionB1){
            a[0] = rand(1E+12, 1E+14);
            a[1] = rand(3.0, 6.0);
            a[2] = rand(1E-4, 0.1);
        }else if (function == FunctionType.FunctionC){
            a[0] = rand(0.05*0.001, 0.15*0.001);
            a[1] = rand(15000, 22000);
            a[2] = rand(50E3, 100E3);
            a[3] = rand(3E10*0.01,3E10*0.09);
            a[4] = rand(1E3*100,1E3*150);
            a[5] = rand(1.5,2.5);
            a[6] = rand(0,0);
            a[7] = rand(0.2,0.8);
            a[8] = rand(0.05,0.25);
            a[9] = rand(0.1,0.9);
            a[10] = rand(0,0);
            a[11] = rand(1E13*0.00001,1E13*0.00009);
            a[12] = rand(0.01,0.09);
        }else{ for(int i = 0; i < a.length ; i++){
            a[i] = rand(0,100);
        }

        }

        //dodac na sztywno generowanie zakresów parametru a jako tablicy!!! i do position set wcodzi tablica parametru a
        position.set(a);
    }

    /**
     * Generate a random number between a certain range.
     * @param beginRange    the minimum value (inclusive)
     * @param endRange      the maximum value (exclusive)
     * @return              the randomly generated value
     */
    private static double rand (double beginRange, double endRange) {
        Random r = new java.util.Random();
        return beginRange + (r.nextDouble()*(endRange-beginRange));
    }

    private static double rand2 (double beginRange, double endRange){
        Random r = new Random();
        return beginRange + (r.nextDouble()/(1.0))*endRange;
    }

    /**
     * Update the personal best if the current evaluation is better.
     */
    void updatePersonalBest () {
        double eval = eval();
        if (eval < bestEval) {
            bestPosition = position.clone();
            bestEval = eval;
        }
    }

    /**
     * Get a copy of the position of the particle.
     * @return  the x position
     */
    Vector getPosition () {
        return position.clone();
    }

    /**
     * Get a copy of the velocity of the particle.
     * @return  the velocity
     */
    Vector getVelocity () {
        return velocity.clone();
    }

    /**
     * Get a copy of the personal best solution.
     * @return  the best position
     */
    Vector getBestPosition() {
        return bestPosition.clone();
    }

    /**
     * Get the value of the personal best solution.
     * @return  the evaluation
     */
    double getBestEval () {
        return bestEval;
    }

    /**
     * Update the position of a particle by adding its velocity to its position.
     */
    void updatePosition () {
        this.position.add(velocity);
    }

    /**
     * Set the velocity of the particle.
     * @param velocity  the new velocity
     */
    void setVelocity (Vector velocity) {
        this.velocity = velocity.clone();
    }


    public enum FunctionType {
        FunctionA,
        FunctionB,
        FunctionB1,
        FunctionC,
        Ackleys,
        Booths,
        ThreeHumpCamel,
        RosenBrock
    }

    public static int getVectorLenght(FunctionType funkcja){
        if(funkcja == FunctionType.FunctionA){
            return 7;
        }else if (funkcja == FunctionType.FunctionB){
            return 15;
        }else if (funkcja == FunctionType.FunctionB1){
            return 3;
        }else if (funkcja == FunctionType.FunctionC){
            return 13;
        } else return 2;
    }

}
