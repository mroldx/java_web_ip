package com.ip.demo;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
public class IndexController {

    private String city;
    @RequestMapping("/")
    public String index(HttpServletRequest request) {

        return "index";
    }

    private IpPojo httpURLGETCase(String ip) {
        //第三方查询接口
        String methodUrl = "http://apidata.chinaz.com/CallAPI/ip";
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        String line = null;
        try {
            URL url = new URL(methodUrl + "?key=ece35883cefe4658bf28c0db039d8ceb&ip=" + ip);
            connection = (HttpURLConnection) url.openConnection();// 根据URL生成HttpURLConnection
            connection.setRequestMethod("GET");// 默认GET请求
            connection.connect();// 建立TCP连接
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 发送http请求
                StringBuilder result = new StringBuilder();
                // 循环读取流
                while ((line = reader.readLine()) != null) {
                    result.append(line).append(System.getProperty("line.separator"));// "\n"
                }
                //json 转对象获取调用结果
                JSONObject jsonObject = JSONObject.parseObject(result.toString());
                IpPojo ipPojo = jsonObject.getObject("Result", IpPojo.class);

                return ipPojo;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            connection.disconnect();
        }
        return null;
    }
}
