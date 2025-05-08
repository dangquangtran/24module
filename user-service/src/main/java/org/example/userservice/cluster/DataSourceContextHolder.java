//package org.example.userservice.cluster;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class DataSourceContextHolder {
//    private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<>();
//    private static final List<String> readDataSourceKeys = new ArrayList<>();
//
//    public static void setDataSourceType(String dataSourceType) {
//        System.out.println("⚡ Using DataSource: " + dataSourceType);
//        CONTEXT_HOLDER.set(dataSourceType);
//    }
//
//    public static String getDataSourceType() {
//        return CONTEXT_HOLDER.get();
//    }
//
//    public static void clearDataSourceType() {
//        CONTEXT_HOLDER.remove();
//    }
//
//    public static void addReadDataSourceKey(String key) {
//        readDataSourceKeys.add(key);
//    }
//
//    public static String getRandomReadDataSourceKey() {
//        return readDataSourceKeys.get(new Random().nextInt(readDataSourceKeys.size()));
//    }
//}