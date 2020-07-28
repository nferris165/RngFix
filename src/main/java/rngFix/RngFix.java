package rngFix;


import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rngFix.util.TextureLoader;

import java.util.Properties;

@SpireInitializer
public class RngFix implements
//        PostDeathSubscriber,
        PostInitializeSubscriber{

    public static final Logger logger = LogManager.getLogger(RngFix.class.getName());

    //mod settings
    private static Properties defaultSettings = new Properties();
    private static final String health_limit_settings = "noHealthLimit";
    public static boolean healthLimit = false;

    private static final String MODNAME = "Better Altar";
    private static final String AUTHOR = "Nichilas";
    private static final String DESCRIPTION = "A mod to make the Forgotten Altar Event better";

    private static final String BADGE_IMAGE = "rngFixResources/images/Badge.png";

    private static final String modID = "rngFix";

    public RngFix() {
        BaseMod.subscribe(this);

        logger.info("Adding mod settings");
        defaultSettings.setProperty(health_limit_settings, "FALSE");
        try {
            SpireConfig config = new SpireConfig("rngFix", "rngFixConfig", defaultSettings);
            config.load();
            healthLimit = config.getBool(health_limit_settings);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void initialize() {
        RngFix rngFix = new RngFix();
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    @Override
    public void receivePostInitialize() {
        Texture badgeTexture = TextureLoader.getTexture(BADGE_IMAGE);
        ModPanel settingsPanel = new ModPanel();

        ModLabeledToggleButton ascLimitButton = new ModLabeledToggleButton("Toggles something",
                350.0f, 750.0f, Settings.CREAM_COLOR, FontHelper.charDescFont,
                healthLimit,
                settingsPanel,
                (label) -> {},
                (button) -> {

                    healthLimit = button.enabled;
                    try {
                        SpireConfig config = new SpireConfig("rngFix", "rngFixConfig", defaultSettings);
                        config.setBool(health_limit_settings, healthLimit);
                        config.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });


        settingsPanel.addUIElement(ascLimitButton);
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);

    }

    /*
    @Override
    public void receivePostDeath() {
        customMetrics metrics = new customMetrics();

        Thread t = new Thread(metrics);
        t.setName("Metrics");
        t.start();
    }
    */
}