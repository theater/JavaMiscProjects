package Sorters;
import java.util.ArrayList;
import java.util.List;

public class MergeSort implements Sorter {

    @Override
    public void sort(List<Integer> listToSort) {
        int size = listToSort.size();
        if (size < 2) {
            return;
        }

        List<Integer> leftSubList = new ArrayList<>(listToSort.subList(0, size / 2));
        List<Integer> rightSubList = new ArrayList<>(listToSort.subList(size / 2, size));
        sort(leftSubList);
        sort(rightSubList);

        merge(listToSort, leftSubList, rightSubList);
    }

    private void merge(List<Integer> listToSort, List<Integer> leftSubList, List<Integer> rightSubList) {
        int i = 0, j = 0, k = 0;
        for (; i < leftSubList.size() && j < rightSubList.size(); k++) {
            if (leftSubList.get(i) <= rightSubList.get(j)) {
                listToSort.set(k, leftSubList.get(i++));
            } else {
                listToSort.set(k, rightSubList.get(j++));
            }
        }

        for (; i < leftSubList.size(); i++, k++) {
            listToSort.set(k, leftSubList.get(i));
        }

        for (; j < rightSubList.size(); j++, k++) {
            listToSort.set(k, rightSubList.get(j));
        }
    }
}
