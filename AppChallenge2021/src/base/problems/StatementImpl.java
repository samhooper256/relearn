package base.problems;

import java.util.Objects;

final class StatementImpl implements Statement {

	private final String html;
	
	StatementImpl(String html) {
		this.html = html;
	}
	
	@Override
	public String html() {
		return html;
	}

	@Override
	public int hashCode() {
		return Objects.hash(html);
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		StatementImpl other = (StatementImpl) obj;
		return Objects.equals(html, other.html);
	}
	
}
