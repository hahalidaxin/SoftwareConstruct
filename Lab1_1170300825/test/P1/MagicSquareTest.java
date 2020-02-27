package P1;

import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;

class MagicSquareTest {

	@Test
	void testSplitError() {
		int maxn=1000;
		String filename="E:\\CodeTraining\\ProgramLessons\\SC\\Lab1_1170300825\\src\\P1\\txt\\6.txt";
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
                            System.out.println("ERROR: 输入矩阵不满足nxn要求");
                            return ;
                        }
                    }
                    for(int i=0;i<tmp.length;i++) {
                        try {
                            square[N][i] = Integer.valueOf(tmp[i]);
                            if(square[N][i]<=0) {
                                System.out.println("ERROR: 不满足正整数要求（负数或零）");
                                return ;
                            }
                        } catch(Exception e) {
                            System.out.println("ERROR: 不满足正整数要求（非数字） || 数字之间并非用\\t分割");
//                            e.printStackTrace();
                            return ;
//                            e.printStackTrace();
                        }
                    }
                    N++;
                    myLine = br.readLine(); // 一次读入一行数据
                }
                for(int i=0;i<N;i++) {
                	StringBuilder sbBuilder=new StringBuilder();
                	for(int j=0;j<N;j++) {
                		sbBuilder.append(square[i][j]+" ");
                	}
                	System.out.println(sbBuilder.toString());
                }
                
                if(sd_N!=N) {
                    System.out.println("ERROR: 输入矩阵不满足nxn要求");
                    return ;
                }

//          
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
	}

}
