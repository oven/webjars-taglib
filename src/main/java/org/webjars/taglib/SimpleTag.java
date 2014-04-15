package org.webjars.taglib;

import org.webjars.WebJarAssetLocator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Base class for simple tags that output one html tag for a single WebJars asset
 *
 * @author Ove Gram Nipen
 */
public abstract class SimpleTag extends SimpleTagSupport {
    protected String webjar, path;
    protected WebJarAssetLocator locator = new WebJarAssetLocator();

    protected JspWriter out() {
        return getJspContext().getOut();
    }

    protected String getContextPath() {
        PageContext context = (PageContext) getJspContext();
        HttpServletRequest request = (HttpServletRequest) context.getRequest();
        return request.getContextPath();
    }

    protected String getFullPath() {
        String result = null == webjar ? locator.getFullPath(path) : locator.getFullPath(webjar, path);
        result = result.substring("META-INF/resources/".length());
        return result;
    }

    /**
     * The path we're looking for, <i>e.g.</i> {@literal bootstrap.css}.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Restrict search to this webjar, <i>e.g.</i> {@literal bootstrap}. Useful when resources have the same name
     * in several webjars.
     */
    public void setWebjar(String webjar) {
        this.webjar = webjar;
    }


    @Override
    public void doTag() throws JspException, IOException {
        if (path == null) throw new JspException("Path must be set");
        doTagInternal();
    }

    protected abstract void doTagInternal() throws JspException, IOException;
}
