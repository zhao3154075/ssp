package weixin.popular.api;

import com.alibaba.fastjson.JSONObject;
import com.cloudplatform.ApiClient;
import weixin.popular.bean.ticket.Ticket;

import java.util.HashMap;
import java.util.Map;

public class TicketAPI extends BaseAPI {
    public TicketAPI() {
    }

    public static Ticket ticketGetticket(String appid) {
        Map map=new HashMap();
        map.put("wx_appid", appid);
        map.put("is_cloud",1);
        JSONObject token= ApiClient.call("wx/index","getJsapiTiket",map);
        Ticket ticket=new Ticket();
        ticket.setTicket(token.getString("jsapi_tiket"));
        return ticket;
    }
}
