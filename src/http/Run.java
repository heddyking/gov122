package http;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.*;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import utils.RecCodeAuto;

public class Run {

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private static Thread thread = null;

    public static void init() {
        RecCodeAuto.init("122.lib");
        String json = "["
//                + "{\"Province\":\"gz\",\"Username\":\"532530199508040233\", \"Password\":\"Pppp0000\",\"State\":\"0\"},"
                + "{\"Province\":\"gz\",\"Username\":\"341124199502272011\", \"Password\":\"Pppp0000\",\"State\":\"0\"}"
                + "]";
        try {
            list = new ObjectMapper().readValue(json, List.class);
        } catch (Exception e) {
        }
    }

    public static void start() {
        if (thread != null) {
            thread.stop();
        }
        thread = null;
        thread = new Thread(() -> {
            while (true) {
                Stepx_Monitor.monitor(m ->{
                     for (Map<String, Object> map : list) {
                         if (!Objects.equals(map.get("State"), "0") || map.get("Set-Cookie")==null) continue;
                         String yzm=m.get(map.get("Username")+"");
                         if(yzm==null) continue;
                         map.put("Yzm", yzm);
                         map.put("State", "1");
                         
                         System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
                         System.out.println("Pass Phone Verify: " + map.get("Username"));
                         System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
                         
                         Step6_SavePhone.phoneyzm(map);
                     }
                });
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {
                }
            }
        });
        thread.start();

        while (true) {
            for (Map<String, Object> map : list) {
                if (!Objects.equals(map.get("State"), "0")) {
                    continue;
                }
                new Thread(() -> {
                    try {
                        if (map.get("Set-Cookie") != null) {
                            Map mapz = Stepz_Logout.logout(map);
                            map.put("Set-Cookie", null);
                        }

                        Map map1 = Step1_Home.home(map);
                        map.put("Set-Cookie", map1.get("Set-Cookie"));

                        map1.put("Province", map.get("Province"));
                        Map map2 = Step2_Captcha.captcha(map1);

                        map2.put("Province", map.get("Province"));
                        map2.put("Username", map.get("Username"));
                        map2.put("Password", map.get("Password"));
                        map2.put("Set-Cookie", map.get("Set-Cookie"));
                        Map map3 = Step3_Login.login(map2);

                        map3.put("Province", map.get("Province"));
                        map3.put("Set-Cookie", map.get("Set-Cookie"));
                        Map map4 = Step4_UserCtrl.userctrl(map3);

                        map4.put("Province", map.get("Province"));
                        map4.put("Set-Cookie", map.get("Set-Cookie"));
                        Map map5 = Step5_PhoneYzm.phoneyzm(map4);
                        if((map5.get("Body")+"").contains("申请次数超过系统限制")){
                            map.put("State", "#");
                        }

                    } catch (Exception e) {
                    }
                }).start();
            }
            try {
                Thread.sleep(20000);
            } catch (Exception e) {
            }
        }
    }

    public static void stop() {
        if (thread != null) {
            thread.stop();
        }
        thread = null;
    }

    public static void main(String[] args) {
        init();
        start();
    }
}
