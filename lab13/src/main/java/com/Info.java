package com;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.text.DateFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

public class Info {
    public Info() {
        String baseName = "res.Messages";
        Locale l = Locale.getDefault();
        ResourceBundle messages = ResourceBundle.getBundle(baseName, l);
        System.out.println(messages.getString("info"));
        System.out.println(messages.getString("country")+" "+l.getDisplayCountry());
        System.out.println(messages.getString("language")+" "+l.getDisplayLanguage());
        System.out.println(messages.getString("currency")+" "+Currency.getInstance(l));
        String[] weekDays = new DateFormatSymbols(l).getWeekdays();
        System.out.println(messages.getString("weekdays")+" "+Arrays.toString(weekDays));
        String[] months=new DateFormatSymbols(l).getMonths();
        System.out.println(messages.getString("months")+" "+Arrays.toString(months));
        LocalDateTime today=LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter
                .ofLocalizedDate(FormatStyle.FULL)
                .withLocale(l);
        System.out.println(messages.getString("today")+" "+today.format(formatter));

        //creeaza un client http si un get pentru locale-ul curent
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://www.oorsprong.org/websamples.countryinfo/CountryInfoService.wso/FullCountryInfo/JSON/debug?sCountryISOCode=" + l.getCountry());
        //executa get-ul si face un JSON cu informatiile oferite
        try {
            HttpResponse httpResponse = httpClient.execute(httpGet);
            StringBuilder responseBuilder;
            try (Scanner scanner = new Scanner(httpResponse.getEntity().getContent())) {
                responseBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    responseBuilder.append(scanner.nextLine());
                }
                httpResponse.getEntity().getContent().close();
            }
            //parseaza JSON-ul si il mapeaza a.i. sa pot folosi get pentru a prelua anumite informatii
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(responseBuilder.toString(), Map.class);
            System.out.println(messages.getString("telephone") + map.get("sPhoneCode"));
            System.out.println(messages.getString("continent") + map.get("sContinentCode"));
            System.out.println(messages.getString("flag") + map.get("sCountryFlag"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
