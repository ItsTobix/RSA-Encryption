import java.math.BigInteger;
import java.util.ArrayList;

public class EEA {

    public static BigInteger[] EEA(BigInteger a, BigInteger b) {

        ArrayList<BigInteger> r = new ArrayList<>(0);
        ArrayList<BigInteger> s = new ArrayList<>(0);
        ArrayList<BigInteger> t = new ArrayList<>(0);
        ArrayList<BigInteger> q = new ArrayList<>(0);

        r.add(a);
        r.add(b);
        s.add(BigInteger.valueOf(1));
        s.add(BigInteger.valueOf(0));
        t.add(BigInteger.valueOf(0));
        t.add(BigInteger.valueOf(1));
        q.add(null);

        int i = 1;
        while (!r.get(i).equals(BigInteger.ZERO)) {

            q.add((r.get(i - 1)).divide(r.get(i)));
            r.add((r.get(i - 1)).subtract((r.get(i)).multiply(q.get(i))));
            s.add((s.get(i - 1)).subtract((s.get(i)).multiply(q.get(i))));
            t.add((t.get(i - 1)).subtract((t.get(i)).multiply(q.get(i))));
            i++;
        }

        BigInteger[] outputArr = new BigInteger[3];
        outputArr[0] = s.get(i - 1);
        outputArr[1] = t.get(i - 1);
        outputArr[2] = r.get(i - 1);

        return outputArr;

    }
}