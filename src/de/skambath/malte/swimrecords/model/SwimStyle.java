package de.skambath.malte.swimrecords.model;

public enum SwimStyle {
	Fly("S"), Backstroke("R"), Breaststroke("B"), Freestyle("F"), Medley("L");
	
	private final String strokeId;

    SwimStyle(String strokeId) {
        this.strokeId = strokeId;
    }

    public String getStrokeId() {
        return strokeId;
    }

    public static SwimStyle fromStrokeId(String strokeId) {
        for (SwimStyle style : values()) {
            if (style.strokeId.equals(strokeId)) {
                return style;
            }
        }
        throw new IllegalArgumentException("Invalid StrokeId: " + strokeId);
    }
    
    @Override
    public String toString() {
        return strokeId;
    }
}
