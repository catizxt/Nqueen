import java.util.*;
public class NQueenClimbHill {
    int n;
    int MAX_N = 30000;
    // int[] rightUp = new int[MAX_N*2];
    // int[] rightDown = new int[MAX_N*2];
    int[] board = new int[MAX_N];
    int[] rightUp = new int[MAX_N*2];
    int[] rightDown = new int[MAX_N*2];
   
    
    public NQueenClimbHill(int n){
        this.n = n;
    }
    
    public void randPosition(int n){
          Random r = new Random(111);
          int[] flag = new int[n];
          int tmp;
          for(int i=0;i<n;i++){
            do{
                  tmp=r.nextInt(n);
            }while(flag[tmp]==1);
            flag[tmp] = 1;
            board[i] = tmp;
          } 
    }

    public int init_conflict(int n){
        int r = 0;
        for (int i = 0; i < n; i++) {
            rightUp[board[i] - i + n] += 1;
            rightDown[board[i]+i] += 1;
        }
        for (int i = 0; i < 2 * n; i++) {
            if (rightUp[i] > 1) r += (rightUp[i] - 1);
            if (rightDown[i] > 1) r += (rightDown[i] - 1);
        }
        return r;      
    }

    public int adjustConflict(int i,int j,int r,int n){
        if(rightUp[board[i]-i+n]>1) r -= 1;
        if(rightUp[board[j]-j+n]>1) r -= 1;
        if(rightUp[board[i]-j+n]>=1) r += 1;
        if(rightUp[board[j]-i+n]>=1) r += 1;
        if(rightDown[board[i]+i]>1) r -= 1;
        if(rightDown[board[j]+j]>1) r -= 1;
        if(rightDown[board[i]+j]>=1) r += 1;
        if(rightDown[board[j]+i]>=1) r += 1;
        return r;
    }

    public int changePosition(int n,int score){
        for(int j=0;j<n-1;j++){
            for(int k=j;k<n;k++){
                int nextScore = adjustConflict(j, k, score, n);
                if(nextScore<score){
                    rightUp[board[j]-j+n] -= 1;
                    rightUp[board[k]-k+n] -= 1;
                    rightDown[board[j]+j] -= 1;
                    rightDown[board[k]+k] -= 1;
                    int tmp = board[k];
                    board[k] = board[j];
                    board[j] = tmp;
                    rightUp[board[j]-j+n] += 1;
                    rightUp[board[k]-k+n] += 1;
                    rightDown[board[j]+j] += 1;
                    rightDown[board[k]+k] += 1;
                    return nextScore;
                }
            }
        }
        return score;
    }


    public void adjustFourQueen(int n){

        Random r = new Random(111); //看一下回收情况
        int[] flag = new int[MAX_N];
        int tmp; 
        int[] position = new int[4];
        for(int i=0;i<4;i++){
            do{
                tmp=r.nextInt(n);
            }while(flag[tmp]==1);
            flag[tmp] = 1;
            position[i] = tmp;
        } 
        tmp = board[position[0]]; board[position[0]] = board[position[1]]; board[position[1]] = tmp;
        tmp = board[position[2]]; board[position[2]] = board[position[3]]; board[position[3]] = tmp;
    }


    public void searchNQueen(int n,int times){
        //试探次数
        randPosition(n);
        for(int i=0;i<times;i++){
            int search_times = 0;
            long startTime = System.currentTimeMillis();    
            int score = init_conflict(n);
            int score_one = 0;
            System.out.printf("initial score is:%d\n",score);
            while(score!=0){
                search_times += 1;
                score = changePosition(n, score);
                if(score == 1){
                    score_one += 1;
                }
                if(score_one>2 && score==1){
                    adjustFourQueen(n);
                    score_one = 0;
                    System.out.printf("adjust four queens' position:%d\n",score);
                }
                System.out.printf("score is:%d\n",score);
            }
            long endTime = System.currentTimeMillis();    //获取开始时间
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
            System.out.printf("随机交换次数:%d\n",search_times);
            /*System.out.println("N个皇后在棋盘的摆放方案如下:\n");
            for(int j=0;j<n;j++){
                System.out.printf("%d",this.board[j]);
            }*/
        }
    }

    public static void main(String[] args){
        int n=100; //可是她到9就是跳不出来
        NQueenClimbHill nQueenClimbHill = new NQueenClimbHill(n);
        nQueenClimbHill.searchNQueen(n, 1);
    }
}
