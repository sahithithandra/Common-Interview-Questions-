import java.io.*;
import java.util.Arrays;

/* Count number of inversions in an integer array A where inversion is
 * a pair p(A[i], A[j]) in which i<j and A[i] > A[j]
 */

public class CountNumberOfInversions {
    
    public static void main(String[] args) throws IOException{
        
        BufferedReader br = null;
        int arraySize = 0;
        int[] inputArray = {};
        int i =0;
        
        try {
            
            String sCurrentLine;
            
            br = new BufferedReader(new FileReader("testfile.txt"));
            
            /* first line of the file contains the number of elements*/
            if((sCurrentLine = br.readLine()) != null)
            {
                arraySize = Integer.parseInt(sCurrentLine);
                inputArray = new int[arraySize];
            }
            while ((sCurrentLine = br.readLine()) != null) {
                inputArray[i] = Integer.parseInt(sCurrentLine);
                i++;
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        System.out.println("value of i" + i);
        long numOfInversions = CountInversions(inputArray);
        System.out.println("Number of Inversions"+" "+numOfInversions);

    }
    
    private static long CountInversions(int[] A) {
        /* Base case, if length is 1, there are no inversions */
        if(A.length == 1)
            return 0;
        long left_count =0;
        long right_count=0;
        long split_count=0;
        if (A.length > 1) {
            int q = A.length/2;
            
            int[] leftArray = Arrays.copyOfRange(A, 0, q);
            int[] rightArray = Arrays.copyOfRange(A,q,A.length);
            /* Count number of inversions in left and right sub arrays */
            left_count = CountInversions(leftArray);
            right_count = CountInversions(rightArray);
            /* Count number of inversions while merging the sub arrays */
            split_count = sortAndCountSplitInversions(A,leftArray,rightArray);
            
        }
        return left_count + right_count + split_count;

    }
    
    static long sortAndCountSplitInversions(int[] a, int[] left_array, int[] right_array) {
        
        int left_length = left_array.length;
        int right_length = right_array.length;
        int totElem = left_length + right_length;
        int counter =0;
        int index,index_left,index_right;     
        index = index_left = index_right = 0;

        while ( index < totElem) {
            if ((index_left < left_array.length) && (index_right < right_array.length)) {
                if (left_array[index_left] < right_array[index_right]) {
                    a[index] = left_array[index_left];
                    index++;
                    index_left++;
                }
                else {
                    a[index] = right_array[index_right];
                    index++;
                    index_right++;
                    /* number of elements remaining in the left array are
                     * the number of split inversions for the element right_array[index_right]
                     */
                    counter = counter + (left_length-index_left);
                }
            }
            else {
                if (index_left >= left_length) {
                    /* copy remaining elements from right sub array */
                    while (index_right < right_length) {
                        a[index] = right_array[index_right];
                        index++;
                        index_right++;
                    }
                }
                if (index_right >= right_length) {
                    /* copy remaining elements from left sub array */
                    while (index_left < left_length) {
                        a[index] = left_array[index_left];
                        index_left++;
                        index++;
                    }
                }
            }
        }
        return counter;
    }
    
}


