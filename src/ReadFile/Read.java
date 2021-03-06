package ReadFile;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;
/**
 * Created by MYC on 2016/11/9.
 */
public class Read {
    byte[] buffer;
    byte[] newBytes;
    short[] ss;
    double[] d;
    //获得文件的字节信息
    public byte[] fileInput(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException(path);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream((int) file.length());
        BufferedInputStream bufferedInputStream = null;
        try {
            bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            int buf_size = 1024;
             this.buffer = new byte[buf_size];
            int len = 0;
            while ((len = bufferedInputStream.read(buffer, 0, buf_size)) != -1) {
                byteArrayOutputStream.write(buffer, 0, len);
            }
            return byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            try {
                bufferedInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            byteArrayOutputStream.close();
        }

    }

    //删除前46个字节
    public byte[] getDatabyte(byte[] b) {

         this.newBytes = new byte[b.length - 46];
        for (int i = 46; i < b.length; i++) {

            newBytes[i - 46] = b[i];

        }
        return newBytes;

    }

    //byte TO short
    public short[] toShort(byte[] b) throws IOException {

        int dataLength = b.length;
        int shortLength = dataLength / 2;

        ByteBuffer byteBuffer = ByteBuffer.wrap(b, 0, dataLength);
        ShortBuffer shortBuffer = byteBuffer.order(ByteOrder.LITTLE_ENDIAN).asShortBuffer();

         this.ss = new short[shortLength];

        shortBuffer.get(ss, 0, shortLength);
        return ss;
    }

    //short TO double
    public double[] toDouble(short[] s)throws IOException {
       // PrintWriter out = new PrintWriter(new FileWriter(new File("test.csv")));
        this.d = new double[s.length];
        for (int i = 0; i < s.length; i++) {
            d[i] = (double) s[i];///Math.pow(2.0,15);
           // out.printf("%.2f,\n",d[i] );
        }
        return d;

    }

    //double[] -> double[][4096]
   /* static double[][] divideDouble(double[] d)throws IOException {
        int max = d.length;
        int n = max / 4096;
        double[][] ds = new double[n][4096];
        PrintWriter out = new PrintWriter(new FileWriter(new File("test(2).csv")));
        for (int counter = 0; counter  < n; counter++) {
            for (int i = 0; i < 4096; i++) {
                if ((i + 4096 * counter) <= max) {
                    ds[counter][i] = d[i + 4096 * counter];
                    out.printf("%.2f,\n",ds[counter][i] );
                }
            }
        }
        return ds;
    }*/

    public  double[] getDoubles(String path) throws IOException {

        return toDouble(toShort(getDatabyte(fileInput(path))));

    }
    public void deleteArray(){
        this.buffer=null;
        this.newBytes=null;
        this.ss=null;
        this.d=null;
    }
}


