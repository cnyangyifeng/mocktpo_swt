package com.mocktpo.util.widgets;

import com.mocktpo.modules.test.widgets.DroppableAnswerComposite;
import com.mocktpo.util.constants.MT;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.dnd.*;

public class AnswerCompositeDropTargetSet {

    public static AnswerCompositeDropTargetSet drop(final DroppableAnswerComposite c) {
        return new AnswerCompositeDropTargetSet(c);
    }

    private AnswerCompositeDropTargetSet(final DroppableAnswerComposite c) {
        DropTarget dropTarget = new DropTarget(c, DND.DROP_MOVE);
        dropTarget.setTransfer(TextTransfer.getInstance());
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
