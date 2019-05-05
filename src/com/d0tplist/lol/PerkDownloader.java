package com.d0tplist.lol;

import com.d0tplist.pojo.Perk;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class PerkDownloader {


    public static void main(String[] args) {
        List<Perk> perkList = getPerk("draven", "lan", new Perk("Test"));

        for (Perk perk1 : perkList) {
            System.out.println("Result: " + perk1);
        }
    }

    public static List<Perk> getPerk(String championName, String region, Perk current) {

        if (region == null) {
            throw new NullPointerException("Region null");
        }

        championName = championName.replaceAll(" ", "").toLowerCase();

        if (championName.equals("maestroyi")) {
            championName = "masteryi";
        }

        final String url = "http://" + region + ".op.gg/champion/" + championName + "/statistics";

        List<PrePerk> perk = get(url);

        if (perk.isEmpty()) {
            return new ArrayList<>();
        }

        List<Perk> result = new ArrayList<>();

        for (PrePerk prePerk : perk) {
            Perk convert = toPerk(prePerk, current);
            result.add(convert);

            System.out.println(convert+" "+prePerk);
        }

        return result;
    }

    private static Perk toPerk(PrePerk perk, Perk current) {

        final Perk result = new Perk();

        try {
            List<Integer> selection = new ArrayList<>();

            selection.addAll(perk.getPrimaryPerks());
            selection.addAll(perk.getSecondaryPerks());
            selection.addAll(perk.getShardPerks());

            result.setId(current.getId());
            result.setSelectedPerkIds(selection);

            result.setIsActive(current.getIsActive());
            result.setIsDeletable(current.getIsDeletable());
            result.setIsValid(true);
            result.setName(current.getName());
            result.setIsEditable(current.getIsEditable());
            result.setOrder(current.getOrder());
            result.setLastModified(System.currentTimeMillis());
            result.setCurrent(current.getCurrent());

            result.setSubStyleId(perk.getSecondaryStyle());
            result.setPrimaryStyleId(perk.getPrimaryStyle());

            result.setPrePerk(perk);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }

    private static List<PrePerk> get(String value) {

        try {

            System.out.println("Requesting: " + value);

            URL url = new URL(value);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("dataType", "text");
            connection.setRequestMethod("GET");

            final int responseCode = connection.getResponseCode();
            final String responseMessage = connection.getResponseMessage();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            final List<String> strings = reader.lines().collect(Collectors.toList());

            final String result = strings.stream().collect(Collectors.joining("\n"));

            List<String> collect = strings.stream()
                    .filter(a -> a.contains("champion-stats-summary-rune__name"))
                    .map(x -> x.trim().replaceAll("<.*?>", ""))
                    .collect(Collectors.toList());

            String[] pages = result.split("perk-page-wrap");

            System.out.println("Pages: " + pages.length + " Types: " + collect.size());

            List<PrePerk> resultList = new ArrayList<>();

            for (int i = 1; i < pages.length; i++) {
                PrePerk convert = convert(pages[i]);
                if (convert != null) {
                    if (i <= 2) {
                        convert.setType(collect.get(0));
                    } else {
                        convert.setType(collect.get(1));
                    }

                    resultList.add(convert);
                }
            }

            System.out.println("Response code: " + responseCode + " " + responseMessage);

            return resultList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new ArrayList<>();
    }

    private static PrePerk convert(String result) {
        List<String> lines = Arrays.stream(result.split("\n")).collect(Collectors.toList());

        PrePerk perk = new PrePerk();

        for (String line : lines) {

            if (line.contains("<strong>")) {

                if (perk.getPickRate() == null) {
                    perk.setPickRate(line.trim().replaceAll("<.*?>", ""));
                    continue;
                }

                if (perk.getWinRate() == null) {
                    perk.setWinRate(line.trim().replaceAll("<.*?>", ""));
                }

            }

            if (line.trim().startsWith("<img src=") && !line.contains("e_grayscale")) {

                String x;


                if (line.contains("perkStyle/")) {
                    String substring = line.substring(line.indexOf("perkStyle/"), line.indexOf(".png"))
                            .substring("perkStyle/".length());

                    if (perk.getPrimaryStyle() == null) {
                        perk.setPrimaryStyle(Integer.valueOf(substring));
                    } else {
                        perk.setSecondaryStyle(Integer.valueOf(substring));
                    }

                    continue;
                }

                if (line.contains("perk/")) {
                    x = line.substring(line.indexOf("perk/"), line.indexOf(".png"))
                            .substring("perk/".length());

                    if (perk.getSecondaryStyle() == null) {
                        perk.getPrimaryPerks().add(Integer.valueOf(x));
                    } else {
                        perk.getSecondaryPerks().add(Integer.valueOf(x));
                    }

                } else if (line.contains("perkShard/")) {
                    x = line.substring(line.indexOf("perkShard/"), line.indexOf(".png"))
                            .substring("perkShard/".length());

                    perk.getShardPerks().add(Integer.valueOf(x));
                }
            }
        }

        if (perk.getPrimaryPerks().isEmpty()) {
            return null;
        }

        return perk;
    }

}
