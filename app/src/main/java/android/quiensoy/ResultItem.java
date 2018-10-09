package android.quiensoy;

class ResultItem {
	private String text;
	private Boolean isCorrect;
	
	String getText() {
		return text;
	}
	void setText(String text) {
		this.text = text;
	}
	Boolean getIsCorrect() {
		return isCorrect;
	}
	void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
}
