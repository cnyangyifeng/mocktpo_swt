package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import com.mocktpo.widget.DroppableAnswerComposite;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.dnd.*;

public class AnswerCompositeDropTargetSet {

    private DropTarget dt;

    public static AnswerCompositeDropTargetSet drop(final DroppableAnswerComposite c) {
        return new AnswerCompositeDropTargetSet(c);
    }

    private AnswerCompositeDropTargetSet(final DroppableAnswerComposite c) {

        dt = new DropTarget(c, DND.DROP_MOVE);
        dt.setTransfer(new Transfer[] { TextTransfer.getInstance() });

        dt.addDropListener(new DropTargetListener() {

            @Override
            public void dragEnter(DropTargetEvent e) {
            }

            @Override
            public void dragLeave(DropTargetEvent e) {
            }

            @Override
            public void dragOperationChanged(DropTargetEvent e) {
            }

            @Override
            public void dragOver(DropTargetEvent e) {
            }

            @Override
            public void drop(DropTargetEvent e) {
                String[] arr = StringUtils.split((String) e.data, MT.CHAR_SEMICOLON);
                c.setAnswer(Integer.valueOf(arr[0]));
                c.setText(arr[1]);
            }

            @Override
            public void dropAccept(DropTargetEvent e) {
            }
        });
    }
}
