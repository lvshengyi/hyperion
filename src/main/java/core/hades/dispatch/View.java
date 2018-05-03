package core.hades.dispatch;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LvShengyI
 */
public class View {

    @Getter
    private String path;

    @Getter
    private Map<String, Object> model = new HashMap<>();

    public View(String path){
        this.path = path;
    }

    public View addModel(String key, Object value){
        model.put(key, value);

        return this;
    }
}
