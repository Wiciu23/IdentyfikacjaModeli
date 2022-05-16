import java.util.ArrayList;

public class Testy {
        public static void main(String args[]){

                ObjectProperties[] tab = ExcelReader.getObjectPropertiesExcel("C:\\Users\\LENOVO\\Desktop\\Wimip\\identyfikacja_modeli\\dane\\Doswiadczenia.xlsx");
                System.out.println(tab.toString());
                double[] tablica = {1.3498872562941594E13, 4.344006308903154, 0.018700272034451595, 2.7116666845391613E13, 4.344006308903154, 0.03805946765899499, 4.197303609035563E14, 7.902045617908976, 0.008516059638842997, 1.6241640012284353, 0.0012504916867971019, -0.15311819879428212, -0.8256531945138955, 162.12492051247042, -0.0838357018545441};   System.out.println(Function.functionA(tablica));
                //System.out.println(Function.testowaFunkcja());
            }
}

