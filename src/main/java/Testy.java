import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Testy {
        public static void main(String args[]) throws IOException {
            //double[] a = {1.0526579370455271E-5, 15.651291777393416, -2.6004906689578218, 0.0026674853755454184, -2.5946715736381925, -0.0422887220521262, 0.0, 0.452, -0.017180035236922072, 0.409, 0.0, 4.2E-5, 0.00621783922304361};
            double[] a = {1.1137460501497857E-4, 31049.141541228888, 94651.45815004251, 1.5951E9, 234211.3397316366, 2.769637106115258, 0.0, 0.452, 0.27810350762520614, 0.409, 0.0, 4.2E8, 0.07907011963088659};
            ObjectProperties[] dataModel = ExcelReader.getObjectPropertiesExcel("C:\\Users\\java\\Particle_swarm\\IdentyfikacjaModeli\\Dane_lab5.xlsx");
            double Q = 312000;
            int j =0;
            for (ObjectProperties data :
                    dataModel) {
                //Obiekty danych jest przechowywany w funkcji celu:
                FileWriter writer = new FileWriter("output"+Integer.toString(j)+".txt");
                double[] obliczone = DifferentialEq.Euler(data.epsilon[100000], data.epsilon[1], a, data.dot_epsilon, data.temperature + 273, Q, data.epsilon[100001]);
                for (int i = 0; i < obliczone.length; i++) {
                    writer.write(Double.toString(obliczone[i]) + "\n");
                }
                writer.close();
                j++;
            }
        }
}


