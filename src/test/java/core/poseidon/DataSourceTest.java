package core.poseidon;

import org.junit.Test;

/**
 * @author LvShengyI
 */
public class DataSourceTest {

    @Test
    public void dataSourceTest(){
        DataSource ds = DataSource.getDataSource();

        System.out.println(ds.toString());
    }
}
