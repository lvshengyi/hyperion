package core.hades.configuration;

import common.exception.InitializeException;
import common.utils.CastUtil;
import core.hades.constant.DataSourceTypeEnum;
import core.hades.poseidonsession.PoseidonSessionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @author LvShengyI
 * <p>
 * 使用单例模式，单例对象为dataSouce
 */
public class Configuration {

    /**
     * 默认配置路径
     */
    private static final String DEFAULT_CONFIG_PATH = "src/main/resources/poseidon.xml";

    /**
     * 默认最大空闲连接数
     */
    private static final Integer DEFAULT_MAX_IDLE_CONNECTION_NUM = 10;

    /**
     * 默认最大活跃连接数
     */
    private static final Integer DEFAULT_MAX_ACTIVE_CONNECTION_NUM = 100;

    /**
     * 默认连接失效时间
     */
    private static final Integer DEFAULT_EXPIRE_TIME = 60;

    /**
     * 持有dataSource，用于实现单例模式
     */
    private volatile static DataSource dataSource;

    /**
     * 用于实现单例模式
     */
    private Configuration() {
    }

    /**
     * 获取dataSource对象
     *
     * @param id
     * @return
     */
    public static DataSource getDataSource(String id) {
        if (id == null) {
            throw new IllegalArgumentException("DataSource id 不能为空");
        }

        //使用双重锁实现单例模式
        if (dataSource == null) {
            synchronized (Configuration.class) {
                if (dataSource == null) {
                    dataSource = createDataSource(id);
                }
            }
        }

        if (dataSource == null) {
            throw new InitializeException("DataSource初始化失败");
        }

        return dataSource;
    }

    /**
     * 读取配置文件，组装成DataSource
     *
     * @param id 要获取的DataSource id
     * @return 找不到对应的id时，返回null
     */
    private static DataSource createDataSource(String id) {
        SAXReader reader = new SAXReader();

        try {
            Document doc = reader.read(DEFAULT_CONFIG_PATH);
            Element root = doc.getRootElement();
            List<Element> dataSourceList = root.elements();

            for (Element dataSourceEle : dataSourceList) {
                String idValue = dataSourceEle.attributeValue("id");

                if (id.equals(idValue)) {
                    List<Element> propertyList = dataSourceEle.elements();
                    DataSource dataSource = new DataSource();
                    String typeValue = dataSourceEle.attributeValue("type");

                    dataSource.setId(idValue);
                    dataSource.setType(dataSourceEle.attributeValue("type"));

                    for (Element property : propertyList) {
                        String propName = property.attributeValue("name");

                        switch (propName) {
                            case "driver":
                                dataSource.setDrive(property.attributeValue("value"));
                                break;
                            case "url":
                                dataSource.setUrl(property.attributeValue("value"));
                                break;
                            case "username":
                                dataSource.setUsername(property.attributeValue("value"));
                                break;
                            case "password":
                                dataSource.setPassword(property.attributeValue("value"));
                                break;
                            default:
                        }

                        //如果是使用连接池，则还需要设置空闲、活跃列表线程数
                        if (!DataSourceTypeEnum.POOL.getDesc().equals(typeValue)) {
                            continue;
                        }

                        switch (propName) {
                            case "maxIdleConnectionNum":
                                dataSource.setMaxIdleConnectionNum(
                                        CastUtil.castToInteger(
                                                property.attributeValue("value"),
                                                DEFAULT_MAX_IDLE_CONNECTION_NUM));
                                break;
                            case "maxActiveConnectionNum":
                                dataSource.setMaxActiveConnectionNum(
                                        CastUtil.castToInteger(
                                                property.attributeValue("value"),
                                                DEFAULT_MAX_ACTIVE_CONNECTION_NUM));
                                break;
                            case "connectionExpireTime":
                                dataSource.setExpireTime(
                                        CastUtil.castToInteger(
                                                property.attributeValue("value"),
                                                DEFAULT_EXPIRE_TIME));
                                break;
                            default:
                        }
                    }

                    return dataSource;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * @return
     */
    public static PoseidonSessionFactory build() {
        return new PoseidonSessionFactory();
    }
}
