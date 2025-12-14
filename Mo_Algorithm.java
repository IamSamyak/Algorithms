import java.util.*;
import java.util.Arrays;

class Main {
    static int[] computeBlocks(int arr[]){
        int n = arr.length;
        int sqrt = (int) Math.sqrt(n);
        
        int blocks[] = new int[sqrt + 1];
        
        int blockIdx = -1;
        for(int i = 0; i < n; i++){
            if(i % sqrt == 0) blockIdx++;
            blocks[blockIdx] += arr[i];
        }
        return blocks;
    }
    
    static int query(int blocks[], int arr[], int left, int right, int sqrt){
        int result = 0;
        
        //left part
        while(left != 0 && left < right && left % sqrt != 0){
            result += arr[left];
            left++;
        }
        
        // middle part
        while(left + sqrt <= right){
            result += blocks[left / sqrt];
            left += sqrt;
        }
        
        while(left <= right){
            result += arr[left];
            left++;
        }
        
        return result;
    }
    
    static void update(int blocks[], int arr[], int i, int val, int sqrt){
        int blockIdx = i / sqrt;
        blocks[blockIdx] += (val - arr[i]);
        arr[i] = val;
    }
    
    public static void main(String[] args) {
        int arr[] = {1, 3, 5, 2, 7, 6 ,3, 1, 4 ,8};
        
        int n = arr.length;
        int sqrt = (int) Math.sqrt(n);
        
        int blocks[] = computeBlocks(arr); 
        int queryOutput = query(blocks, arr, 2, 7, 3);
        System.out.println("queryOutput: "+ queryOutput);
        System.out.println(Arrays.toString(blocks));
    }
}
