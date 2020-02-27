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
//                        检查矩阵是否行列相等
                        if(sd_N!=tmp.length) {
                            System.out.println("ERROR: 输入矩阵行列不相等");
                            return false;
                        }
                    }
                    for(int i=0;i<tmp.length;i++) {
                        try {
                            square[N][i] = Integer.valueOf(tmp[i]);
                            if(square[N][i]<=0) {
                                System.out.println("ERROR: 不满足正整数要求");
                                return false;
                            }
                        } catch(Exception e) {
                            System.out.println("ERROR: 不满足正整数要求||数字之间并非用\\t分割");
//                            e.printStackTrace();
                            return false;
//                            e.printStackTrace();
                        }
                    }
                    N++;
                    myLine = br.readLine(); // 一次读入一行数据
                }
                
                if(sd_N!=N) {
                    System.out.println("ERROR: 输入矩阵行列不相等");
                    return false;
                }

//                检查MagicSquare
                int sd_sum = 0;
                for(int i=0;i<N;i++) sd_sum += square[0][i];
//                检查行
                for(int i=1;i<N;i++) {
                    int tmp_sum = 0;
                    for(int j=0;j<N;j++) {
                        tmp_sum += square[i][j];
                    }
                    if(tmp_sum!=sd_sum) return false;
                }
//                  检查列
                for(int j=0;j<N;j++) {
                    int tmp_sum = 0;
                    for(int i=0;i<N;i++) {
                        tmp_sum += square[i][j];
                    }
                    if(tmp_sum!=sd_sum) return false;
                }
//                 检查对角线
                int x_sum = 0,y_sum = 0;
                for(int i=0;i<N;i++) {
                    x_sum+=square[i][i];
                    y_sum+=square[i][N-1-i];
                }
                if(x_sum!=sd_sum) return false;
                if(y_sum!=sd_sum) return false;
//                通过验证 为魔术方阵
                return true;
            } catch (IOException e) {
                System.out.println("ERROR: IO错误");
//                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: 文件未找到");
//            e.printStackTrace();
        }
        try {
        	br.close();
        }catch(IOException e) {
        	e.printStackTrace();
        }
        return true;
    }

//    奇数阶幻方最经典的填法是罗伯法。填写的方法是：
//
//    把1（或最小的数）放在第一行正中；按以下规律排列剩下的(n×n－1)个数：
//            （1）每一个数放在前一个数的右上一格；
//
//            （2）如果这个数所要放的格已经超出了顶行那么就把它放在底行，仍然要放在右一列；
//
//            （3）如果这个数所要放的格已经超出了最右列那么就把它放在最左列，仍然要放在上一行；
//
//            （4）如果这个数所要放的格已经超出了顶行且超出了最右列，那么就把它放在底行且最左列；
//
//            （5）如果这个数所要放的格已经有数填入，那么就把它放在前一个数的下一行同一列的格内。

//   生成一个MagicSquare
    public static boolean generateMagicSquare(int n) {
//       (2) 当输入的n不合法时（n为偶数、n为负数等），不要该函数抛出异常并非法退出，而是提示错误并“优雅的”退出——函数输出false结束。
        if((n<=0) || ((n&1)==0)) {
            System.out.println("ERROR: 输入不合法，请输入奇数!");
            return false;
        }
        int magic[][] = new int[n][n];  //声明magic square二维数组
        int row = 0, col = n / 2, i, j, square = n * n;     //(row，col) 当前位置 square为总个数

        for (i = 1; i <= square; i++) { //使用罗伯法生成一个幻方 for 依次 填入所有的数字i
            magic[row][col] = i;    //在(row,col)位置上填入i
            if (i % n == 0)     //如果i%n==0则行数+1
                row++;
            else {  //否则
                if (row == 0)   //如果row==0则行数=n-1
                    row = n - 1;
                else            //否则行数--
                    row--;

                if (col == (n - 1))  //如果列数==n-1则列数=0
                    col = 0;
                else            //否则列数++
                    col++;
            }
        }
////      打印生成的幻方
//        for (i = 0; i < n; i++) {
//            for (j = 0; j < n; j++)
//                System.out.print(magic[i][j] + "\t");
//            System.out.println();
//        }
//      (1) 将产生的magic square写入文件\src\P1\txt\6.txt中；
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
            System.out.println("您的输入是一个MagicSquare");
        } else {
            System.out.println("您的输入不是一个MagicSquare");
        }
    }
}
