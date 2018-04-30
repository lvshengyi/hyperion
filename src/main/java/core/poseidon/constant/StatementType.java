package core.poseidon.constant;

import lombok.Getter;

/**
 * @author LvShengyI
 */
public enum StatementType {
    SELECT(1, "SELECT"),
    INSERT(2, "INSERT"),
    UPDATE(3, "UPDATE"),
    DELETE(4, "DELETE");

    @Getter
    private Integer code;

    @Getter
    private String desc;

    StatementType(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据类型描述返回相应类型
     *
     * @param desc
     * @return
     */
    public static StatementType getByDesc(String desc){
        StatementType[] statementTypeList = StatementType.values();

        for(StatementType type : statementTypeList){
            if(type.desc.toUpperCase().equals(desc.toUpperCase())){
                return type;
            }
        }

        throw new IllegalArgumentException("can not find by arg:" + desc);
    }
}
