package de.skambath.malte.swimrecords.model;

/**
 * Represents a state with a name and ID.
 */
public class State {

    private int id;
    private String name;

    /**
     * Constructor for State.
     * @param id The unique identifier of the state.
     * @param name The name of the state.
     */
    public State(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Gets the state ID.
     * @return The state ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the state ID.
     * @param id The state ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the state name.
     * @return The state name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the state name.
     * @param name The state name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns a string representation of the state.
     * @return A string representation of the state.
     */
    @Override
    public String toString() {
        return "State{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
