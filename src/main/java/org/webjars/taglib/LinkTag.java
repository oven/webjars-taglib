package org.webjars.taglib;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Outputs a single link tag to a webjar resource, including the request context path.
 * <p>
 * Example output:
 * {@code
 * <link rel='stylesheet' type='text/css' href='/myappcontext/webjars/tablesorter/2.15.5/css/theme.default.css' />
 * }
 * </p>
 *
 * @author Ove Gram Nipen
 */
@SuppressWarnings("serial")
public class LinkTag extends SimpleTag {

    private String rel = "stylesheet";
    private String type = "text/css";
    private String media;

    @Override
    public void doTagInternal() throws JspException, IOException {
        StringBuffer sb = new StringBuffer();
        sb.append("<link ");
        sb.append(String.format("rel='%s' ", rel));
        if (null != media) sb.append(String.format("media='%s' ", media));
        sb.append(String.format("type='%s' ", type));
        sb.append(String.format("href='%s/%s'/>", getContextPath(), getFullPath()));
        out().print(sb.toString());
    }

    /**
     * Set the {@code rel} attribute in the generated link tag. Defaults to {@code rel='stylesheet'}.
     *
     * @param rel the value of the {@code rel} attribute in the generated link tag.
     */
    public void setRel(String rel) {
        this.rel = rel;
    }

    /**
     * Set the {@code type} attribute in the generated link tag. Defaults to {@code type='text/css'}.
     *
     * @param type the value of the {@code type} attribute in the generated link tag.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Set the {@code media} attribute in the generated link tag. Not included by default.
     *
     * @param media the value of the {@code media} attribute in the generated link tag.
     */
    public void setMedia(String media) {
        this.media = media;
    }
}
