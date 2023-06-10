package com.rumaruka.gribtweaks.config;

import com.electronwill.nightconfig.core.Config;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public class GTConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final General GENERAL = new General(BUILDER);


    public static class General {
        public static final String GENERAL = "general";

        public final ForgeConfigSpec.BooleanValue sandstormEnabled;
        public final ForgeConfigSpec.IntValue sandstormSandLayerChance;
        public final ForgeConfigSpec.IntValue sandstormFog;
        public final ForgeConfigSpec.IntValue sandDarkness;
        public final ForgeConfigSpec.IntValue sandAlpha;
        public final ForgeConfigSpec.IntValue sandEyesAlpha;
        public final ForgeConfigSpec.IntValue sandstormTransitionTime;

        //Client
        public final ForgeConfigSpec.DoubleValue particleamount;
        public final ForgeConfigSpec.DoubleValue particleDensity;
        public final ForgeConfigSpec.DoubleValue sandmotemodifier;
        public final ForgeConfigSpec.DoubleValue snowmodifier;
        public final ForgeConfigSpec.DoubleValue blizzardsnowmodifier;
        public final ForgeConfigSpec.DoubleValue blizzardwindmodifier;
        public final ForgeConfigSpec.DoubleValue updraftmodifier;
        public final ForgeConfigSpec.DoubleValue windmodifier;
        public final ForgeConfigSpec.DoubleValue sporemodifier;
        public final ForgeConfigSpec.DoubleValue soulmodifier;
        public final ForgeConfigSpec.DoubleValue tearmodifier;
        public final ForgeConfigSpec.DoubleValue rainmodifier;
        public final ForgeConfigSpec.DoubleValue heavyrainmodifier;
        public final ForgeConfigSpec.DoubleValue tumblebushmodifier;
        public final ForgeConfigSpec.DoubleValue fireflymodifier;
        private static ForgeConfigSpec.ConfigValue<List<Config>> tempInfoEntries;

        public General(ForgeConfigSpec.Builder builder) {

            builder.push(GENERAL);

            this.sandstormEnabled = builder.comment("Enable/disables all functionality of sandstorms")
                    .translation("atum.configGui.sandstormenabled")
                    .define("Sandstorm Enabled", true);
            this.sandstormSandLayerChance = builder.comment("Chance for sandstorms to generate sand layers. The higher the value, the more rare it is. Set to 0 to disable.")
                    .translation("atum.configGui.sandstormsandrarity")
                    .defineInRange("Sandstorm Sand Layer", 75, 0, 10000);
            this.sandstormFog = builder.comment("Multiplier to fog during sandstorms")
                    .translation("atum.config.sandstormfog")
                    .defineInRange("Sandstorm Fog", 2, 0, 100);
            this.sandDarkness = builder.comment("How light the sand particles are")
                    .translation("atum.config.sandbrightness")
                    .defineInRange("Sandstorm Brightness", 75, 0, 100);
            this.sandAlpha = builder.comment("Base transparency for sand particles")
                    .translation("atum.config.sandalpha")
                    .defineInRange("Sandstorm Base Transparency", 10, 0, 100);
            this.sandEyesAlpha = builder.comment("Sand particle transparency while wearing Sandstorm reducing helmets")
                    .translation("atum.config.sandalpha")
                    .defineInRange("Sandstorm Helmet Transparency", 40, 0, 100);
            this.sandstormTransitionTime = builder.comment("Seconds it takes to transition from clear to sandstorm")
                    .translation("atum.config.sandstormtransition")
                    .defineInRange("Sandstorm Transition Time", 25, 0, 100);


            this.particleamount = builder.defineInRange("particleamount", 32.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.particleDensity = builder.defineInRange("particleDensity", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.sandmotemodifier = builder.defineInRange("sandmotemodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.snowmodifier = builder.defineInRange("snowmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.blizzardsnowmodifier = builder.defineInRange("blizzardsnowmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.blizzardwindmodifier = builder.defineInRange("blizzardwindmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.updraftmodifier = builder.defineInRange("updraftmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.windmodifier = builder.defineInRange("windmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.sporemodifier = builder.defineInRange("sporemodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.soulmodifier = builder.defineInRange("soulmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.tearmodifier = builder.defineInRange("tearmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.rainmodifier = builder.defineInRange("rainmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.heavyrainmodifier = builder.defineInRange("heavyrainmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.tumblebushmodifier = builder.defineInRange("tumblebushmodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            this.fireflymodifier = builder.defineInRange("fireflymodifier", 1.0, Double.MIN_VALUE, Double.MAX_VALUE);
            builder.pop();
        }


    }


    public static ForgeConfigSpec spec = BUILDER.build();


}
