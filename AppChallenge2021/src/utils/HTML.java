package utils;

public final class HTML {
	
	private HTML() {
		
	}
	
	private static final String
			HTML_TAG = "html",
			BODY_TAG = "body",
			MATH_TAG = "math",
			DIV_TAG = "div";
	
	public static String ensureMathTags(String html) {
		return ensureTags(html, MATH_TAG);
	}
	
	public static String ensureHTMLTags(String html) {
		return ensureTags(html, HTML_TAG);
	}
	
	public static String ensureBodyTags(String html) {
		return ensureTags(html, BODY_TAG);
	}
	
	public static String ensureDivTags(String html) {
		return ensureTags(html, DIV_TAG);
	}
	
	public static String ensureTags(String html, String tag) {
		return ensureTags(html, String.format("<%s>", tag), String.format("</%s>", tag));
	}
	
	private static String ensureTags(String html, String openingTag, String closingTag) {
		if(!html.startsWith(openingTag))
			html = openingTag + html;
		if(!html.endsWith(closingTag))
			html = html + closingTag;
		return html;
	}
	
}
