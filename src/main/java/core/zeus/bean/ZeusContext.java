package core.zeus.bean;

/**
 * @author LvShengyI
 */
public class ZeusContext {

    /**
     * 屏蔽构造函数
     */
    private ZeusContext() {
    }

    /**
     * 初始化方法
     */
    public static ZeusContext ini() {
        BeanContainer.ini();

        return new ZeusContext();
    }

    /**
     * 获取bean
     *
     * @param classPath
     * @return
     */
    public <T> T getBean(String classPath) {
        return BeanContainer.get(classPath);
    }
}
