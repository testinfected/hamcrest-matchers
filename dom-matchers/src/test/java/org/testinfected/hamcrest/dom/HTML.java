package org.testinfected.hamcrest.dom;

import org.w3c.dom.Element;

public class HTML {

    private HTML() {}

    public static Element html(String dom) {
        return Documents.toElement("<html><body>" + dom + "</body></html>");
    }
}