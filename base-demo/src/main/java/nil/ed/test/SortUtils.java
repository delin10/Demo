package nil.ed.test;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.TypeConverterSupport;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lidelin.
 */
public class SortUtils {

    public static void sortSeq(List<SortableBean> beans, SortableBean currentBean, int maxOrder, int place) {
        place = Math.min(place, maxOrder);
        if (place <= 0 || place == currentBean.getSort()) {
            return;
        }
        int min = Math.min(currentBean.getSort() == 0 ? place : currentBean.getSort(), place);
        int cursor = currentBean.getSort() != 0 && currentBean.getSort() < place ? min : min + 1;

        for (SortableBean bean : beans) {
            if (bean == currentBean) {
                bean.setSort(place);
            } else {
                bean.setSort(cursor++);
            }
        }
    }

    interface SortableBean {
        /**
         * 设置当前排序位置.
         * @param sort 排序位置.
         */
        void setSort(int sort);

        /**
         * 获取排序位置.
         * @return 排序位置.
         */
        int getSort();
    }

    static class TestSortableBean implements SortableBean {
        @Getter @Setter private long id;
        @Getter @Setter private int sort;
    }

    public static void main(String[] args) {
        TestSortableBean bean1 = new TestSortableBean();
        bean1.setId(1L);
        bean1.setSort(1);
        TestSortableBean bean2 = new TestSortableBean();
        bean2.setId(2L);
        bean2.setSort(2);
        TestSortableBean bean3 = new TestSortableBean();
        bean3.setId(3L);
        bean3.setSort(3);
        TestSortableBean bean4 = new TestSortableBean();
        bean4.setId(4L);
        bean4.setSort(4);
        TestSortableBean bean5 = new TestSortableBean();
        bean5.setId(5L);
        bean5.setSort(5);

        List<SortableBean> beanList = Arrays.asList(bean1, bean2, bean3, bean4, bean5);
        sortSeq(beanList, bean1, 5, 5);
        List<Integer> sortList1 = beanList.stream().map(SortableBean::getSort).collect(Collectors.toList());
        assert Arrays.equals(sortList1.toArray(), Arrays.asList(4, 1, 2, 3, 5).toArray());
        sortSeq(beanList, bean1, 5, 1);
        bean5.setSort(0);
        sortSeq(beanList, bean5, 5, 1);
        System.out.println(Integer.MAX_VALUE);
    }

}
