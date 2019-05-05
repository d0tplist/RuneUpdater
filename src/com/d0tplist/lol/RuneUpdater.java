package com.d0tplist.lol;


import com.d0tplist.pojo.*;
import com.d0tplist.pojo.Locale;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("Duplicates")
public class RuneUpdater extends Application {

    private static String port = null;
    private static String authToken = null;

    private Locale locale = null;
    private Session session = null;
    private List<Champion> championList;
    private List<PerkData> perkDataList;
    private List<Style> styleList;
    private List<Perk> opggperks;

    private static HashMap<String, String> regions = new HashMap<>();

    public static void main(String[] args) {
        Application.launch(RuneUpdater.class, args);
    }

    private final AnchorPane root = new AnchorPane();
    private final AnchorPane master = new AnchorPane();

    private ToolBar toolBar = new ToolBar();
    private Button buttonUpdate = new Button("Load");
    private ChoiceBox<Perk> choiceBox = new ChoiceBox<>();
    private ComboBox<Champion> championChoiceBox = new ComboBox<>();
    private SplitPane splitPane = new SplitPane();
    private VBox vBoxCurrent = new VBox();
    private VBox vBoxNew = new VBox();
    private CheckBox checkBoxVoice = new CheckBox("Champion Voice");
    private final VBox loader = new VBox();
    private final ProgressIndicator indicator = new ProgressIndicator();

