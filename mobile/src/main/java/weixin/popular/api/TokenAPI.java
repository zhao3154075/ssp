package weixin.popular.api;

import com.alibaba.fastjson.JSONObject;
import com.cloudplatform.ApiClient;
import weixin.popular.bean.token.Token;

import java.util.HashMap;
import java.util.Map;

public class TokenAPI extends BaseAPI {
    public TokenAPI() {
    }

    public static Token token(String appid, String secret) {
        Map map=new HashMap();
        map.put("wx_appid",appid);
        map.put("wx_appsecret",secret);
        JSONObject token= ApiClient.call("wx/index","getAccessToken",map);
        Token token1=new Token();
        token1.setAccess_token(token.getString("access_token"));
        return token1;
    }
}
