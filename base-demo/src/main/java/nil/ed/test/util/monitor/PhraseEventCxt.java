package nil.ed.test.util.monitor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.google.common.collect.Maps;
import lombok.Getter;
import nil.ed.test.util.AlarmLevel;

/**
 * @author lidelin.
 */
public class PhraseEventCxt {

    @Getter
    private final Map<String, Map<String, String>> all = new LinkedHashMap<>();
    private Map<String, String> current;

    public PhraseEventCxt switchTo(String phrase) {
        all.computeIfAbsent(phrase, k -> Maps.newLinkedHashMap());
        current = all.get(phrase);
        return this;
    }

    public PhraseEventCxt recordRed(String event, Object message) {
        return record(event, message, AlarmLevel.RED);
    }

    public PhraseEventCxt recordRed(String event, String message, Object...args) {
        return record(event, message, AlarmLevel.RED, args);
    }

    public PhraseEventCxt recordGreen(String event, Object message) {
        return record(event, message, AlarmLevel.GREEN);
    }

    public PhraseEventCxt recordGreen(String event, String message, Object...args) {
        return record(event, message, AlarmLevel.GREEN, args);
    }

    public PhraseEventCxt recordBlack(String event, Object message) {
        return record(event, message, AlarmLevel.BLACK);
    }

    public PhraseEventCxt recordBlack(String event, String message, Object...args) {
        return record(event, message, AlarmLevel.BLACK, args);
    }

    public PhraseEventCxt record(String event, Object message, AlarmLevel level) {
        current.put(event, level.render(message));
        return this;
    }

    public PhraseEventCxt record(String event, String message, AlarmLevel level, Object...args) {
        current.put(event, level.render(String.format(message, args)));
        return this;
    }

    public PhraseEventCxt reset() {
        all.clear();
        current = null;
        return this;
    }
}
