package com.mocktpo.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import com.mocktpo.vo.StyleRangeVo;

public class StyleRangeUtils {

    private StyleRangeUtils() {
    }

    public static void decorate(StyledText st, List<StyleRangeVo> srvs) {
        List<StyleRange> srs = new ArrayList<StyleRange>();

        for (StyleRangeVo srv : srvs) {
            StyleRange sr = new StyleRange();
            sr.start = srv.getStart();
            sr.length = srv.getLength();
            sr.fontStyle = srv.getFontStyle();
            if (StringUtils.isNotEmpty(srv.getForeground())) {
                sr.foreground = ColorUtils.fromHex(st.getDisplay(), srv.getForeground());
            }
            if (StringUtils.isNotEmpty(srv.getBackground())) {
                sr.background = ColorUtils.fromHex(st.getDisplay(), srv.getBackground());
            }
            sr.underline = srv.isUnderline();
            if (StringUtils.isNotEmpty(srv.getImage())) {
                Image image = ImageUtils.load(st.getDisplay(), srv.getImage());
                if (null != image) {
                    sr.data = image;
                    Rectangle bounds = image.getBounds();
                    sr.metrics = new GlyphMetrics(bounds.height, 0, bounds.width);
                }
            }
            srs.add(sr);
        }

        st.setStyleRanges(srs.toArray(new StyleRange[0]));
    }
}
