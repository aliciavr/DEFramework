package planner.utils;

public class PrintingTools {
    public static String toString(Object[] v) {
        String vStr = "[";
        
        for (int i = 0; i < v.length - 1; i++) {
            vStr += v[i].toString() + ", ";
        }
        vStr += v[v.length - 1] + "]";
        
        return vStr;
    }

    public static String toString(int[] v) {
        String vStr = "[";
        
        for (int i = 0; i < v.length - 1; i++) {
            vStr += v[i] + ", ";
        }
        vStr += v[v.length - 1] + "]";
        
        return vStr;
    }
    
    public static String toString(double[] v) {
        String vStr = "[";
        
        for (int i = 0; i < v.length - 1; i++) {
            vStr += v[i] + ", ";
        }
        vStr += v[v.length - 1] + "]";
        
        return vStr;
    }
    
    public static String toString(double[][] v) {
        String vStr = "";
        
        for (int i = 0; i < v.length; i++) {
            for (int j = 0; j < v[i].length - 1; j++) {
                vStr += v[i][j] + ", ";
            }
            vStr += v[i][v[i].length - 1] + "\n";
        }
        
        
        return vStr;
    }
}
