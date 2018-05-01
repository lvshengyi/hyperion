package core.poseidon.executor.cache;

import core.poseidon.configuration.StatementMapper;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * @author LvShengyI
 */
@AllArgsConstructor
@EqualsAndHashCode
public class CacheKey {

    private StatementMapper mapper;

    private Object param;
}