    @Override
    public void start(Stage stage) throws Exception {

        regions.put("cs_CZ", "eune");
        regions.put("el_GR", "eune");
        regions.put("pl_PL", "eune");
        regions.put("ro_RO", "eune");
        regions.put("hu_HU", "eune");
        regions.put("en_GB", "euw");
        regions.put("de_DE", "euw");
        regions.put("es_ES", "euw");
        regions.put("it_IT", "euw");
        regions.put("fr_FR", "euw");
        regions.put("ja_JP", "jp");
        regions.put("ko_KR", "www");
        regions.put("es_MX", "lan");
        regions.put("es_AR", "las");
        regions.put("pt_BR", "br");
        regions.put("en_US", "na");
        regions.put("en_AU", "oce");
        regions.put("ru_RU", "ru");
        regions.put("tr_TR", "tr");
        regions.put("ms_MY", "sg");
        regions.put("en_PH", "ph");
        regions.put("en_SG", "sg");
        regions.put("th_TH", "th");
        regions.put("vn_VN", "vn");
        regions.put("id_ID", "id");
        regions.put("zh_MY", "");
        regions.put("zh_CN", "");
        regions.put("zh_TW", "tw");

        final List<Perk> perks = start();

        splitPane.setStyle("-fx-base: #4d4d4d");
        toolBar.setStyle("-fx-base: #0272B7");

        Scene scene = new Scene(master, 750, 768);
        stage.setScene(scene);
        stage.setTitle("RuneUpdater");

        toolBar.getItems().add(buttonUpdate);
        toolBar.getItems().add(choiceBox);
        toolBar.getItems().add(championChoiceBox);
        toolBar.getItems().add(checkBoxVoice);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(vBoxCurrent);
        scrollPane.setFitToWidth(true);

        ScrollPane scrollPaneNew = new ScrollPane();
        scrollPaneNew.setContent(vBoxNew);
        scrollPaneNew.setFitToWidth(true);

        splitPane.getItems().add(scrollPane);
        splitPane.getItems().add(scrollPaneNew);

        root.getChildren().add(toolBar);
        root.getChildren().add(splitPane);

        AnchorPane.setTopAnchor(toolBar, 0.0);
        AnchorPane.setRightAnchor(toolBar, 0.0);
        AnchorPane.setLeftAnchor(toolBar, 0.0);

        AnchorPane.setBottomAnchor(splitPane, 5.0);
        AnchorPane.setRightAnchor(splitPane, 5.0);
        AnchorPane.setLeftAnchor(splitPane, 5.0);
        AnchorPane.setTopAnchor(splitPane, 45.0);

        AnchorPane.setTopAnchor(root, 0.0);
        AnchorPane.setLeftAnchor(root, 0.0);
        AnchorPane.setRightAnchor(root, 0.0);
        AnchorPane.setBottomAnchor(root, 0.0);

        master.getChildren().add(root);

        choiceBox.setPrefWidth(150);
        championChoiceBox.setPrefWidth(150);

        buttonUpdate.setDefaultButton(true);

        if (perks != null) {
            choiceBox.getItems().addAll(perks);
        }

        System.out.println(championList);

        championList.sort(Comparator.comparing(Champion::getName));

        championChoiceBox.getItems().addAll(championList);

        championChoiceBox.getSelectionModel().selectFirst();

        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            render(newValue, vBoxCurrent, false);
            checkSelection(newValue);
        });

        choiceBox.getSelectionModel().selectFirst();
        checkSelection(choiceBox.getSelectionModel().getSelectedItem());

        championChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            loadOPGG(newValue);
        });

        buttonUpdate.setOnAction(event -> {
            render(choiceBox.getSelectionModel().getSelectedItem(), vBoxCurrent, false);
            loadOPGG(championChoiceBox.getSelectionModel().getSelectedItem());
        });

        loader.getChildren().add(indicator);
        loader.setAlignment(Pos.CENTER);

        indicator.setPrefSize(120, 120);

        stage.show();
    }

    private void checkSelection(Perk newValue) {

        if (newValue == null) {
            return;
        }

        Optional<Champion> first = championChoiceBox.getItems().stream()
                .filter(a -> a.getName().equals(newValue.getName()))
                .findFirst();

        first.ifPresent(champion -> championChoiceBox.getSelectionModel().select(champion));
    }

    private void loadOPGG(Champion newValue) {
        showLoading();

        if (checkBoxVoice.isSelected()) {
            new Thread(() -> {
                String path = getURL(newValue.getChooseVoPath());

                InputStream inputStream = getInputStream(path);

                if (inputStream == null) {
                    return;
                }

                OggPlayer.play(inputStream);

            }).start();
        }

        // System.out.println("Locale: '" + locale.getLocale() + "' Region: " + locale.getRegion()+" Result: "+regions.get(locale.getLocale()));

        new Thread(() -> {
            opggperks = PerkDownloader.getPerk(newValue.getAlias(), regions.get(locale.getLocale()), choiceBox.getSelectionModel().getSelectedItem());

            final Perk perk = opggperks.get(0);
            Platform.runLater(() -> {
                render(perk, vBoxNew, true);
                hiddeLoading();
            });

        }).start();
    }

    private void showLoading() {


        indicator.setProgress(-1);

        master.getChildren().add(loader);
        root.setEffect(new GaussianBlur());

        AnchorPane.setTopAnchor(loader, 0.0);
        AnchorPane.setLeftAnchor(loader, 0.0);
        AnchorPane.setRightAnchor(loader, 0.0);
        AnchorPane.setBottomAnchor(loader, 0.0);
    }

    private void hiddeLoading() {

        indicator.setProgress(0.0);
        master.getChildren().remove(loader);
        root.setEffect(null);
    }

    private List<Perk> start() {


        AllowAll.setUp();

        boolean windows = System.getProperty("os.name").contains("windows");

        String url;

        if (windows) {
            url = "C:\\Program Files(x86)\\League of Legends\\Lol\\Logs\\LeagueClient Logs";
        } else {
            url = "/Applications/League of Legends.app/Contents/LoL/Logs/LeagueClient Logs";
        }

        File logsFolder = new File(url);

        File[] files = logsFolder.listFiles();

        if (files == null) {
            return null;
        }

        List<File> logs = Arrays.stream(files)
                .filter(a -> a.getName().contains("LeagueClientUx.log"))
                .sorted((A, B) -> Long.compare(A.lastModified(), B.lastModified()) * -1)
                .collect(Collectors.toList());

        File file = logs.get(0);

        try {
            final List<String> strings = Files.readAllLines(file.toPath());

            for (String string : strings) {
                System.out.println(string);
            }

            Optional<String> first = strings.stream()
                    .filter(a -> a.trim().contains("--app-port="))
                    .findFirst();

            Optional<String> opToken = strings.stream()
                    .filter(a -> a.trim().contains("--remoting-auth-token="))
                    .findFirst();

            first.ifPresent(s -> port = s.split("=")[1].trim());

            opToken.ifPresent(s -> authToken = s.split("=")[1].trim());

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("App port: " + port);
        System.out.println("Auth token: " + authToken);

        final String riotURL = "https://riot:" + authToken + "@127.0.0.1:" + port + "/";

        final String perkURL = getURL("/lol-perks/v1/pages");
        final String localeURL = getURL("/riotclient/region-locale");
        final String sessionURL = getURL("/lol-login/v1/session");
        final String championURL = getURL("/lol-champions/v1/owned-champions-minimal");
        final String perkDataURL = getURL("/lol-perks/v1/perks");
        final String stylesURL = getURL("/lol-perks/v1/styles");

        System.out.println("Riot URL: " + riotURL);
        System.out.println("Perk Pages: " + perkURL);
        System.out.println("Locale URL: " + localeURL);
        System.out.println("Champion URL: " + championURL);
        System.out.println("PerkData: " + perkDataURL);

        Gson gson = new Gson();

        final String perkResult = get(perkURL);
        final String localeResult = get(localeURL);
        final String sessionResult = get(sessionURL);
        final String championResult = get(championURL);
        final String perkDataResult = get(perkDataURL);
        final String styleResult = get(stylesURL);

        List<Perk> perkList = gson.fromJson(perkResult, new TypeToken<List<Perk>>() {
        }.getType());

        championList = gson.fromJson(championResult, new TypeToken<List<Champion>>() {
        }.getType());

        perkDataList = gson.fromJson(perkDataResult, new TypeToken<List<PerkData>>() {
        }.getType());

        styleList = gson.fromJson(styleResult, new TypeToken<List<Style>>() {
        }.getType());

        locale = gson.fromJson(localeResult, Locale.class);
        session = gson.fromJson(sessionResult, Session.class);

        System.out.println("Result: " + perkResult);
        System.out.println("LocaleResult: " + localeResult);
        System.out.println("ChampionResult: " + championResult);

        if (perkList == null) {
            return null;
        }

        return perkList.stream()
                .filter(Perk::getIsEditable)
                .sorted(Comparator.comparing(Perk::getName))
                .collect(Collectors.toList());
    }

    public static String getURL(String path) {
        return "https://127.0.0.1:" + port + path;
    }


    private HBox wrapp(Node node) {
        HBox hBox = new HBox();

        hBox.getChildren().add(node);
        hBox.setAlignment(Pos.CENTER);

        return hBox;
    }

    private void render(Perk perk, VBox container, boolean opgg) {

        if (perk == null) {
            return;
        }

        container.getChildren().clear();
        container.setSpacing(10.0);

        String date = new java.text.SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new java.util.Date(perk.getLastModified()));

        Label label = new Label(opgg ? "OP.GG Recomendation for " + championChoiceBox.getSelectionModel().getSelectedItem().getName() : "Your current rune page");
        label.setTextAlignment(TextAlignment.CENTER);
        container.getChildren().add(wrapp(label));

        if (opgg) {

            Button button = new Button("Update current page");
            container.getChildren().add(wrapp(button));

            ChoiceBox<Perk> choiceBox = new ChoiceBox<>();
            choiceBox.getItems().addAll(opggperks);

            container.getChildren().add(wrapp(choiceBox));
            choiceBox.getSelectionModel().select(perk);

            choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                render(newValue, container, opgg);
            });

            Label labelWinPick = new Label("Win Rate: " + perk.getPrePerk().getWinRate() + " Pick Rate: " + perk.getPrePerk().getPickRate());
            container.getChildren().add(wrapp(labelWinPick));

            button.setOnAction(event -> {
                final Perk selectedItem = choiceBox.getSelectionModel().getSelectedItem();

                updatePerkPage(selectedItem);

            });

        } else {
            Label labelTime = new Label("Last modification: " + date);
            labelTime.setTextAlignment(TextAlignment.CENTER);

            container.getChildren().add(wrapp(labelTime));
        }

        RowComponent last = null;
        boolean secondStyle = false;

        for (Integer selectedPerkId : perk.getSelectedPerkIds()) {

            Optional<PerkData> first = perkDataList.stream()
                    .filter(a -> Objects.equals(a.getId(), selectedPerkId))
                    .findFirst();

            if (first.isPresent()) {

                PerkData perkData = first.get();

                RowComponent component = create(perkData, perk);

                if (component == null) {
                    return;
                }

                if (component.getType() == 3 && last != null && last.getType() != 3) {
                    container.getChildren().add(new Separator());
                }

                if (!secondStyle && component.getPerkStyle().getId().equals(perk.getSubStyleId())) {

                    container.getChildren().add(new Separator());

                    String url = getURL(component.getPerkStyle().getIconPath());

                    InputStream inputStream = getInputStream(url);

                    if (inputStream != null) {
                        Image image = new Image(inputStream);
                        ImageView imageView = new ImageView(image);
                        imageView.setEffect(new DropShadow());

                        imageView.setFitHeight(35);
                        imageView.setFitWidth(35);

                        HBox box = new HBox();
                        box.getChildren().add(imageView);
                        box.setAlignment(Pos.CENTER);
                        container.getChildren().add(box);
                    }

                    secondStyle = true;
                }

                container.getChildren().add(component);

                if (last == null) {
                    container.getChildren().add(new Separator());
                }

                last = component;

            }

        }

        //PerkDownloader.getPerks();
    }


    private void updatePerkPage(Perk perk) {

        request(getURL("/lol-perks/v1/pages/" + perk.getId()), "DELETE");

        Gson gson = new Gson();

        perk.setId(0);

        String s = gson.toJson(perk);

        String post = post(getURL("/lol-perks/v1/pages"), s);

        Perk perk1 = gson.fromJson(post, Perk.class);

        choiceBox.getItems().remove(choiceBox.getSelectionModel().getSelectedIndex());
        choiceBox.getItems().add(perk1);
        choiceBox.getSelectionModel().select(perk1);
        render(perk1, vBoxCurrent, false);

    }


    private Optional<PerkData> getPerkData(int selectedPerkId) {
        return perkDataList.stream()
                .filter(a -> Objects.equals(a.getId(), selectedPerkId))
                .findFirst();
    }

    private Style getStyle(PerkData perkData) {
        for (Style style : styleList) {
            for (Slot slot : style.getSlots()) {
                for (Integer perk : slot.getPerks()) {
                    if (perk.equals(perkData.getId())) {
                        return style;
                    }
                }
            }
        }

        return null;
    }

    private RowComponent create(PerkData perkData, Perk perk) {

        final Style style = getStyle(perkData);

        if (style != null) {
            Slot slot = style.getSlots()
                    .stream()
                    .filter(a -> a.getPerks().stream().anyMatch(x -> x.equals(perkData.getId())))
                    .findFirst().get();

            List<PerkData> collect = slot.getPerks().stream()
                    .map(this::getPerkData)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toList());

            int type = switch (slot.getType()) {
                case "kKeyStone" -> 1;
                case "kMixedRegularSplashable" -> 2;
                case "kStatMod" -> 3;
                default -> 0;
            };


            return new RowComponent(perkData, type, style, collect);

        } else {
            System.out.println("Not present: " + perkData.getId());
        }

        return null;
    }


    public static InputStream getInputStream(String value) {

        try {

            URL url = new URL(value);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            String auth = new String(Base64.getEncoder().encode(("riot:" + authToken).getBytes()));

            connection.addRequestProperty("Authorization", "Basic " + auth);

            final int responseCode = connection.getResponseCode();
            final String responseMessage = connection.getResponseMessage();

            InputStream inputStream = connection.getInputStream();

            System.out.println(value + " " + responseCode + " " + responseMessage);

            return inputStream;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private String post(String value, String postData) {
        try {

            URL url = new URL(value);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.addRequestProperty("Accept", "application/json");
            connection.addRequestProperty("Content-type", "application/json");
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Length", String.valueOf(postData.getBytes().length));

            String auth = new String(Base64.getEncoder().encode(("riot:" + authToken).getBytes()));

            connection.addRequestProperty("Authorization", "Basic " + auth);

            OutputStream os = connection.getOutputStream();
            os.write(postData.getBytes());

            final int responseCode = connection.getResponseCode();
            final String responseMessage = connection.getResponseMessage();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String result = reader.lines().collect(Collectors.joining());

            System.out.println(value + " " + responseCode + " " + responseMessage);

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    private String get(String value) {
        return request(value, "GET");
    }

    private String request(String value, String type) {

        try {

            URL url = new URL(value);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.addRequestProperty("Accept", "application/json");
            connection.setRequestMethod(type);

            String auth = new String(Base64.getEncoder().encode(("riot:" + authToken).getBytes()));

            connection.addRequestProperty("Authorization", "Basic " + auth);

            final int responseCode = connection.getResponseCode();
            final String responseMessage = connection.getResponseMessage();

            InputStream inputStream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String result = reader.lines().collect(Collectors.joining());

            System.out.println(value + " " + responseCode + " " + responseMessage);

            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
