package core.poseidon;

import common.annotation.Sql;

/**
 * @author LvShengyI
 */
public interface UserMapper {

    /**
     * 获取数据
     *
     * @param id
     * @return
     */
    @Sql("SELECT * FROM user WHERE id = #{id}")
    User getUser(Integer id);
}
