package nil.ed.test.comm.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author lidelin.
 */
@Data
@Accessors(chain = true)
public class TestDTO {
    private Integer id;
    private String name;
}
