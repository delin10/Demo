package nil.ed.test.h2.mapper;

import java.util.List;

import nil.ed.test.h2.TestDO;

public interface TestDAO {

    void insert(String resourceId);

    List<TestDO> getAll();

}
