package nil.ed.test.easyexcel;

import java.util.List;

import com.alibaba.excel.event.AnalysisEventListener;
import com.google.common.collect.Lists;

/**
 * @author niexiao
 * @since 2021/1/31 3:28 下午
 */
public abstract class BaseExcelListener<T> extends AnalysisEventListener<T> {

    /**
     * 批量处理的数量
     */
    protected static final int BATCH_COUNT = 1000;

    /**
     * 传入的记录
     */
    protected List<T> list = Lists.newArrayList();

}
