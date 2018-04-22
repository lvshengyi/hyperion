package core.hades.configuration;

import core.hades.poseidonsession.PoseidonSessionFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.util.List;

/**
 * @author LvShengyI
 */
public class Configuration {

    /**
     * 默认配置路径
     */
    private static final String DEFAULT_CONFIG_PATH = "src/main/resources/poseidon.xml";

    /**
     * 读取配置文件
     *
     * @param id
     * @return
     */
    public static DataSource configIni(String id) {
        if (id == null) {
            throw new IllegalArgumentException("dataSource must not be null");
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
                    dataSource.setType(dataSourceEle.attributeValue("type"));

                    for(Element property : propertyList){
                        String propName = property.attributeValue("name");

                        switch (propName){
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
                    }

                    return dataSource;
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        throw new IllegalArgumentException("can not find dataSource with id = " + id);
    }

    public static PoseidonSessionFactory build(){
        return new PoseidonSessionFactory();
    }
}
