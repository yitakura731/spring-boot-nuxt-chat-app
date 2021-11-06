package com.example.demo.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseCookie;

import com.example.demo.api.data.AppUser;
import com.example.demo.auth.JwtAuthentication;
import com.example.demo.repository.data.BaseUser;
public class Tools {

    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    public static boolean existAttr(
            List<Map<String, Object>> attributes, String id) throws Exception {
        boolean retVal = false;
        for (Map<String, Object> attr : attributes) {
            Object actId = attr.get("id");
            if (actId != null && ((String) actId).equals(id)) {
                retVal = true;
                break;
            }
        }
        return retVal;
    }

    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("Asia/Tokyo"));
    }

    public static Date toDate(ZonedDateTime date) {
        return Date.from(date.toInstant());
    }

    public static LocalDateTime toLocalDateTime(String date) {
        return LocalDateTime.parse(date + " 00:00:00.000", FORMAT);
    }

    public static LocalDateTime nowDateTime() {
        return LocalDateTime.now();
    }

    public static ResponseCookie createCookie(
            String key, String value, String domain, boolean secure, boolean httpOnly) {
        return ResponseCookie.from(key, value).
                domain(domain).
                path("/").
                maxAge(Duration.ofDays((long) 1)).
                httpOnly(httpOnly).
                secure(secure).
                sameSite("Strict").
                build();
    }

    public static AppUser toAppUser(BaseUser user) {
        return AppUser.builder().
                id(user.getId()).
                userId(user.getUserId()).
                name(user.getName()).
                email(user.getEmail()).
                build();
    }

    public static AppUser toAppUser(JwtAuthentication user) {
        return AppUser.builder().
                id(user.getId()).
                userId(user.getUserId()).
                name(user.getName()).
                email(user.getEmail()).
                build();
    }
}
