package com.szcgc.third.tyc.business;

import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import com.szcgc.comm.util.HttpUtils;
import com.szcgc.third.tyc.config.TycConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TycApi {

    private static final Log logger = LogFactory.getLog(TycApi.class);
    private static final String OK_REP = "\"error_code\":0";
    private static final String ENC = "UTF-8";
    private static final Map<String, String> HEADER = new HashMap<String, String>(1);

    static {
        HEADER.put("Authorization", TycConfig.self.getToken());
    }

    public static <T> T interact(TycRequest request, Class<T> valueType) throws Exception {
        StringBuilder sblog = new StringBuilder(request.getUrl());
        StringBuilder sb = new StringBuilder(request.getUrl());
        sblog.append('?');
        sb.append('?');
        Field[] fields = read(request);
        for (Field field : fields) {
            Object value = field.get(request);
            if (value == null)
                continue;
            sb.append(field.getName());
            sb.append('=');
            sb.append(URLEncoder.encode(String.valueOf(value), ENC));
            sb.append('&');

            sblog.append(field.getName());
            sblog.append("=");
            sblog.append(value);
            sblog.append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        String req = sb.toString();
        String rep = HttpUtils.httpGet(req, HEADER);
        sblog.append(rep);
        logger.info(sblog.toString());
        //System.out.println(sblog.toString());

        if (rep.indexOf(OK_REP) < 0)
            return null;
        ObjectMapper mapper = new ObjectMapper();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(TycResponse.class, valueType);
        TycResponse<T> tycRep = mapper.readValue(rep, javaType);
        return tycRep.result;
    }

    private static final Map<Class<?>, Field[]> FIELDSMAP = new HashMap<>();

    private static Field[] read(TycRequest request) {
        Class<?> key = request.getClass();
        Field[] fields = FIELDSMAP.get(key);
        if (fields != null)
            return fields;
        fields = request.getClass().getFields();
        for (Field field : fields) {
            field.setAccessible(true);
        }
        FIELDSMAP.put(key, fields);
        return fields;
    }
}
