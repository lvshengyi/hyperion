package core.poseidon.executor.handler;

import lombok.Getter;
import lombok.Setter;

/**
 * @author LvShengyI
 */
public class ParamNode {

    /**
     * 结点字段名
     */
    @Getter
    @Setter
    private String fieldName;

    /**
     * 使用链表形式存取
     */
    private ParamNode next;

    public ParamNode next(){
        return next;
    }

    public void next(ParamNode node){
        this.next = node;
    }
}
