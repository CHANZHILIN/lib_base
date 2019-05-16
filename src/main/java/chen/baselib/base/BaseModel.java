package chen.baselib.base;

import chen.baselib.api.Api;
import chen.baselib.api.ApiEngine;

/**
 * Created by CHEN_ on 2019/4/11.
 */

public abstract class BaseModel {

    protected final Api mApiService;

    public BaseModel() {
        mApiService = ApiEngine.getInstance().getApiService();
    }
}
