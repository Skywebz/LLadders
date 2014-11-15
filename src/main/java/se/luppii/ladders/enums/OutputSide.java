package se.luppii.ladders.enums;

public enum OutputSide {
	UPDOWN("Top/Bottom"), LEFT("Left"), RIGHT("Right");
	
	private String outputSide;
	
	OutputSide(String side) {
		this.outputSide = side;
	}
	
	public OutputSide next() {
		return this.ordinal() < OutputSide.values().length - 1 
				? OutputSide.values()[this.ordinal() + 1] 
				: OutputSide.UPDOWN;
	}
	
	public String toString() {
		return this.outputSide;
	}
}
