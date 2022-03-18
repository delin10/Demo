package nil.ed.test.db.mysql.binlog;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author lidelin.
 */
@Getter
@AllArgsConstructor
public enum EventType {
    UNKNOWN_EVENT(0, ""),
    START_EVENT_V3(1, ""),
    QUERY_EVENT(2, ""),
    STOP_EVENT(3, ""),
    ROTATE_EVENT(4, ""),
    INTVAR_EVENT(5, ""),
    LOAD_EVENT(6, ""),
    SLAVE_EVENT(7, ""),
    CREATE_FILE_EVENT(8, ""),
    APPEND_BLOCK_EVENT(9, ""),
    EXEC_LOAD_EVENT(10, ""),
    DELETE_FILE_EVENT(11, ""),
    NEW_LOAD_EVENT(12, ""),
    RAND_EVENT(13, ""),
    USER_VAR_EVENT(14, ""),
    FORMAT_DESCRIPTION_EVENT(15, ""),
    XID_EVENT(16, ""),
    BEGIN_LOAD_QUERY_EVENT(17, ""),
    EXECUTE_LOAD_QUERY_EVENT(18, ""),
    TABLE_MAP_EVENT(19, ""),
    WRITE_ROWS_EVENTv0(20, ""),
    UPDATE_ROWS_EVENTv0(21, ""),
    DELETE_ROWS_EVENTv0(22, ""),
    WRITE_ROWS_EVENTv1(23, ""),
    UPDATE_ROWS_EVENTv1(24, ""),
    DELETE_ROWS_EVENTv1(25, ""),
    INCIDENT_EVENT(26, ""),
    HEARTBEAT_EVENT(27, ""),
    IGNORABLE_EVENT(28, ""),
    ROWS_QUERY_EVENT(29, ""),
    WRITE_ROWS_EVENTv2(30, ""),
    UPDATE_ROWS_EVENTv2(31, ""),
    DELETE_ROWS_EVENTv2(32, ""),
    GTID_EVENT(33, ""),
    ANONYMOUS_GTID_EVENT(34, ""),
    PREVIOUS_GTIDS_EVENT(35, ""),
    ;
    private final int code;
    private final String desc;

    public static EventType findByCode(int code) {
        for (EventType eventType : EventType.values()) {
            if (eventType.getCode() == code) {
                return eventType;
            }
        }
        return EventType.UNKNOWN_EVENT;
    }
}
