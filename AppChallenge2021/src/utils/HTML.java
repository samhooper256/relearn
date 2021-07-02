package utils;

public final class HTML {
	
	private HTML() {
		
	}
	
	private static final String
			OPENING_HTML_TAG = "<html>",
			CLOSING_HTML_TAG = "</html>",
			OPENING_BODY_TAG = "<body>",
			CLOSING_BODY_TAG = "</body>",
			OPENING_MATH_TAG = "<math>",
			CLOSING_MATH_TAG = "</math>";
	
	public static String ensureMathTags(String html) {
		return ensureTags(html, OPENING_MATH_TAG, CLOSING_MATH_TAG);
	}
	
	public static String ensureHTMLTags(String html) {
		return ensureTags(html, OPENING_HTML_TAG, CLOSING_HTML_TAG);
	}
	
	public static String ensureBodyTags(String html) {
		return ensureTags(html, OPENING_BODY_TAG, CLOSING_BODY_TAG);
	}
	
	private static String ensureTags(String html, String openingTag, String closingTag) {
		if(!html.startsWith(openingTag))
			html = openingTag + html;
		if(!html.endsWith(closingTag))
			html = html + closingTag;
		return html;
	}
	
}
