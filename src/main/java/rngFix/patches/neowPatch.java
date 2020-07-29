package rngFix.patches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.EventRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;

import java.util.ArrayList;

public class neowPatch {
    @SpirePatch(
            clz = NeowEvent.class,
            method = "blessing"
    )

    public static class generatePatch {
        @SpireInsertPatch(
                locator = eventRngPatch.Locator.class,
                localvars = {"rng"}
        )
        public static void Insert(EventRoom __instance, @ByRef Random[] rng){
            Random gen = new Random(Settings.seed, 13);
            rng[0] = new Random(gen.random.nextLong(), AbstractDungeon.eventRng.counter);
        }
    }

    public static class Locator extends SpireInsertLocator {
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractDungeon.class, "bossCount");
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), finalMatcher);
        }
    }
}
