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

    private static final String MODNAME = "Better Altar";
    private static final String AUTHOR = "Nichilas";
    private static final String DESCRIPTION = "A mod to make the Forgotten Altar Event better";

    private static final String BADGE_IMAGE = "rngFixResources/images/Badge.png";

    private static final String modID = "rngFix";

    public RngFix() {
        BaseMod.subscribe(this);
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