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
public class LinkTagTest {

    @InjectMocks
    private LinkTag linkTag = new LinkTag();

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
        linkTag.doTag();
    }

    @Test
    public void can_get_css_with_webjar_and_path() throws Exception {
        when(mockAssetLocator.getFullPath("tablesorter", "theme.default.css")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/css/theme.default.css");
        linkTag.setPath("theme.default.css");
        linkTag.setWebjar("tablesorter");
        linkTag.doTag();
        verifyOutContains("<link rel='stylesheet' type='text/css' href='/mywebappcontext/webjars/tablesorter/2.15.5/css/theme.default.css'/>");
    }

    @Test
    public void can_get_css_with_only_path() throws Exception {
        when(mockAssetLocator.getFullPath("theme.default.css")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/css/theme.default.css");
        linkTag.setPath("theme.default.css");
        linkTag.doTag();
        verifyOutContains("<link rel='stylesheet' type='text/css' href='/mywebappcontext/webjars/tablesorter/2.15.5/css/theme.default.css'/>");
    }

    @Test
    public void can_override_rel() throws Exception {
        when(mockAssetLocator.getFullPath("theme.default.css")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/css/theme.default.css");
        linkTag.setPath("theme.default.css");
        linkTag.setRel("alternate stylesheet");
        linkTag.doTag();
        verifyOutContains("<link rel='alternate stylesheet' type='text/css' href='/mywebappcontext/webjars/tablesorter/2.15.5/css/theme.default.css'/>");
    }

    @Test
    public void can_override_media() throws Exception {
        when(mockAssetLocator.getFullPath("theme.default.css")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/css/theme.default.css");
        linkTag.setPath("theme.default.css");
        linkTag.setMedia("print");
        linkTag.doTag();
        verifyOutContains("<link rel='stylesheet' media='print' type='text/css' href='/mywebappcontext/webjars/tablesorter/2.15.5/css/theme.default.css'/>");
    }

    @Test
    public void can_override_type() throws Exception {
        when(mockAssetLocator.getFullPath("theme.default.xsl")).thenReturn(
                "META-INF/resources/webjars/tablesorter/2.15.5/css/theme.default.xsl");
        linkTag.setPath("theme.default.xsl");
        linkTag.setType("text/xsl");
        linkTag.doTag();
        verifyOutContains("<link rel='stylesheet' type='text/xsl' href='/mywebappcontext/webjars/tablesorter/2.15.5/css/theme.default.xsl'/>");
    }

    private void verifyOutContains(String expected) throws JspException, IOException {
        verify(mockJspWriter).print(expected);
    }
}
