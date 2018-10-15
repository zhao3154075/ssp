package weixin.popular.api;

import com.alibaba.fastjson.JSONObject;
import com.cloudplatform.ApiClient;
import weixin.popular.bean.ticket.Ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketAPI extends BaseAPI {
    public TicketAPI() {
    }

    public static Ticket ticketGetticket(String appid,String secret) {
        Map map=new HashMap();
        map.put("wx_appid", appid);
        map.put("wx_appsecret",secret);
        JSONObject token= ApiClient.call("wx/index","getJsapiTiket",map);
        Ticket ticket=new Ticket();
        ticket.setTicket(token.getString("jsapi_tiket"));
        return ticket;
    }
}
