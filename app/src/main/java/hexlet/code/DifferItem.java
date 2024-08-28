package hexlet.code;

import java.util.List;

public final class DifferItem {
    private String state;
    private List<Object> values;

    public DifferItem(String state, List<Object> values) {
        this.state = state;
        this.values = values;
    }

    public String getState() {
        return state;
    }

    public List<Object> getValues() {
        return values;
    }
}
