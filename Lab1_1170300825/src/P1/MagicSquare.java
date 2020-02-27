package P1;

import java.io.*;
import java.util.Set;

public class MagicSquare {

    public final int maxn = 1000;
    public static final String FILENAME = "E:\\CodeTraining\\ProgramLessons\\SC\\Lab1_1170300825\\src\\P1\\txt\\5.txt";
    public static final String OUTPUT_FILENAME = "E:\\CodeTraining\\ProgramLessons\\SC\\Lab1_1170300825\\src\\P1\\txt\\6.txt";

    public boolean isLegalMagicSquare(String filename) {
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
            reader = new InputStreamReader(new FileInputStream(filename));
            br = new BufferedReader(reader);
            String myLine;
            try {
                int[][] square = new int[maxn][maxn];
                String[] tmp ;
                int N = 0;
                int sd_N = -1;
                myLine = br.readLine();
                while (myLine != null) {
                    tmp = myLine.split("\t");
                    if(sd_N==-1) {
                        sd_N = tmp.length;
                    } else {
//                        �������Ƿ��������
                        if(sd_N!=tmp.length) {
                            System.out.println("ERROR: ����������в����");
                            return false;
                        }
                    }
                    for(int i=0;i<tmp.length;i++) {
                        try {
                            square[N][i] = Integer.valueOf(tmp[i]);
                            if(square[N][i]<=0) {
                                System.out.println("ERROR: ������������Ҫ��");
                                return false;
                            }
                        } catch(Exception e) {
                            System.out.println("ERROR: ������������Ҫ��||����֮�䲢����\\t�ָ�");
//                            e.printStackTrace();
                            return false;
//                            e.printStackTrace();
                        }
                    }
                    N++;
                    myLine = br.readLine(); // һ�ζ���һ������
                }
                
                if(sd_N!=N) {
                    System.out.println("ERROR: ����������в����");
                    return false;
                }

//                ���MagicSquare
                int sd_sum = 0;
                for(int i=0;i<N;i++) sd_sum += square[0][i];
//                �����
                for(int i=1;i<N;i++) {
                    int tmp_sum = 0;
                    for(int j=0;j<N;j++) {
                        tmp_sum += square[i][j];
                    }
                    if(tmp_sum!=sd_sum) return false;
                }
//                  �����
                for(int j=0;j<N;j++) {
                    int tmp_sum = 0;
                    for(int i=0;i<N;i++) {
                        tmp_sum += square[i][j];
                    }
                    if(tmp_sum!=sd_sum) return false;
                }
//                 ���Խ���
                int x_sum = 0,y_sum = 0;
                for(int i=0;i<N;i++) {
                    x_sum+=square[i][i];
                    y_sum+=square[i][N-1-i];
                }
                if(x_sum!=sd_sum) return false;
                if(y_sum!=sd_sum) return false;
//                ͨ����֤ Ϊħ������
                return true;
            } catch (IOException e) {
                System.out.println("ERROR: IO����");
//                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: �ļ�δ�ҵ�");
//            e.printStackTrace();
        }
        try {
        	br.close();
        }catch(IOException e) {
        	e.printStackTrace();
        }
        return true;
    }

//    �����׻÷���������޲�������д�ķ����ǣ�
//
//    ��1������С���������ڵ�һ�����У������¹�������ʣ�µ�(n��n��1)������
//            ��1��ÿһ��������ǰһ����������һ��
//
//            ��2������������Ҫ�ŵĸ��Ѿ������˶�����ô�Ͱ������ڵ��У���ȻҪ������һ�У�
//
//            ��3������������Ҫ�ŵĸ��Ѿ���������������ô�Ͱ������������У���ȻҪ������һ�У�
//
//            ��4������������Ҫ�ŵĸ��Ѿ������˶����ҳ����������У���ô�Ͱ������ڵ����������У�
//
//            ��5������������Ҫ�ŵĸ��Ѿ��������룬��ô�Ͱ�������ǰһ��������һ��ͬһ�еĸ��ڡ�

//   ����һ��MagicSquare
    public static boolean generateMagicSquare(int n) {
//       (2) �������n���Ϸ�ʱ��nΪż����nΪ�����ȣ�����Ҫ�ú����׳��쳣���Ƿ��˳���������ʾ���󲢡����ŵġ��˳������������false������
        if((n<=0) || ((n&1)==0)) {
            System.out.println("ERROR: ���벻�Ϸ�������������!");
            return false;
        }
        int magic[][] = new int[n][n];  //����magic square��ά����
        int row = 0, col = n / 2, i, j, square = n * n;     //(row��col) ��ǰλ�� squareΪ�ܸ���

        for (i = 1; i <= square; i++) { //ʹ���޲�������һ���÷� for ���� �������е�����i
            magic[row][col] = i;    //��(row,col)λ��������i
            if (i % n == 0)     //���i%n==0������+1
                row++;
            else {  //����
                if (row == 0)   //���row==0������=n-1
                    row = n - 1;
                else            //��������--
                    row--;

                if (col == (n - 1))  //�������==n-1������=0
                    col = 0;
                else            //��������++
                    col++;
            }
        }
////      ��ӡ���ɵĻ÷�
//        for (i = 0; i < n; i++) {
//            for (j = 0; j < n; j++)
//                System.out.print(magic[i][j] + "\t");
//            System.out.println();
//        }
//      (1) ��������magic squareд���ļ�\src\P1\txt\6.txt�У�
        String filename = OUTPUT_FILENAME;
        
        try {
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(filename));
            BufferedWriter bw = new BufferedWriter(writer);
            for(i=0;i<n;i++) {
                StringBuffer bf = new StringBuffer();
                for(j=0;j<n;j++) {
                    bf.append(Integer.toString(magic[i][j])+"\t");
                }
                bf.append("\n");
                try {
                    bw.write(bf.toString());
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                bw.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
//        generateMagicSquare(11);
        if(new MagicSquare().isLegalMagicSquare(FILENAME)) {
            System.out.println("����������һ��MagicSquare");
        } else {
            System.out.println("�������벻��һ��MagicSquare");
        }
    }
}
