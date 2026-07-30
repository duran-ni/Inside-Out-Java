package dev.nieves.model;

/**
 * Enumerado que representa las emociones disponibles para un momento vivido.
 * Se guarda el nombre visible en español para mostrarlo directamente en consola.
 */
public enum Emotion {
    JOY("Alegría"),
    SADNESS("Tristeza"),
    ANGER("Ira"),
    DISGUST("Asco"),
    FEAR("Miedo"),
    ANXIETY("Ansiedad"),
    ENVY("Envidia"),
    SHAME("Vergüenza"),
    BOREDOM("Aburrimiento"),
    NOSTALGIA("Nostalgia");

    private final String displayName;

    Emotion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}