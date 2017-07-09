package com.mocktpo.util.widgets;

import com.mocktpo.util.ImageUtils;
import com.mocktpo.util.ResourceManager;
import com.mocktpo.vo.StyleRangeVo;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class StyleRangeUtils {

    private StyleRangeUtils() {
    }

    public static void decorate(StyledText st, List<StyleRangeVo> srvs) {
        if (srvs == null) {
            return;
        }
        List<StyleRange> srs = new ArrayList<StyleRange>();
        for (StyleRangeVo srv : srvs) {
            StyleRange sr = new StyleRange();
            sr.start = srv.getStart();
            sr.length = srv.getLength();
            /* Font Style */
            if (srv.getFontStyle() != 0) {
                sr.fontStyle = srv.getFontStyle();
            }
            /* Foreground */
            if (srv.getForeground() != 0) {
                sr.foreground = ResourceManager.getColor(srv.getForeground());
            }
            /* Background */
            if (srv.getBackground() != 0) {
                sr.background = ResourceManager.getColor(srv.getBackground());
            }
            /* Underline */
            if (srv.isUnderline()) {
                sr.underline = srv.isUnderline();
            }
            /* Image */
            if (StringUtils.isNotEmpty(srv.getImage())) {
                Image image = ImageUtils.load(st.getDisplay(), srv.getImage());
                sr.data = image;
                Rectangle bounds = image.getBounds();
                sr.metrics = new GlyphMetrics(bounds.height, 0, bounds.width);
            }
            srs.add(sr);
        }
        st.setStyleRanges(srs.toArray(new StyleRange[srs.size()]));
    }
}
