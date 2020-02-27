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
//                        �������Ƿ��������
                        if(sd_N!=tmp.length) {
                            System.out.println("ERROR: �����������nxnҪ��");
                            return ;
                        }
                    }
                    for(int i=0;i<tmp.length;i++) {
                        try {
                            square[N][i] = Integer.valueOf(tmp[i]);
                            if(square[N][i]<=0) {
                                System.out.println("ERROR: ������������Ҫ�󣨸������㣩");
                                return ;
                            }
                        } catch(Exception e) {
                            System.out.println("ERROR: ������������Ҫ�󣨷����֣� || ����֮�䲢����\\t�ָ�");
//                            e.printStackTrace();
                            return ;
//                            e.printStackTrace();
                        }
                    }
                    N++;
                    myLine = br.readLine(); // һ�ζ���һ������
                }
                for(int i=0;i<N;i++) {
                	StringBuilder sbBuilder=new StringBuilder();
                	for(int j=0;j<N;j++) {
                		sbBuilder.append(square[i][j]+" ");
                	}
                	System.out.println(sbBuilder.toString());
                }
                
                if(sd_N!=N) {
                    System.out.println("ERROR: �����������nxnҪ��");
                    return ;
                }

//          
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
	}

}
