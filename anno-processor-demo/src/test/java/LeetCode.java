import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

public class LeetCode {
    public static void main(String[] args) {

    }
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (o1, o2) -> Integer.compare(o1[1],o2[1]));
        Method method;
        method.setA
        int ans=0;
        int[] prev = null;
        for (int i=0;i<intervals.length;++i){
            if (i==0){
                prev=intervals[i];
            }else{
                if (intervals[i][1]>prev[1]&&intervals[i][0]<prev[1]){
                    ++ans;
                }else{
                    ++ans;
                }
            }
        }
        return ans;
    }
}
