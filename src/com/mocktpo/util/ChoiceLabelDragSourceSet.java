package com.mocktpo.util;

import com.mocktpo.util.constants.MT;
import org.eclipse.swt.dnd.*;
import org.eclipse.swt.widgets.Label;

public class ChoiceLabelDragSourceSet {

    private DragSource ds;

    public static ChoiceLabelDragSourceSet drag(final Label c) {
        return new ChoiceLabelDragSourceSet(c);
    }

    private ChoiceLabelDragSourceSet(final Label c) {

        ds = new DragSource(c, DND.DROP_MOVE);
        ds.setTransfer(new Transfer[] { TextTransfer.getInstance() });

        ds.addDragListener(new DragSourceListener() {

            @Override
            public void dragStart(DragSourceEvent e) {
                if (0 == c.getText().length()) {
                    e.doit = false;
                }
            }

            @Override
            public void dragSetData(DragSourceEvent e) {
                if (TextTransfer.getInstance().isSupportedType(e.dataType)) {
                    e.data = c.getData(MT.KEY_CHOICE).toString() + MT.CHAR_SEMICOLON + c.getText();
                }
            }

            @Override
            public void dragFinished(DragSourceEvent e) {
                if (e.detail == DND.DROP_MOVE) {
                    c.setText("");
                }
            }
        });
    }
}
