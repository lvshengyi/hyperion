package core.poseidon.configuration;

import common.exception.InitializeException;
import common.utils.CastUtil;
import core.poseidon.configuration.datasourcetype.ConnectionPool;
import core.poseidon.configuration.datasourcetype.ConnectionUnpool;
import core.poseidon.constant.DataSourceTypeEnum;
import core.poseidon.constant.StatementType;
import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LvShengyI
 * <p>
 * 使用单例模式，单例对象为dataSouce
 */
public class Configuration {

    /**
     * 默认配置路径
     */
    private static final String DEFAULT_CONFIG_PATH = "G:\\Work\\hyperion\\src\\main\\resources\\poseidon\\poseidon.xml";

    /**
     * 默认映射路径
     */
    private static final String DEFAULT_MAPPER_PATH = "G:\\Work\\hyperion\\src\\main\\resources\\poseidon\\poseidon_mapper.xml";

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
    @Getter
    private volatile DataSource dataSource;

    /**
     * 用于储存初始化后的mapper数据
     */
    @Getter
    private volatile Map<String, StatementMapper> statementMapperMap;

    /**
     * 配置实例
     */
    private volatile static Configuration config;

    /**
     * 用于实现单例模式
     */
    private Configuration() {
    }

    /**
     * 初始化方法
     *
     * @param id
     * @return
     */
    public static Configuration build(String id){
        if(config == null){
            synchronized (Configuration.class){
                if(config == null){
                    Configuration.config = new Configuration(getDataSource(id), getMapper());
                }
            }
        }

        return config;
    }

    /**
     * 实际构造函数
     *
     * @param dataSource
     * @param statementMapperMap
     */
    private Configuration(DataSource dataSource, Map<String, StatementMapper> statementMapperMap){
        this.dataSource = dataSource;
        this.statementMapperMap = statementMapperMap;
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

                    dataSource.setId(idValue);
                    dataSource.setType(dataSourceEle.attributeValue("statementType"));

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

                    String dataSourceType = dataSourceEle.attributeValue("type");
                    if(DataSourceTypeEnum.UNPOOLED.getDesc().equals(dataSourceType)){
                        dataSource.setConnectionManage(new ConnectionUnpool(dataSource));
                    }else if(DataSourceTypeEnum.POOL.getDesc().equals(dataSourceType)){
                        dataSource.setConnectionManage(new ConnectionPool(dataSource));
                    }else{
                        throw new IllegalArgumentException("检查配置文件中数据库连接类型的参数");
                    }

                    return dataSource;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        throw new InitializeException("dataSource initialize failed, id:" + id);
    }

    /**
     * 获取mapper
     *
     * @return
     */
    public static Map<String, StatementMapper> getMapper(){
        SAXReader reader = new SAXReader();
        Map<String, StatementMapper> map = new HashMap<>(16);

        try {
            Document doc = reader.read(DEFAULT_MAPPER_PATH);
            Element root = doc.getRootElement();
            List<Element> mapperList = root.elements();

            for (Element mapperEle : mapperList) {
                StatementMapper mapper = new StatementMapper();
                String nodeName = mapperEle.getName();
                String id = mapperEle.attributeValue("id");
                String nameSpace = mapperEle.attributeValue("nameSpace");
                String parameterType = mapperEle.attributeValue("parameterType");
                String resultType = mapperEle.attributeValue("resultType");
                String stat = mapperEle.getStringValue();
                stat = statementPreProcess(stat);
                String key = String.format("%s.%s", nameSpace, id);

                mapper.setId(id);
                mapper.setNameSpace(nameSpace);
                mapper.setStatementType(StatementType.getByDesc(nodeName));
                mapper.setParameterType(parameterType);
                mapper.setResultType(resultType);
                mapper.setStat(stat);

                map.put(key, mapper);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 对mapper中的SQL进行预处理
     * 去除了换行与头尾的空格
     *
     * @param stat
     * @return
     */
    private static String statementPreProcess(String stat){
        return stat.replaceAll("\n", "").trim();
    }
}
