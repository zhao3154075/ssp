//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package weixin.popular.support;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import weixin.popular.api.TicketAPI;
import weixin.popular.bean.ticket.Ticket;

public class TicketManager {
    private static final Logger logger = LoggerFactory.getLogger(TicketManager.class);
    private static ScheduledExecutorService scheduledExecutorService;
    private static Map<String, String> ticketMap = new ConcurrentHashMap();
    private static Map<String, ScheduledFuture<?>> futureMap = new ConcurrentHashMap();
    private static int poolSize = 2;
    private static boolean daemon;
    private static String firestAppid;
    private static final String KEY_JOIN = "__";

    public TicketManager() {
    }

    private static void initScheduledExecutorService() {
        logger.info("daemon:{},poolSize:{}", daemon, poolSize);
        scheduledExecutorService = Executors.newScheduledThreadPool(poolSize, new ThreadFactory() {
            public Thread newThread(Runnable arg0) {
                Thread thread = Executors.defaultThreadFactory().newThread(arg0);
                thread.setDaemon(TicketManager.daemon);
                return thread;
            }
        });
    }

    public static void setPoolSize(int poolSize) {
        poolSize = poolSize;
    }

    public static void setDaemon(boolean daemon) {
        daemon = daemon;
    }

    public static void init(String appid,String secret) {
        init(appid,secret, 0, 7140);
    }

    public static void init(String appid,String secret, String types) {
        init(appid,secret, 0, 7140, types);
    }

    public static void init(String appid,String secret, int initialDelay, int delay) {
        init(appid,secret, initialDelay, delay, "jsapi");
    }

    public static void init(final String appid,final String secret, int initialDelay, int delay, String... types) {
        if (firestAppid == null) {
            firestAppid = appid;
        }

        String[] var4 = types;
        int var5 = types.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            final String type = var4[var6];
            final String key = appid + "__" + type;
            if (scheduledExecutorService == null) {
                initScheduledExecutorService();
            }

            if (futureMap.containsKey(key)) {
                ((ScheduledFuture)futureMap.get(key)).cancel(true);
            }

            if (initialDelay == 0) {
                doRun(appid,secret, type, key);
            }

            ScheduledFuture<?> scheduledFuture = scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
                public void run() {
                    TicketManager.doRun(appid,secret, type, key);
                }
            }, initialDelay == 0 ? (long)delay : (long)initialDelay, (long)delay, TimeUnit.SECONDS);
            futureMap.put(key, scheduledFuture);
        }

    }

    private static void doRun(String appid,String secret, String type, String key) {
        try {
            Ticket ticket = TicketAPI.ticketGetticket(appid,secret);
            ticketMap.put(key, ticket.getTicket());
            logger.info("TICKET refurbish with appid:{} type:{}", appid, type);
        } catch (Exception var5) {
            logger.error("TICKET refurbish error with appid:{} type:{}", appid, type);
            logger.error("", var5);
        }

    }

    public static void destroyed() {
        scheduledExecutorService.shutdownNow();
        logger.info("destroyed");
    }

    public static void destroyed(String appid) {
        destroyed(appid, "jsapi", "wx_card");
    }

    public static void destroyed(String appid, String... types) {
        String[] var2 = types;
        int var3 = types.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String type = var2[var4];
            String key = appid + "__" + type;
            if (futureMap.containsKey(key)) {
                ((ScheduledFuture)futureMap.get(key)).cancel(true);
                logger.info("destroyed appid:{} type:{}", appid, type);
            }
        }

    }

    public static String getTicket(String appid) {
        return getTicket(appid, "jsapi");
    }

    public static String getTicket(String appid, String type) {
        return (String)ticketMap.get(appid + "__" + type);
    }

    public static String getDefaultTicket() {
        return (String)ticketMap.get(firestAppid);
    }

    static {
        daemon = Boolean.TRUE;
    }
}
