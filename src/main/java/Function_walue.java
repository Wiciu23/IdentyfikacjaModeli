import java.io.BufferedWriter;
import java.io.FileWriter;

public class Function_walue {

    public static void main(String[] args){
        double[] a = {1.3498872562941594E13, 4.344006308903154, 0.018700272034451595, 6.422004635483496E12, 4.344006308903154, -0.010182975811056991, 9.656968680116653E8, -4.045186917813081, 7.582257641115789E-4, -0.2897948826328763, -0.0011992378977853197, -0.10421386016379519, -0.10198155524627987, 2.5649165965705995E-4, 0.3215875044115834};
                for(double i = 0; i < 1 ; i += 0.01){
            System.out.println(Function.funkcja_lab2(i,0.1,850,a));
        }
}
}



