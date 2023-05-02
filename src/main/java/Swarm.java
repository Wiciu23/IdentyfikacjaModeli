import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.*;


/**`
 * Represents a swarm of particles from the Particle Swarm Optimization algorithm.
 */
public class Swarm{

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    private Publisher publisher;
    private int numOfParticles, epochs;
    private double inertia, cognitiveComponent, socialComponent;
    private Vector bestPosition;
    private double bestEval;
    private Particle.FunctionType function; // The function to search.
    public static final double DEFAULT_INERTIA = 0.729844;
    public static final double DEFAULT_COGNITIVE = 1.496180; // Cognitive component. == Social component
    public static final double DEFAULT_SOCIAL = 1.496180; // Social component. 1,496180
    int vectorLength;

    ObjectProperties[] dataTable;

    /**
     * When Particles are created they are given a random position.
     * The random position is selected from a specified range.
     * If the begin range is 0 and the end range is 10 then the
     * value will be between 0 (inclusive) and 10 (exclusive).
     */
    private int beginRange, endRange;
    private static final int DEFAULT_BEGIN_RANGE = -100;
    private static final int DEFAULT_END_RANGE = 101;

    /**
     * Construct the Swarm with default values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     */
    public Swarm (Particle.FunctionType function, int particles, int epochs) {
        this(function, particles, epochs, DEFAULT_INERTIA, DEFAULT_COGNITIVE, DEFAULT_SOCIAL);
    }


    /**
     * Construct the Swarm with custom values.
     * @param particles     the number of particles to create
     * @param epochs        the number of generations
     * @param inertia       the particles resistance to change
     * @param cognitive     the cognitive component or introversion of the particle
     * @param social        the social component or extroversion of the particle
     */
    public Swarm (Particle.FunctionType function, int particles, int epochs, double inertia, double cognitive, double social) {
        this.numOfParticles = particles;
        this.epochs = epochs;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        this.function = function;
        double infinity = Double.POSITIVE_INFINITY;
        bestPosition = new Vector(new double[]{infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity,infinity});
        bestEval = Double.POSITIVE_INFINITY;
        beginRange = DEFAULT_BEGIN_RANGE;
        endRange = DEFAULT_END_RANGE;
        vectorLength = Particle.getVectorLenght(function);
    }

    public Vector getBestPosition() {
        return bestPosition;
    }

    public Particle.FunctionType getFunction() {
        return function;
    }

    /**
     * Execute the algorithm.
     */
    public void run () {
        Particle[] particles = initialize();
        //DLA KAŻDEGO PUNKTU ITERUJEMY PO WSZYSTKICH DANYCH W EXCELU DLA WSZYSTKICH PUNKTÓW Z KAŻDĄ INDYWIDUALNĄ TEMPERATURĄ,
        //TRZEBA DODAĆ INDYWIDDUALNE TWORZENIE FUNKCJI I TA FUNKCJA BĘDZIE ITEROWAĆ PO WARTOŚCIACH TAKIE JAKIE SĄ W EXCELU, A DO NIEJ
        // BĘDĄ DODAWANE TYLKO PARAMETRY A

        double oldEval = bestEval;
        System.out.println("--------------------------EXECUTING-------------------------");
        System.out.println("Global Best Evaluation (Epoch " + 0 + "):\t"  + bestEval + "Vec " + bestPosition.toString());

        /*try{
            // Create file
            FileWriter fstream = new FileWriter("out.txt");
            BufferedWriter out = new BufferedWriter(fstream);
            //Close the output stream
*/
        //for (int i = 0; i < epochs; i++) {
        int i = 0;
        while(bestEval > 0.000000001){

            if (bestEval < oldEval) {
                System.out.print("Best" + (i + 1) + "):\t" + bestEval + "Vec: " + bestPosition.toString());
                oldEval = bestEval;
                publisher.publish(bestPosition.getCordinates());
               // out.write(String.valueOf(bestEval) + " \n");
            }

            for (Particle p : particles) {
                p.updatePersonalBest();
                updateGlobalBest(p);
            }

            for (Particle p : particles) {
                updateVelocity(p);
                p.updatePosition();
            }

            //System.out.println("   Epoch No: " + i + " | CURRENT ERROR: " + oldEval + "  BEST ERROR: " + bestEval + " |");
            System.out.println("   Epoch No: " + i + "= " + bestEval);
            i++;
            //if(bestEval < 0.0001) break;

        }

        System.out.println("---------------------------RESULT---------------------------");
        System.out.println("a = " + bestPosition.toString());
        System.out.println("Final Best Evaluation: " + bestEval);
        System.out.println("---------------------------COMPLETE-------------------------");

      /*  out.close();
        }catch (Exception e){//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        } */

    }

    /**
     * Create a set of particles, each with random starting positions.
     * @return  an array of particles
     */
    private Particle[] initialize () {
        Particle[] particles = new Particle[numOfParticles];
        for (int i = 0; i < numOfParticles; i++) {
            Particle particle = new Particle(function, beginRange, endRange,vectorLength);
            particles[i] = particle;
            updateGlobalBest(particle);
        }
        return particles;
    }
    //DODAĆ OBIEKT DANYCH BEZPOŚREDNIO DO ROJU.
    /**
     * Update the global best solution if a the specified particle has
     * a better solution
     * @param particle  the particle to analyze
     */
    private void updateGlobalBest (Particle particle) {
        if (particle.getBestEval() < bestEval) {
            bestPosition = particle.getBestPosition();
            bestEval = particle.getBestEval();
        }
    }

    /**
     * Update the velocity of a particle using the velocity update formula
     * @param particle  the particle to update
     */
    private void updateVelocity (Particle particle) {
        Vector oldVelocity = particle.getVelocity();
        Vector pBest = particle.getBestPosition();
        Vector gBest = bestPosition.clone();
        Vector pos = particle.getPosition();

        Random random = new Random();
        double r1 = random.nextDouble();
        double r2 = random.nextDouble();

        // The first product of the formula.
        Vector newVelocity = oldVelocity.clone();
        newVelocity.mul(inertia);

        // The second product of the formula.
        pBest.sub(pos);
        pBest.mul(cognitiveComponent);
        pBest.mul(r1);
        newVelocity.add(pBest);

        // The third product of the formula.
        gBest.sub(pos);
        gBest.mul(socialComponent);
        gBest.mul(r2);
        newVelocity.add(gBest);

        particle.setVelocity(newVelocity);
    }

}
