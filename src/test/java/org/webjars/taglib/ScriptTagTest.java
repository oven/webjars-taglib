package org.webjars.taglib;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ScriptTagTest {

    @InjectMocks
    private ScriptTag scriptTag = new ScriptTag();

    @Mock
    private WebJarAssetLocator mockAssetLocator;

    @Mock
    private JspWriter mockJspWriter;

    @Mock
    private PageContext mockPageContext;

    @Mock
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() throws Exception {
        when(mockPageContext.getOut()).thenReturn(mockJspWriter);
        when(mockPageContext.getRequest()).thenReturn(mockRequest);
        when(mockRequest.getContextPath()).thenReturn("/mywebappcontext");
    }

    @Test(expected = JspException.class)
    public void path_required() throws JspException, IOException {
        scriptTag.doTag();
    }

    @Test
    public void can_get_script_with_webjar_and_path() throws Exception {
        when(mockAssetLocator.getFullPath("tablesorter", "jquery.tablesorter.js")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/js/jquery.tablesorter.js");
        scriptTag.setPath("jquery.tablesorter.js");
        scriptTag.setWebjar("tablesorter");
        scriptTag.doTag();
        verifyOutContains("<script src='/mywebappcontext/webjars/tablesorter/2.15.5/js/jquery.tablesorter.js'></script>");
    }

    @Test
    public void can_get_script_with_only_path() throws Exception {
        when(mockAssetLocator.getFullPath("jquery.tablesorter.js")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/js/jquery.tablesorter.js");
        scriptTag.setPath("jquery.tablesorter.js");
        scriptTag.doTag();
        verifyOutContains("<script src='/mywebappcontext/webjars/tablesorter/2.15.5/js/jquery.tablesorter.js'></script>");
    }

    private void verifyOutContains(String expected) throws JspException, IOException {
        verify(mockJspWriter).print(expected);
    }
}
