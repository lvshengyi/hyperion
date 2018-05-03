package core.zeus.bean;

import common.utils.CastUtil;
import core.hades.annotation.Action;
import core.hades.annotation.Controller;
import core.hades.annotation.Service;
import core.zeus.annotation.Aspect;
import core.zeus.annotation.Inject;
import core.zeus.aop.Proxy;
import core.zeus.aop.ProxyManager;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author LvShengyI
 * <p>
 * bean容器，包括bean的初始化
 */
public class BeanContainer {

    /**
     * 初始化标记
     */
    private static volatile Boolean is_ini = false;

    /**
     * 默认配置路径
     */
    private final static String DEFAULT_CONFIG_PATH = "src/main/resources/zeus/zeus.xml";

    /**
     * 标记以注解方式实现自动注入
     */
    private final static String SCAN_NODE_NAME = "component-scan";

    /**
     * 类集合
     */
    private final static Set<Class> CLASS_SET = new HashSet<>(16);

    /**
     * bean容器
     */
    private final static Map<String, Object> BEAN_CONTAINER = new HashMap<>(16);

    /**
     * 要扫描的注解类
     */
    private final static List<Class> SCAN_ANNOTATION_CLASS = new ArrayList<>(16);

    /**
     * 初始化要扫描的注解
     */
    static {
        SCAN_ANNOTATION_CLASS.add(Controller.class);
        SCAN_ANNOTATION_CLASS.add(Service.class);
        SCAN_ANNOTATION_CLASS.add(Action.class);
        SCAN_ANNOTATION_CLASS.add(Aspect.class);
    }

    /**
     * 屏蔽构造方法
     */
    private BeanContainer() {

    }

    /**
     * 获取基包路径
     *
     * @return
     */
    private static String getBasePackagePath() {
        SAXReader reader = new SAXReader();

        Document doc = null;
        try {
            doc = reader.read(DEFAULT_CONFIG_PATH);
            Element root = doc.getRootElement();
            List<Element> elementList = root.elements();

            String basePackage = null;
            for (Element ele : elementList) {
                if (ele.getName().equals(SCAN_NODE_NAME)) {
                    basePackage = ele.attributeValue("base-package");

                    break;
                }
            }

            return basePackage == null ? null : basePackage.replace(".", "/");
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 初始化
     */
    public static void ini() {
        if (!is_ini) {
            synchronized (ZeusContext.class) {
                if (!is_ini) {
                    beanContainerIni(getBasePackagePath());
                    proxyIni();
                }

                is_ini = true;
            }
        }
    }

    /**
     * 获取bean
     *
     * @param key
     * @param <T>
     * @return
     */
    static <T> T get(String key) {
        return (T) BEAN_CONTAINER.get(key);
    }

    /**
     * 类集合初始化
     *
     * @param basePackage
     */
    private static void getClassSetByScan(String basePackage) {
        File file = new File(basePackage);

        try {
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File childFile : files) {
                    getClassSetByScan(childFile.getPath());
                }
            } else {
                String path = file.getPath().replace("src\\main\\java\\", "")
                        .replace(".java", "")
                        .replace("\\", ".");

                Class clz = Class.forName(path);

                if (classCheck(clz)) {
                    CLASS_SET.add(Class.forName(path));
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查Class中是否有需要被扫描的注解
     *
     * @param clz
     * @return
     */
    private static Boolean classCheck(Class clz){
        for(Class anno : SCAN_ANNOTATION_CLASS){
            if(clz.isAnnotationPresent(anno)){
                return true;
            }
        }

        return false;
    }

    /**
     * bean容器初始化
     */
    private static void beanContainerIni(String basePackagePath) {
        try {
            if (basePackagePath == null) {
                beanContainerIniFromScanConfig();
            } else {
                getClassSetByScan(basePackagePath);
                for (Class clz : CLASS_SET) {
                    BEAN_CONTAINER.put(clz.getName(), clz.newInstance());
                }
                beanIni();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void beanContainerIniFromScanConfig() {
        try {
            SAXReader reader = new SAXReader();
            Document doc = reader.read(DEFAULT_CONFIG_PATH);
            Element root = doc.getRootElement();
            List<Element> beanList = root.elements();

            for (Element bean : beanList) {
                if (!Objects.equals(bean.getName(), "bean")) {
                    continue;
                }

                String id = bean.attributeValue("id");
                String classRef = bean.attributeValue("class");
                Class clz = Class.forName(classRef);
                Object instance = clz.newInstance();

                List<Element> propertyList = bean.elements();
                for (Element property : propertyList) {
                    String fieldName = property.attributeValue("name");
                    Field field = clz.getDeclaredField(fieldName);
                    Object value = property.attributeValue("value");

                    fieldValueSet(field, instance, value);
                }

                BEAN_CONTAINER.put(id, instance);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将值写入字段中，主要处理类型转换
     *
     * @param field
     * @param instance
     * @param value
     */
    private static void fieldValueSet(Field field, Object instance, Object value) {
        try {
            Class fieldType = field.getType();
            field.setAccessible(true);

            if (fieldType.equals(Integer.class)) {
                field.set(instance, CastUtil.castToInteger(value));
            } else if (fieldType.equals(Long.class)) {
                field.set(instance, CastUtil.castToLong(value));
            } else if (fieldType.equals(Float.class)) {
                field.set(instance, CastUtil.castToFloat(value));
            } else if (fieldType.equals(Double.class)) {
                field.set(instance, CastUtil.castToDouble(value));
            } else if (fieldType.equals(String.class)) {
                field.set(instance, value);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * bean初始化
     */
    private static void beanIni() {
        try {
            Set<Map.Entry<String, Object>> entrySet = BEAN_CONTAINER.entrySet();

            for (Map.Entry entry : entrySet) {
                String className = (String) entry.getKey();
                Class clz = Class.forName(className);
                Object instance = entry.getValue();
                Field[] fields = clz.getDeclaredFields();

                for (Field field : fields) {
                    if (field.isAnnotationPresent(Inject.class)) {
                        field.setAccessible(true);
                        Class fieldClz = field.getType();
                        field.set(instance, BEAN_CONTAINER.get(fieldClz.getName()));
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * aop绑定
     */
    private static void proxyIni() {
        Set<Class> aspectSet = new HashSet<>(16);

        for (Class clz : CLASS_SET) {
            if (clz.isAnnotationPresent(Aspect.class)) {
                aspectSet.add(clz);
            }
        }

        for (Class aspectClass : aspectSet) {
            Aspect aspect = (Aspect) aspectClass.getAnnotation(Aspect.class);
            Class targetAnnoClass = aspect.value();

            try {
                List<Proxy> proxyList = new ArrayList<>();
                proxyList.add((Proxy) aspectClass.newInstance());

                for (Class clz : CLASS_SET) {
                    if (clz.isAnnotationPresent(targetAnnoClass)) {
                        BEAN_CONTAINER.put(clz.getName(), ProxyManager.createProxy(clz, proxyList));
                    }
                }
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
