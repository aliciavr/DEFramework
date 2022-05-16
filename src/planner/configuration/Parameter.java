package planner.configuration;

import java.util.ArrayList;

/**
 * Parameter is a class for supporting the base scheme of a parametrizable
 * object in DEFramework with a set of basic characteristics and possible
 * values.
 *
 * @author <a href="mailto:avr00036@red.ujaen.es">Alicia Vazquez Ramos</a>
 * @param <T> a value parametrizable in DEFramework.
 * @version 1.0
 * @since 1.0
 */
public class Parameter<T> {

    /**
     * Name of the Parameter.
     */
    private String name;

    /**
     * Description of the Parameter.
     */
    private String description;

    /**
     * Configured values for the Parameter.
     */
    private ArrayList<T> values;

    /**
     * Constructor given the name and the description.
     *
     * @param name name of the Parameter.
     * @param description description of the Parameter.
     */
    public Parameter(String name, String description) {
        this.name = name;
        this.description = description;
        this.values = new ArrayList<>();
    }

    /**
     * Constructor given the name, the description and a list of configured
     * values.
     *
     * @param name name of the Parameter.
     * @param description description of the Parameter.
     * @param values list of the configured values.
     */
    public Parameter(String name, String description, ArrayList<T> values) {
        this.name = name;
        this.description = description;
        this.values = values;
    }

    /**
     * Gets the name of the Parameter.
     *
     * @return the name of the Parameter.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the description of the Parameter.
     *
     * @return the description of the Parameter.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets the list of configured values for the Parameter.
     *
     * @return the list of configured values for the Parameter.
     */
    public ArrayList<T> getValues() {
        return values;
    }

    /**
     * Gets the number of configured values for the Parameter.
     *
     * @return the number of configured values for the Parameter.
     */
    public int getNumValues() {
        return values.size();
    }

    /**
     * Gets one of the configured values for the Parameter at position i.
     *
     * @param i position of required value.
     * @return a value at position i of the list of configured values.
     */
    public T getValue(int i) {
        return values.get(i);
    }

    /**
     * Sets a name for the Parameter.
     *
     * @param name the new name.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets a desctiption for the Parameter.
     *
     * @param description the new description.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets a list with a set of configured values for the Parameter.
     *
     * @param values new list of configured values.
     */
    public void setValues(ArrayList<T> values) {
        this.values = values;
    }

    /**
     * Sets a new value at position i in the current list of configured values.
     *
     * @param i position for the new value.
     * @param value value to be added.
     */
    public void setValue(int i, T value) {
        values.set(i, value);
    }

    /**
     * Adds a new value at the end of the current list of configured values.
     *
     * @param value value to be added.
     */
    public void addValue(T value) {
        values.add(value);
    }
}
