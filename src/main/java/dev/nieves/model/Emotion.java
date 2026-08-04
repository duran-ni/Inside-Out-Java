package dev.nieves.model;

/**
 * Enumerado que representa las emociones disponibles para un momento vivido.
 * Se guarda el nombre visible en español para mostrarlo directamente en consola.
 */
public enum Emotion {
    /** Alegría. */
    JOY("Alegría"),
    /** Tristeza. */
    SADNESS("Tristeza"),
    /** Ira. */
    ANGER("Ira"),
    /** Asco. */
    DISGUST("Asco"),
    /** Miedo. */
    FEAR("Miedo"),
    /** Ansiedad. */
    ANXIETY("Ansiedad"),
    /** Envidia. */
    ENVY("Envidia"),
    /** Vergüenza. */
    SHAME("Vergüenza"),
    /** Aburrimiento. */
    BOREDOM("Aburrimiento"),
    /** Nostalgia. */
    NOSTALGIA("Nostalgia");

    private final String displayName;

    Emotion(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
