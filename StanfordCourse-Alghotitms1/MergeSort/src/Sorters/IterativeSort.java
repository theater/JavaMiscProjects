package Sorters;
import java.util.List;

public class IterativeSort implements Sorter {
    @Override
    public void sort(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = i; j < list.size(); j++) {
                if (list.get(i) > list.get(j)) {
                    swap(list, i, j);
                }
            }
        }
    }

    private void swap(List<Integer> list, int i, int j) {
        Integer temp;
        temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }
}
