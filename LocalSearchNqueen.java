//import java.util.*;
public class LocalSearchNqueen {
    int n;
    int MAX_N = 1000000;

    int[] rightUp1 = new int[MAX_N*2];  //计算冲突
    int[] rightDown1 = new int[MAX_N*2];
    
    int[] board = new int[MAX_N];
    int[] rightUp = new int[MAX_N*2];  //计算总的冲突？？？
    int[] rightDown = new int[MAX_N*2];
   
    
    public LocalSearchNqueen(int n){
        this.n = n;
    } 

    public int randPos(int start,int end){
        return (int)(Math.random()*(end-start) +start);
    }

    public int init_conflict(int n){
        int r = 0;
    
        for (int i = 0; i < 2 * n; i++) {
            if (rightUp[i] > 1) r += (rightUp[i] - 1);
            if (rightDown[i] > 1) r += (rightDown[i] - 1);
        }
        return r;      
    }

    public void changePosition(int i,int j,int flag){
        if(flag == 0){
            rightDown1[board[i]+i]--;
            rightUp1[board[i]-i+n]--; 
        }

        rightUp[board[j]-j+n] -= 1;
        rightUp[board[i]-i+n] -= 1;
        rightDown[board[j]+j] -= 1;
        rightDown[board[i]+i] -= 1;

        int tmp = board[i];
        board[i] = board[j];
        board[j] = tmp;

        rightUp[board[j]-j+n] += 1;
        rightUp[board[i]-i+n] += 1;
        rightDown[board[j]+j] += 1;
        rightDown[board[i]+i] += 1;

        if(flag==1){
            rightDown1[board[i]+i]++; //换完之后的
            rightUp1[board[i]-i+n]++;
        }
    }

    public int randQueen(int n){
        int x = (int) (n *3.08);
        int i,j;

        //这个地方要将borad初始化为0
        for (i = 0; i < 2 * n; i++) {
           rightUp[i] = 0; rightUp1[i] = 0;
           rightDown[i] = 0; rightDown1[i] = 0;
        }

        for(i=0;i<n;i++)
	    {
	  	    board[i]=i;
	  	    rightUp[board[i]-i+n]++;  //这个地方为什么要减一个1
	  	    rightDown[board[i]+i]++;
        }
        //为什么一定要替换呢
        for(i=0,j=0;i<x && j<n;i++){
            int k = randPos(j, n); 
            //System.out.printf("J的数目:%d\n",j);
            changePosition(j, k, 1);  //变换位置
            if(rightUp1[board[j]-j+n]>1 || rightDown1[board[j]+j]>1){
                changePosition(j, k, 0);  //交换回来
            }   
            else{
                j++;
            }    
        }
        for(i=j;i<n;i++){
            int k = randPos(i, n); 
            changePosition(i, k, 2); 
        }
        //System.out.printf("解决的冲突数:%d\n",j);
        // init_conflict(n); 可以计算一下冲突的大小
        return j; 
    }

    boolean totalConficts(int i,int n){
        return (rightUp[board[i]-i+n]>1) || (rightDown[board[i]+i]>1);
    }

    public void searchNQueen(int n,int times){
        //试探次数
        for(int i=0;i<times;i++){
            //int search_times = 0; //这个就是充分利用了search times这个变量的
            long startTime = System.currentTimeMillis();    
            int k = randQueen(n); //后面还有k个皇后没有求解
            System.out.printf("init_search conflicts:%d\n",init_conflict(n));
            System.out.printf("init_search已经解决冲突的皇后数目:%d\n",k);
            boolean b;
            for(int j=k;j<n;j++){
                int c = 0;
                if(totalConficts(j, n)){
                    do{
                        int pos = randPos(0, n);
                        changePosition(j, pos, 2);  //左边对角线的都不变化了？？？
                        c++;
                        b=(totalConficts(pos,n)||totalConficts(j,n));
		    	        if(b) changePosition(j, pos, 2); 

				        if(c == 7000)   //7000次不行又初始化
				        {
                        k=randQueen(n);  
                        j = k;
                        break;		
                        }
				 }while(b);	 
                } 
            }
            long endTime = System.currentTimeMillis();    //获取开始时间
            System.out.println("程序运行时间：" + (endTime - startTime) + "ms");
            int score = init_conflict(n);
            System.out.printf("冲突数conflicts:%d\n",score);
            /*for(int j=0;j<n;j++){
                System.out.printf("%d,",board[j]);
            }*/
        }
    }

    public static void main(String[] args){
        int n=1000000; 
        LocalSearchNqueen localSearchNqueen = new LocalSearchNqueen(n);
        //int k = localSearchNqueen.randPos(57,100);
        //int j = localSearchNqueen.randPos(57,100);
        //System.out.printf("随机数:------------------\n");
        localSearchNqueen.searchNQueen(n, 1); 
    }
}
