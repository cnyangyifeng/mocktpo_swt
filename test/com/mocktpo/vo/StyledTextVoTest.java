package com.mocktpo.vo;

import java.util.List;

import org.eclipse.swt.SWT;
import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.mocktpo.util.ConfigUtils;

public class StyledTextVoTest {

    @Test
    public void testLoad() {
        StyledTextVo vo = ConfigUtils.load("test_intro.json", StyledTextVo.class);
        String vos = JSON.toJSONString(vo);

        System.out.println(vos);
    }

    @Test
    public void testSave() {
        StyledTextVo vo = ConfigUtils.load("test_intro.json", StyledTextVo.class);

        List<StyleRangeVo> srvs = vo.getStyles();

        StyleRangeVo srv = new StyleRangeVo();
        srv.setStart(10);
        srv.setLength(15);
        srv.setFontStyle(SWT.BOLD | SWT.ITALIC);

        srvs.add(srv);
        vo.setStyles(srvs);

        ConfigUtils.save("test_intro.json", vo);
    }
}
