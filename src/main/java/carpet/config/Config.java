package carpet.config;

public class Config extends TinyConfig {

    @Entry(min = 0)
    public static int extraOffset = 0;

    @Entry public static boolean dynamicOffset = true;
    @Entry public static boolean disableMod = false;

}
