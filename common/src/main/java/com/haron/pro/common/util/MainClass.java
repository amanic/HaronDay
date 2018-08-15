package com.haron.pro.common.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * Created by chenhaitao on 2018/8/14.
 */
public class MainClass {
    public static void main(String[] args) throws Exception {
        main1();
    }



    public static void main1() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "{" +
                "\"sites\": [" +
                "{ \"name1\":\"菜鸟教程\" , \"url\":\"www.runoob.com\" }, " +
                "{ \"name1\":\"google\" , \"url\":\"www.google.com\" }, " +
                "{ \"name1\":\"微博\" , \"url\":\"www.weibo.com\" }" +
                "]" +
                "}";
//        Sites sites = objectMapper.readValue(s,Sites.class);
        JsonNode jsonNode = objectMapper.readTree(s);

        System.out.println(jsonNode.get("sites").isArray());


    }



    public static class Sites{
        private List<Con> sites;
        public static class Con{
            @JsonProperty("name1")
            private String name;
            private String url;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            @Override
            public String toString() {
                try {
                    return new ObjectMapper().writeValueAsString(this);
                } catch (JsonProcessingException e) {
                    return "null";
                }
            }
        }

        public List<Con> getSites() {
            return sites;
        }

        public void setSites(List<Con> sites) {
            this.sites = sites;
        }

        @Override
        public String toString() {
            try {
                return new ObjectMapper().writeValueAsString(this);
            } catch (JsonProcessingException e) {
                return "null";
            }
        }
    }
}
