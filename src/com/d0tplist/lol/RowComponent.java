package com.d0tplist.lol;

import com.d0tplist.pojo.PerkData;
import com.d0tplist.pojo.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.*;
import javafx.scene.layout.HBox;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RowComponent extends HBox {

    private static HashMap<String, Image> cache = new HashMap<>();

    private PerkData selected;
    private int type;
    private Style style;

    public RowComponent(PerkData selected, int type, Style style, List<PerkData> perks) {

        this.selected = selected;
        this.type = type;
        this.style = style;

        final List<ImageView> components = new ArrayList<>();


        for (int i = 0; i < perks.size(); i++) {
            PerkData perk = perks.get(i);

            ImageView imageView = create(perk);

            components.add(imageView);

            getChildren().add(imageView);
        }

        setSpacing(10);

        setAlignment(Pos.CENTER);

    }

    public Style getPerkStyle() {
        return style;
    }

    public PerkData getSelected() {
        return selected;
    }

    public int getType() {
        return type;
    }


    private ImageView create(PerkData perkData) {

        Image image;

        final String path = RuneUpdater.getURL(perkData.getIconPath());

        if (cache.containsKey(path)) {
            image = cache.get(path);
        } else {

            InputStream inputStream = RuneUpdater.getInputStream(path);

            if (inputStream == null) {
                return new ImageView();
            }

            image = new Image(inputStream);
            cache.put(path, image);
        }

        final ImageView imageView = new ImageView(image);

        if (type == 1) {
            imageView.setFitHeight(65);
            imageView.setFitWidth(65);
        } else if (type == 2) {
            imageView.setFitHeight(46);
            imageView.setFitWidth(46);
        } else if (type == 3) {
            imageView.setFitHeight(35);
            imageView.setFitWidth(35);
        }

        if (!perkData.getId().equals(selected.getId())) {
            ColorAdjust desaturate = new ColorAdjust();
            desaturate.setSaturation(-1);

            desaturate.setInput(new DropShadow());

            imageView.setEffect(desaturate);

        } else {
            imageView.setEffect(new DropShadow());
        }

        Tooltip tooltip = new Tooltip(perkData.getShortDesc().replaceAll("<.*?>", ""));
        tooltip.setPrefWidth(250);
        tooltip.setWrapText(true);
        Tooltip.install(imageView, tooltip);

        return imageView;
    }

}
