package com.mocktpo.util.widgets;

import com.mocktpo.util.constants.MT;
import com.mocktpo.widgets.DroppableAnswerComposite;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.dnd.*;

public class AnswerCompositeDropTargetSet {

    public static AnswerCompositeDropTargetSet drop(final DroppableAnswerComposite c) {
        return new AnswerCompositeDropTargetSet(c);
    }

    private AnswerCompositeDropTargetSet(final DroppableAnswerComposite c) {
        DropTarget dropTarget = new DropTarget(c, DND.DROP_MOVE);
        dropTarget.setTransfer(new Transfer[]{TextTransfer.getInstance()});
        dropTarget.addDropListener(new DropTargetAdapter() {
            @Override
            public void drop(DropTargetEvent e) {
                String[] arr = StringUtils.split((String) e.data, MT.CHAR_SEMICOLON);
                c.setAnswer(Integer.valueOf(arr[0]));
                c.setText(arr[1]);
            }
        });
    }
}
