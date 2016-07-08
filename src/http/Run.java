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
import utils.RecCodeAuto2;

public class Run {

    private static List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
    private static Thread thread = null;
    private static Thread result = null;

    public static void init() {
        RecCodeAuto.init("122.lib");
        RecCodeAuto2.init("122_2.lib");
        String json = "["
                //                + "{\"Province\":\"gz\",\"Username\":\"532530199508040233\", \"Password\":\"Pppp0000\",\"State\":\"0\",\"Cphs\":[\"C2C000\"]},"
                + "{\"Province\":\"gz\",\"Username\":\"340621199506128719\", \"Password\":\"Pppp0000\",\"State\":\"0\",\"Cphs\":[\"C2C000\",\"C2C222\",\"C1E794\",\"C5H734\"]}"
                + "]";
        try {
            list = new ObjectMapper().readValue(json, List.class);
        } catch (Exception e) {
        }
    }

    public static void start() {
        if (result != null) {
            result.stop();
        }
        result = null;
        result=new Thread(()->{
            while (true) {
                System.out.println("###############Result################");
                for(Map<String,Object> map: list){
                    if(map.containsKey("Cph")){
                        System.out.println(map.get("Username")+": "+map.get("Cph"));
                    }
                }
                System.out.println("#####################################");
                try {
                    Thread.sleep(6666);
                } catch (Exception e) {
                }
            }
        });
        result.start();
        
        if (thread != null) {
            thread.stop();
        }
        thread = null;
        thread = new Thread(() -> {
            while (true) {
                Stepx_Monitor.monitor(m -> {
                    for (Map<String, Object> map : list) {
                        if (!Objects.equals(map.get("State"), "0") || map.get("Set-Cookie") == null) {
                            continue;
                        }
                        String yzm = m.get(map.get("Username") + "");
                        if (yzm == null) {
                            continue;
                        }
                        map.put("Yzm", yzm);
                        map.put("State", "1");

                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");
                        System.out.println("Pass Phone Verify: " + map.get("Username"));
                        System.out.println("@@@@@@@@@@@@@@@@@@@@@@");

                        Map<String, Object> map6 = Step6_SavePhone.phoneyzm(map);
                        //can't pass yzm
                        if (!(map6.get("Body") + "").contains("操作成功")) {
                            map.put("State", "0");
                            continue;
                        }
                        if ((map6.get("Location") + "").contains("user/m/login") || (map6.get("Location") + "").contains("veh1/netxh/indexCtrl")) {
                            map.put("State", "0");
                            continue;
                        }
                        
                        Map<String, Object> map7 = Step7_Keyboard.keyboard(map);

                        try {
                            List<String> cphs = (List<String>) map.get("Cphs");
                            int k = 0;
                            //try to select car number
                            while (Objects.equals(map.get("State"), "1") && cphs != null && k < cphs.size()) {
                                String cph = cphs.get(k);
                                ++k;

                                Map<String, Object> map8 = Step8_Captcha.captcha(map);

                                try {
                                    Thread.sleep(22222);
                                } catch (Exception e) {
                                }

                                map8.put("Province", map.get("Province"));
                                map8.put("Set-Cookie", map.get("Set-Cookie"));
                                map8.put("Cph", cph);
                                Map<String, Object> map9 = Step9_Confirm.confirm(map8);

                                if ((map9.get("Body") + "").contains("年")
                                        && (map9.get("Body") + "").contains("月")
                                        && (map9.get("Body") + "").contains("日")
                                        && !(map9.get("Body") + "").contains("失败")) {
                                    map.put("State", "2");
                                    map.put("Cph", cph);
                                    break;
                                }
                                if ((map9.get("Location") + "").contains("user/m/login")
                                        || (map9.get("Location") + "").contains("veh1/netxh/indexCtrl")) {
                                    map.put("State", "0");
                                    break;
                                }
                            }
                        } catch (Exception e) {
                        }
                    }
                });
                try {
                    Thread.sleep(666 * 10);
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
                        if ((map5.get("Body") + "").contains("申请次数超过系统限制")) {
                            map.put("State", "#");
                        }

                    } catch (Exception e) {
                    }
                }).start();
            }
            try {
                Thread.sleep(11111 * 10);
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
