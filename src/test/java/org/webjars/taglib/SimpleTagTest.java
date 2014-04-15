package org.webjars.taglib;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.webjars.MultipleMatchesException;
import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import java.io.IOException;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SimpleTagTest {
    @InjectMocks
    private SimpleTag simpleTag = new SimpleTag() {
        @Override
        protected void doTagInternal() throws JspException, IOException {
            getFullPath();
        }
    };

    @Mock
    private WebJarAssetLocator mockAssetLocator;

    @Mock
    private JspWriter mockJspWriter;

    @Mock
    private PageContext mockPageContext;

    @Mock
    private HttpServletRequest mockRequest;

    @Test(expected = MultipleMatchesException.class)
    public void does_not_mask_multiple_matches_exception() throws Exception {
        when(mockAssetLocator.getFullPath("foo.js")).thenThrow(MultipleMatchesException.class);
        simpleTag.setPath("foo.js");
        simpleTag.doTag();
    }
}
