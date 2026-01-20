package hello.proxy.pureproxy.proxy.code;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CacheProxy implements Subject{

    private Subject target;
    private String cacheValue;

    public CacheProxy(Subject target) {
        this.target = target;
    }

    @Override
    public String operation() {
        log.debug("proxy operation");
        if(cacheValue == null){
            log.info("캐시 없음, 호출");
            cacheValue = target.operation();
        }else{
            log.info("캐시 있음, 반환");
        }
        return cacheValue;
    }
}
