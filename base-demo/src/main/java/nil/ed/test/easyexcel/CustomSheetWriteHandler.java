package nil.ed.test.easyexcel;

import java.util.Collections;
import java.util.LinkedList;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.write.handler.AbstractSheetWriteHandler;
import com.alibaba.excel.write.handler.AbstractWorkbookWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;

/**
 * @author lidelin.
 */
public class CustomSheetWriteHandler extends AbstractSheetWriteHandler {

    @Override
    public void beforeSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        super.beforeSheetCreate(writeWorkbookHolder, writeSheetHolder);
        System.out.println(writeSheetHolder.getExcelWriteHeadProperty().getHeadMap());
        for (Head head : writeSheetHolder.getExcelWriteHeadProperty().getHeadMap().values()) {
            System.out.println(head.getHeadNameList());
            head.setHeadNameList(Collections.singletonList("asda" + head.getHeadNameList().get(0)));
        }
    }
//
//    @Override
//    public void afterWorkbookCreate(WriteWorkbookHolder writeWorkbookHolder) {
//        for (Head head : writeWorkbookHolder.getExcelWriteHeadProperty().getHeadMap().values()) {
//            System.out.println(head.getHeadNameList());
//            head.setHeadNameList(Collections.singletonList("asda" + head.getHeadNameList().get(0)));
//        }
//        super.afterWorkbookCreate(writeWorkbookHolder);
//    }
}
