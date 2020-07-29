package rngFix;


import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import rngFix.util.TextureLoader;

@SpireInitializer
public class RngFix implements
//        PostDeathSubscriber,
        PostInitializeSubscriber{

    public static final Logger logger = LogManager.getLogger(RngFix.class.getName());

    private static final String MODNAME = "RNG Fix";
    private static final String AUTHOR = "Nichilas";
    private static final String DESCRIPTION = "Fixes inherent correlation issues from the Random Number Generator!";

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