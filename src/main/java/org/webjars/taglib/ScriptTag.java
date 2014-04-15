package org.webjars.taglib;

import javax.servlet.jsp.JspException;
import java.io.IOException;

/**
 * Outputs a single script tag.
 * <p>
 * Example output:
 * {@code
 * <script src='/myappcontext/webjars/tablesorter/2.15.5/js/tablesorter.min.js' />
 * }
 * </p>
 *
 * @author Ove Gram Nipen
 */
@SuppressWarnings("serial")
public class ScriptTag extends SimpleTag {
    @Override
    public void doTagInternal() throws JspException, IOException {
        out().print(String.format("<script src='%s/%s'></script>", getContextPath(), getFullPath()));
    }
}
