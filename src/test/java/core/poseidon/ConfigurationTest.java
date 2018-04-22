package core.poseidon;

import base.BaseTest;
import core.hades.configuration.Configuration;
import core.hades.configuration.DataSource;
import org.junit.Test;

/**
 * @author LvShengyI
 */
public class ConfigurationTest extends BaseTest{

    @Test
    public void initTest(){
        Configuration configuration = new Configuration();

        DataSource dataSource = configuration.configIni("mysql");

        print(dataSource);
    }
}
