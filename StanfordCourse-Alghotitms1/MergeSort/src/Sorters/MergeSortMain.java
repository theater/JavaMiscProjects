package Sorters;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MergeSortMain {

    private static List<Integer> initializeArray(int size) {
        List<Integer> result = new ArrayList<>(size);
        Random rand = new Random(System.currentTimeMillis());
        for (int i = 0; i < size; i++) {
            result.add(rand.nextInt(20000));
        }
        return result;
    }

    public static void main(String... args) {
        int size = 25000;
        List<Integer> list1 = initializeArray(size);
        List<Integer> list2 = initializeArray(size);
        List<Integer> list3 = initializeArray(size);
        List<Integer> list4 = initializeArray(size);

        // printList(list);
        long startTime = System.currentTimeMillis();
        Sorter mergeSort = new MergeSort();
        mergeSort.sort(list1);
        long endTime = System.currentTimeMillis();
        System.out.format("Took me %d ms to complete merge sort with %d size\n", endTime - startTime, size);

        // printList(list2);
        startTime = System.currentTimeMillis();
        Sorter sort2 = new IterativeSort();
        sort2.sort(list2);
        endTime = System.currentTimeMillis();
        System.out.format("Took me %d ms to complete iterative sort with %d size\n", endTime - startTime, size);

        startTime = System.currentTimeMillis();
        list3 = list3.stream().sorted().collect(Collectors.toList());
        endTime = System.currentTimeMillis();
        System.out.format("Took me %d ms to complete stream API single thread sort with %d size\n", endTime - startTime,
                size);

        startTime = System.currentTimeMillis();
        list4 = list4.parallelStream().sorted().collect(Collectors.toList());
        endTime = System.currentTimeMillis();
        System.out.format("Took me %d ms to complete stream API parallel sort with %d size\n", endTime - startTime,
                size);
        // printList(list2);
    }

    private static void printList(List<Integer> list) {
        list.forEach(el -> System.out.println(el + " "));
        System.out.println();
    }

}
