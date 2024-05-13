package com.verygoodbank.tes.util;

import com.verygoodbank.tes.constant.Constants;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtils {

    private static DateFormat FOMATTER = new SimpleDateFormat("yyyyMMdd");
    public static BufferedReader parseToBufferedReader(MultipartFile file) {
        try {
            return new BufferedReader(new InputStreamReader(file.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * extract productId from a trade line
     * ultilizing String.substring() instead of split to avoid uneccessary compute
     * @param line
     * @return
     */
    public static String extractId(String line) {
        int startIndex = line.indexOf(",");
        int endIndex = line.indexOf(",", startIndex + 1);
        return line.substring(startIndex + 1, endIndex);
    }

    /**
     * extract date field from a trade line
     * ultilizing String.substring() instead of split to avoid uneccessary compute
     * @param line
     * @return
     */
    public static String extractDate(String line) {
        int index = line.indexOf(",");
        return line.substring(0, index);
    }

    /**
     * replace productId to pruductName for a trade line
     * ultilizing String.substring() instead of split to avoid uneccessary compute
     * @param line
     * @return
     */
    public static String replaceIdToName(String line, String name) {
        int startIndex = line.indexOf(",");
        int endIndex = line.indexOf(",", startIndex + 1);
        return line.substring(0, startIndex + 1) +  name + line.substring(endIndex, line.length());
    }

    public static HttpHeaders constructCsvResponseHeaders(String outputFileName) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String fileName = new String(outputFileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        httpHeaders.setContentDispositionFormData(Constants.CONTENTD_ISPOSITION_FORMDATA, fileName);
        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);

        return httpHeaders;
    }

    /**
     * validate the date format of a date
     * @param date
     * @return
     */
    public static boolean dateFormatValidation(String date) {
        try {
            Date parsed = FOMATTER.parse(date);
            return date.equals(FOMATTER.format(parsed));
        } catch (Exception e) {
            return false;
        }
    }
}
