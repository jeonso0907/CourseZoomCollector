import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JavaSample {

    public static List<List<Integer>> permute(int[] nums) {

        List<List<Integer>> list = new ArrayList<>();
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            set.add(nums[i]);
            List<Integer> l = new ArrayList<>();
            l.add(nums[i]);
            int j = 0;
            while (l.size() != nums.length || j == nums.length - 1) {
                if (j == i || set.contains(nums[j])) {
                    j++;
                } else {
                    l.add(nums[j]);
                    set.add(nums[j]);
                    j++;
                }
            }
            list.add(l);
        }

        return list;
    }

    public static void main(String[] args) {

        int[] i = {1, 2, 3};
        List<List<Integer>> l = permute(i);

        for (List<Integer> ll : l) {
            for (int ii : ll) {
                System.out.print(ii + " ");
            }
            System.out.println();
        }

        // System.out.println("Hello");
    }

}