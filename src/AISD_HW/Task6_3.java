package AISD_HW;

public class Task6_3 {

    public static void main(String[] args) {
        System.out.println(multiplyKar(23,142));
    }
    private static long multiplyKar(long a, long b){
        if(a<10||b<10)
            return a*b;
        long n = Math.round(getLength(a));

        long x1 = (long) (a/getDegree(n/2));
        long x2 = (long) (a%getDegree(n/2));
        long y1 = (long) (b/getDegree(n/2));
        long y2 = (long) (b%getDegree(n/2));

        long first = multiplyKar(x1,y1);
        long second = multiplyKar(x2,y2);
        long third = multiplyKar(x1+x2, y1+y2);
        return (long) (first*getDegree(n) + (third-second-first)*getDegree(n/2) + second);
    }

    private static long getLength(long a){
        String str = Long.toString(a);
        return str.length();
    }

    private static double getDegree(long n){
        return Math.pow(10, Math.round(n));
    }
}
